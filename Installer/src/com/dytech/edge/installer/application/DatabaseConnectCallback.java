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

package com.dytech.edge.installer.application;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.dytech.devlib.PropBagEx;
import com.dytech.edge.common.Constants;
import com.dytech.edge.installer.DatabaseCommand;
import com.dytech.edge.installer.DatabaseCommand.NonUnicodeEncodingException;
import com.dytech.gui.workers.GlassSwingWorker;
import com.dytech.installer.Callback;
import com.dytech.installer.Wizard;

@SuppressWarnings("nls")
public class DatabaseConnectCallback implements Callback
{
	@Override
	public void task(final Wizard installer)
	{
		PropBagEx output = installer.getOutputNow();

		final String installerPath = output.getNode("installer/local");
		final String database = output.getNode("datasource/database");
		final String username = output.getNode("datasource/username");
		final String password = output.getNode("datasource/password");
		final String dbtype = output.getNode("datasource/dbtype");
		final String idtype = output.getNode("datasource/idtype");

		String h = output.getNode("datasource/host");
		int p;
		if( h.indexOf(':') >= 0 )
		{
			String[] bits = h.split(":");
			h = bits[0];
			p = Integer.parseInt(bits[1]);
		}
		else
		{
			p = DatabaseCommand.getDefaultPort(dbtype);
		}

		final String host = h;
		final String port = Integer.toString(p);

		GlassSwingWorker<?> worker = new GlassSwingWorker<Object>()
		{
			/*
			 * (non-Javadoc)
			 * @see com.dytech.gui.workers.AdvancedSwingWorker#construct()
			 */
			@Override
			public Object construct() throws SQLException, Exception
			{
				Properties properties = new Properties();
				FileInputStream fis = new FileInputStream(installerPath + '/' + Constants.LEARNINGEDGE_CONFIG_FOLDER
					+ "/hibernate.properties." + dbtype);
				properties.load(fis);
				fis.close();

				String driver = properties.getProperty("hibernate.connection.driver_class");
				String connectionUrl = properties.getProperty("hibernate.connection.url");
				connectionUrl = connectionUrl.replace("${datasource/host}", host);
				connectionUrl = connectionUrl.replace("${datasource/port}", port);
				connectionUrl = connectionUrl.replace("${datasource/database}", database);
				connectionUrl = connectionUrl.replace("${datasource/idtype}", idtype);

				testDatabase(driver, connectionUrl, username, password, dbtype, database);

				return null;
			}

			@Override
			public void finished()
			{
				installer.gotoRelativePage(1);
			}

			@Override
			public void exception()
			{
				getException().printStackTrace();
				if( getException() instanceof NonUnicodeEncodingException )
				{
					JOptionPane.showMessageDialog(installer.getFrame(),
						"The database does not appear to be setup with UNICODE or UTF-8 character\n"
							+ "encoding.  Please ensure that you have followed the instructions for setting\n"
							+ "up this type of database as specified in the installation documentation.",
						"Incorrect Character Encoding", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					String message = getException().getMessage();
					if( message.length() > 100 )
					{
						message = shorten(message);
					}
					JOptionPane.showMessageDialog(installer.getFrame(),
						"An error occured while attempting to connect to the database. \n"
							+ "Please check that you have entered the correct values as specified in the\n"
							+ " installation documentation and try again.\n"
							+ "The error provided by the application was: \n" + "-  " + message,
						"Could Not Connect To Database", JOptionPane.ERROR_MESSAGE);
				}
				installer.gotoRelativePage(-1);
			}
		};

		worker.setComponent(installer.getFrame());
		worker.start();
	}

	// quick dirty and un-robust get around to shorten potentially very long
	// messages returned from trying to connect to the database.
	private String shorten(String longString)
	{
		StringBuilder shortString = new StringBuilder(); // the string to
		// return.
		String[] words = longString.split(" ");
		int charPerLine = 0;
		for( int i = 0; i < words.length; i++ )
		{
			if( charPerLine < 90 )
			{
				charPerLine = charPerLine + words[i].length();
				shortString.append(words[i]);
			}
			else
			{
				shortString.append("\n");
				charPerLine = words[i].length();
				shortString.append(words[i]);
			}
			shortString.append(" ");
		}

		return shortString.toString();
	}

	public void testDatabase(String driver, String connectionUrl, String username, String password, String dbtype,
		String database) throws SQLException, ClassNotFoundException, NonUnicodeEncodingException
	{
		Connection connection = null;
		try
		{
			// We USED to use Hibernate but I removed it due to ClassLoader
			// issues. So shoot me...
			Class.forName(driver);
			connection = DriverManager.getConnection(connectionUrl, username, password);
			DatabaseCommand.ensureUnicodeEncoding(connection, dbtype, database);
		}
		catch( SQLException pain )
		{
			throw new RuntimeException("Attempted connect at connectionURL (" + connectionUrl + "), username ("
				+ username + "), password(" + password + "), dbtype(" + dbtype + "), database(" + database + ')', pain);
		}
		finally
		{
			if( connection != null )
			{
				connection.close();
			}
		}
	}
}
