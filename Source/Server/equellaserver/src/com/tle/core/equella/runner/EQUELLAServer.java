/*
 * Copyright 2017 Apereo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tle.core.equella.runner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.PropertyConfigurator;
import org.java.plugin.JpfException;
import org.java.plugin.ObjectFactory;
import org.java.plugin.Plugin;
import org.java.plugin.PluginClassLoader;
import org.java.plugin.PluginManager;
import org.java.plugin.PluginManager.PluginLocation;
import org.java.plugin.registry.ManifestInfo;
import org.java.plugin.standard.StandardPathResolver;
import org.java.plugin.util.ExtendedProperties;
import org.java.plugin.util.IoUtil;

@SuppressWarnings("nls")
public class EQUELLAServer
{
	private static final Collection<String> STARTUP_ROLES = Arrays.asList("initial", "core", "web");

	// Same as Constants.UPGRADE_LOCK
	private static final String UPGRADE_LOCK = "equella.lock";

	public PluginManager manager;

	// JSVC methods (Unix/other)
	public void init(String[] args)
	{
		System.out.println("Initializing EQUELLA Server");
	}

	public void start()
	{
		System.out.println("Starting EQUELLA Server...");
		main(new String[0]);
	}

	public void stop()
	{
		System.out.println("Stopping EQUELLA Server...");
	}

	public void destroy()
	{
		System.out.println("Stopped!");
	}

	// PROCRUN methods (Windows)
	public static void start(String[] args)
	{
		System.out.println("Starting EQUELLA Server...");
		main(new String[0]);
	}

	public static void stop(String[] args)
	{
		System.out.println("Stopping EQUELLA Server...");
	}

	public static void main(String[] args)
	{
		new EQUELLAServer().startServer();
	}

	public String getProperty(Properties props, String key, String defValue) {
		return System.getProperty(key, props.getProperty(key, defValue));
	}

	public void startServer()
	{
		ClassLoader loader = getClass().getClassLoader();

		// // Delete the lock file to signify startup
		URL url = loader.getResource(UPGRADE_LOCK);
		if( url != null )
		{
			try
			{
				Files.deleteIfExists(Paths.get(url.toURI()));
			}
			catch( IOException | URISyntaxException e )
			{
				// Do not stop - But unable to delete equella.lock
			}
		}

		PropertyConfigurator.configure(loader.getResource("learningedge-log4j.properties"));
		Properties mandatory = new Properties();
		Properties optional = new Properties();

		try
		{
			TomcatLogRedirector.activate();
			mandatory.load(loader.getResourceAsStream("mandatory-config.properties"));
			optional.load(loader.getResourceAsStream("optional-config.properties"));

			ExtendedProperties props = new ExtendedProperties();
			String pathResolver;
            boolean devMode = Boolean.parseBoolean(getProperty(mandatory, "equella.devmode", "false"));
            if (devMode)
			{
				System.out.println("Started server in dev mode");
				pathResolver = StandardPathResolver.class.getName();
			}
			else
			{
				pathResolver = CleaningShadingPathResolver.class.getName();
			}
			props.setProperty("org.java.plugin.PathResolver", pathResolver);
			props.setProperty("org.java.plugin.standard.StandardPluginLifecycleHandler.probeParentLoaderLast", "true");
			ObjectFactory objectFactory = ObjectFactory.newInstance(props);

			String pluginLocations = getProperty(mandatory, "plugins.location", null);
			if (devMode)
			{
				System.out.println("Scanning for plugins in "+pluginLocations);
			}
			manager = objectFactory.createManager();

			Map<String, TLEPluginLocation> registered = new HashMap<String, TLEPluginLocation>();

			PluginScanner.scanForPlugins(manager.getRegistry(), registered, pluginLocations, devMode);

			manager.publishPlugins(registered.values().toArray(new PluginLocation[registered.size()]));

			List<Object[]> alreadyRegistered = new ArrayList<Object[]>();
			for( TLEPluginLocation pluginLocation : registered.values() )
			{
				Object[] entry = new Object[]{pluginLocation.getManifestInfo(), pluginLocation.getJar(),
						pluginLocation.getVersion(), pluginLocation.getContextLocation(),
						pluginLocation.getManifestLocation()};
				alreadyRegistered.add(entry);
			}
			Plugin plugin = manager.getPlugin("com.equella.base");
			PluginClassLoader plugLoader = manager.getPluginClassLoader(plugin.getDescriptor());
			Class<?> clazz = plugLoader.loadClass("com.tle.core.application.ApplicationStarter");
			Method method = clazz.getMethod("start", PluginManager.class, Collection.class, Collection.class);
			method.invoke(null, manager, alreadyRegistered, STARTUP_ROLES);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
