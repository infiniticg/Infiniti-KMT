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

package com.tle.web.appletcommon.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProgressMonitorInputStream extends FilterInputStream
{
	private final ProgressMonitorCallback callback;

	public ProgressMonitorInputStream(InputStream delegate, ProgressMonitorCallback callback)
	{
		super(delegate);
		this.callback = callback;
	}

	@Override
	public int read() throws IOException
	{
		int result = super.read();
		if( result != -1 )
		{
			callback.addToProgress(1);
		}
		return result;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		int result = super.read(b, off, len);
		if( result > 0 )
		{
			callback.addToProgress(result);
		}
		return result;
	}
}
