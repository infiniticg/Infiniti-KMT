package com.tle.resttests;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.collections.Sets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.tle.common.Check;
import com.tle.common.Pair;
import com.tle.json.assertions.ApiAssertions;
import com.tle.json.framework.PageContext;
import com.tle.json.framework.TestInstitution;
import com.tle.resttests.util.OAuthClient;

/**
 * @author Aaron
 */
@TestInstitution("autotest")
@SuppressWarnings("nls")
public abstract class AbstractRestApiTest extends AbstractSessionTest
{
	protected ObjectMapper mapper;
	protected ThreadLocal<HttpClient> client = new ThreadLocal<HttpClient>();
	protected ApiAssertions asserter;
	private String proxyHost;
	private int proxyPort;

	protected final List<OAuthClient> clients = Lists.newArrayList();
	private String token;

	private ClientConnectionManager conMan;
	private SSLSocketFactory blindFactory;
	private String adminToken;

	@BeforeClass
	public void setup() throws Exception
	{
		adminToken = "admin_token=" + testConfig.getAdminPassword();
		conMan = new PoolingClientConnectionManager();

		// Allows us to just accept all SSL certs
		final TrustManager[] blindTrustMan = new TrustManager[]{new X509TrustManager()
		{
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws CertificateException
			{
				// Nope
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws CertificateException
			{
				// Nope
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers()
			{
				return new java.security.cert.X509Certificate[0];
			}
		}};

		final SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, blindTrustMan, new java.security.SecureRandom());
		blindFactory = sc.getSocketFactory();

		conMan.getSchemeRegistry().register(new Scheme("https", 443, new SchemeSocketFactory()
		{
			@Override
			public Socket connectSocket(Socket s, InetSocketAddress i, InetSocketAddress i2, HttpParams h)
				throws IOException, UnknownHostException, ConnectTimeoutException
			{
				s.connect(i, 60000);
				return s;
			}

			@Override
			public Socket createSocket(HttpParams p) throws IOException
			{
				return blindFactory.createSocket();
			}

			@Override
			public boolean isSecure(Socket arg0) throws IllegalArgumentException
			{
				return false;
			}
		}));

		mapper = new ObjectMapper();
		proxyHost = testConfig.getProperty("proxy.host");
		String proxyPortString = testConfig.getProperty("proxy.port");
		if( proxyPortString != null )
		{
			proxyPort = Integer.parseInt(proxyPortString);
		}

		asserter = new ApiAssertions(context);
		registerClients();
	}

	protected HttpClient getClient()
	{
		HttpClient httpClient = client.get();
		if( httpClient == null )
		{
			boolean ssl = context.getBaseUrl().startsWith("https");
			httpClient = createHttpClient(ssl);
			client.set(httpClient);
		}
		return httpClient;
	}

	public void registerClients() throws Exception
	{
		// Create OAuth client-credentials clients
		final List<OAuthClient> oauthClients = getOAuthClients();
		for( OAuthClient clientInfo : oauthClients )
		{
			ObjectNode oauthclient = mapper.createObjectNode();
			oauthclient.put("name", clientInfo.getName());
			oauthclient.put("clientId", clientInfo.getClientId());
			String secret = clientInfo.getSecret();
			if( Check.isEmpty(secret) )
			{
				secret = UUID.randomUUID().toString();
				clientInfo.setSecret(secret);
			}
			oauthclient.put("clientSecret", secret);
			oauthclient.put("userId", clientInfo.getUserId());
			String redirectUrl = clientInfo.getUrl();
			if( Check.isEmpty(redirectUrl) )
			{
				redirectUrl = "default";
				clientInfo.setUrl(redirectUrl);
			}
			oauthclient.put("redirectUrl", redirectUrl);
			clients.add(clientInfo);
			HttpResponse postResponse = postEntity(oauthclient.toString(), context.getBaseUrl() + "api/oauth",
				adminToken, true);
			Header postHeader = postResponse.getLastHeader("Location");
			if( postHeader == null )
			{
				throw new RuntimeException("POST response did not contain 'Location' in header (code: "
					+ postResponse.getStatusLine().getStatusCode() + ')');
			}
			String clientUri = postHeader.getValue();
			JsonNode entity = getEntity(clientUri, adminToken);
			clientInfo.setUuid(entity.get("uuid").asText());
		}
	}

	@Override
	protected void cleanupAfterClass() throws Exception
	{
		List<String> clientUuids = Lists.newArrayList();
		Set<String> lookups = Sets.newHashSet();
		for( OAuthClient client : clients )
		{
			if( client.getUuid() != null )
			{
				clientUuids.add(client.getUuid());
			}
			else
			{
				lookups.add(client.getClientId());
			}
		}
		if( !lookups.isEmpty() )
		{
			JsonNode clients = getEntity(context.getBaseUrl() + "api/oauth/", adminToken);
			for( JsonNode clientNode : clients.get("results") )
			{
				if( lookups.contains(clientNode.get("clientId").asText()) )
				{
					clientUuids.add(clientNode.get("uuid").asText());
				}
			}
		}
		for( String clientUuid : clientUuids )
		{
			deleteResource(context.getBaseUrl() + "api/oauth/" + clientUuid, adminToken);
		}
	}

	protected String getToken() throws IOException
	{
		if( token == null )
		{
			token = requestToken(clients.get(0));
		}
		return token;
	}

	protected String requestToken(OAuthClient client) throws IOException
	{
		return requestToken(client, context);
	}

	protected String requestToken(OAuthClient client, PageContext contextToUse) throws IOException
	{
		final String tokenGetUrl = contextToUse.getBaseUrl()
			+ "oauth/access_token?grant_type=client_credentials&client_id=" + client.getClientId()
			+ "&redirect_uri=default&client_secret=" + client.getSecret();
		final HttpResponse response = execute(new HttpGet(tokenGetUrl), false);
		final JsonNode responseNode = readJson(mapper, response);
		final JsonNode tokenNode = responseNode.get("access_token");
		if( tokenNode == null )
		{
			throw new RuntimeException("Failed to get \"access_token\" in oauth/access_token?... GET request");
		}
		return "access_token=" + tokenNode.textValue();
	}

	protected String requestToken(String id) throws IOException
	{
		for( OAuthClient client : clients )
		{
			if( client.getClientId().equals(id) )
			{
				return requestToken(client);
			}
		}
		return null;
	}

	protected List<OAuthClient> getOAuthClients()
	{
		final List<Pair<String, String>> clients = Lists.newArrayList();
		final List<OAuthClient> oClients = Lists.newArrayList();
		addOAuthClients(clients);
		addFullOAuthClients(oClients);
		for( Pair<String, String> client : clients )
		{
			OAuthClient oClient = new OAuthClient();
			oClient.setName(client.getFirst());
			oClient.setClientId(client.getFirst());
			oClient.setUserId(client.getSecond());
			oClients.add(oClient);
		}

		return oClients;
	}

	protected abstract void addOAuthClients(List<Pair<String, String>> clients);

	protected void addFullOAuthClients(List<OAuthClient> clients)
	{
		// nothing
	}

	protected HttpClient createHttpClient(boolean ssl)
	{
		HttpClient client;
		if( ssl )
		{
			client = new DefaultHttpClient(conMan);
		}
		else
		{
			client = new DefaultHttpClient();
		}

		if( proxyHost != null )
		{
			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		return client;
	}

	protected HttpResponse download(String url, File downloadTo) throws Exception
	{
		return download(url, downloadTo, null);
	}

	protected HttpResponse download(String url, File downloadTo, String token) throws Exception
	{
		final HttpResponse response = execute(new HttpGet(url), false, token);
		final InputStream echoedContent = response.getEntity().getContent();

		OutputStream out = null;
		try
		{
			out = new BufferedOutputStream(new FileOutputStream(downloadTo));
			ByteStreams.copy(echoedContent, out);
			out.flush();
			return response;
		}
		finally
		{
			Closeables.close(out, true);
		}
	}

	protected JsonNode getEntity(String uri, String token, Object... params) throws IOException
	{
		HttpResponse response = execute(new HttpGet(appendQueryString(uri, queryString(params))), false, token);
		return mapper.readTree(response.getEntity().getContent());
	}

	protected HttpResponse getResponse(String uri, String token, boolean consume, Object... params) throws IOException
	{
		return execute(new HttpGet(appendQueryString(uri, queryString(params))), consume, token);
	}

	protected HttpResponse postUri(String uri, String token, Object... paramNameValues) throws IOException
	{
		return execute(new HttpPost(appendQueryString(uri, queryString(paramNameValues))), true, token);
	}

	protected HttpResponse putEntity(String json, String uri, String token, boolean consume, Object... paramNameValues)
		throws IOException
	{
		final HttpPut request = new HttpPut(appendQueryString(uri, queryString(paramNameValues)));
		final StringEntity ent = new StringEntity(json, "UTF-8");
		ent.setContentType("application/json");
		request.setEntity(ent);
		return execute(request, consume, token);
	}

	protected HttpResponse postEntity(String json, String uri, String token, boolean consume, Object... paramNameValues)
		throws IOException
	{
		final HttpPost request = new HttpPost(appendQueryString(uri, queryString(paramNameValues)));
		final StringEntity ent = new StringEntity(json, "UTF-8");
		ent.setContentType("application/json");
		request.setEntity(ent);
		return execute(request, consume, token);
	}

	protected HttpResponse deleteResource(String uri, String token, Object... paramNameValues) throws IOException
	{
		return execute(new HttpDelete(appendQueryString(uri, queryString(paramNameValues))), true, token);
	}

	protected HttpHead getHead(String url, Object... paramNameValues) throws IOException
	{
		return new HttpHead(appendQueryString(url, queryString(paramNameValues)));
	}

	protected HttpPut getPut(String url, File file, Object... paramNameValues) throws IOException
	{
		final HttpPut put = new HttpPut(appendQueryString(url, queryString(paramNameValues)));
		if( file != null )
		{
			InputStreamEntity inputStreamEntity = new InputStreamEntity(new FileInputStream(file), file.length());
			inputStreamEntity.setContentType("application/octet-stream");
			put.setEntity(inputStreamEntity);
		}
		return put;
	}

	protected HttpPost getPost(String url, String jsonEntity) throws UnsupportedEncodingException
	{
		final HttpPost post = new HttpPost(url);
		final StringEntity string = new StringEntity(jsonEntity, "application/json", "UTF-8");
		post.setEntity(string);
		return post;
	}

	protected HttpPut getPut(String url, String jsonEntity) throws UnsupportedEncodingException
	{
		final HttpPut put = new HttpPut(url);
		final StringEntity string = new StringEntity(jsonEntity, "application/json", "UTF-8");
		put.setEntity(string);
		return put;
	}

	protected static void assertResponse(HttpResponse response, int status, String message)
	{
		AssertJUnit.assertEquals(message, status, response.getStatusLine().getStatusCode());
	}

	protected HttpResponse execute(HttpUriRequest request, boolean consume) throws ClientProtocolException, IOException
	{
		return execute(request, consume, null);
	}

	protected HttpResponse execute(HttpUriRequest request, boolean consume, String token)
		throws ClientProtocolException, IOException
	{
		if( token != null )
		{
			final Header tokenHeader = new BasicHeader("X-Authorization", token);
			request.setHeader(tokenHeader);
		}
		request.setHeader("X-Autotest-Key", context.getFullName(""));
		HttpResponse response = getClient().execute(request);
		if( consume )
		{
			EntityUtils.consume(response.getEntity());
		}
		return response;
	}

	protected ObjectNode readJson(ObjectMapper mapper, HttpResponse response) throws IOException
	{
		InputStream content = null;
		try
		{
			content = response.getEntity().getContent();
			return (ObjectNode) mapper.readTree(content);
		}
		finally
		{
			if( content != null )
			{
				content.close();
			}
		}
	}

	protected String getLink(ObjectNode rootNode, String linkName)
	{
		ObjectNode linksNode = (ObjectNode) rootNode.get("links");
		if( linksNode == null )
		{
			return null;
		}
		JsonNode linkNode = linksNode.get(linkName);
		if( linkNode == null )
		{
			return null;
		}
		return linkNode.asText();
	}

	protected ArrayNode getArray(ObjectNode rootNode, String fieldName)
	{
		return (ArrayNode) rootNode.get(fieldName);
	}

	protected ObjectNode getObject(ObjectNode rootNode, String fieldName)
	{
		return (ObjectNode) rootNode.get(fieldName);
	}

	/**
	 * Appends a query string to a URL, detecting if the URL already contains a
	 * query string
	 * 
	 * @param url
	 * @param queryString
	 * @return
	 */
	public String appendQueryString(String url, String queryString)
	{
		return url
			+ (queryString == null || queryString.equals("") ? "" : (url.contains("?") ? '&' : '?') + queryString);
	}

	/**
	 * @param paramNameValues An even number of strings indicating the parameter
	 *            names and values i.e. "param1", "value1", "param2", "value2"
	 * @return
	 */
	public String queryString(Object... paramNameValues)
	{
		final List<NameValuePair> params = Lists.newArrayList();
		if( paramNameValues != null )
		{
			if( paramNameValues.length % 2 != 0 )
			{
				throw new RuntimeException("Must supply an even number of paramNameValues");
			}
			for( int i = 0; i < paramNameValues.length; i += 2 )
			{
				Object val = paramNameValues[i + 1];
				if( val != null )
				{
					params.add(new BasicNameValuePair(paramNameValues[i].toString(), val.toString()));
				}
			}
		}
		return queryString(params);
	}

	/**
	 * Turns a list of NameValuePair into a query string. e.g
	 * param1=val1&param2=val2
	 * 
	 * @param params
	 * @return
	 */
	public String queryString(List<NameValuePair> params)
	{
		if( params == null )
		{
			return null;
		}
		return URLEncodedUtils.format(params, "UTF-8");
	}

	protected String paramString(Map<String, ?> paramMap)
	{
		List<NameValuePair> params = Lists.newArrayList();
		if( paramMap != null )
		{
			for( Entry<String, ?> paramEntry : paramMap.entrySet() )
			{
				Object value = paramEntry.getValue();
				if( value != null )
				{
					params.add(new BasicNameValuePair(paramEntry.getKey(), value.toString()));
				}
			}
		}
		return URLEncodedUtils.format(params, "UTF-8");
	}
}
