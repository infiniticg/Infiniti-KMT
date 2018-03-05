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

package com.dytech.installer.commands;

import java.io.IOException;

import com.dytech.common.io.FileWrapper;
import com.dytech.installer.InstallerException;

public class CMove extends Command
{
	protected String source;
	protected String destination;
	protected boolean force;

	public CMove(String source, String destination, boolean force)
	{
		this.source = source;
		this.destination = destination;
		this.force = force;
	}

	@Override
	public void execute() throws InstallerException
	{
		propogateTaskStarted(1);

		FileWrapper from = new FileWrapper(source);
		FileWrapper to = new FileWrapper(destination);

		try
		{
			from.move(to, force);
		}
		catch( IOException ex )
		{
			final String message = "" + "Fatal Error Moving File:\n" + "Source = " + from.getAbsolutePath() + '\n'
				+ "Destination = " + to.getAbsolutePath() + '\n' + "Force = " + Boolean.toString(force);
			throw new InstallerException(message, ex);
		}

		propogateSubtaskCompleted();
		propogateTaskCompleted();
	}

	@Override
	public String toString()
	{
		return new String("Moving " + source + " to " + destination);
	}
}