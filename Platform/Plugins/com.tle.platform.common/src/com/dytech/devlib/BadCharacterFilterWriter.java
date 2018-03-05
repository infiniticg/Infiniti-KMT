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

package com.dytech.devlib;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Can only strip out invalid unicode chars as we see them... this means that
 * escaped invalid chars will slip through the cracks. Eg &amp;#x0B; Not as big
 * a deal as it seems, when WE read the chars back in, they will be removed. If
 * anyone else reads them in... unrucky.
 * 
 * @author aholland
 */
public class BadCharacterFilterWriter extends FilterWriter
{
	protected boolean didDiscard;
	private final Writer wrapped;

	public BadCharacterFilterWriter(final Writer writer)
	{
		super(writer);
		wrapped = writer;
	}

	@Override
	public void write(final String str) throws IOException
	{
		write(str.toCharArray());
	}

	@Override
	public void write(final int c) throws IOException
	{
		// invalid unicode char
		if( (c < 0x20 && c != 0x9 && c != 0xA && c != 0xD) || (c > 0xD7FF && c < 0xE000) || (c > 0xFFFD && c < 0x10000)
			|| c > 0x10FFFF )
		{
			// discard
			didDiscard = true;
		}
		else
		{
			wrapped.write(c);
		}
	}

	@Override
	public void write(final char[] cbuf, final int off, final int len) throws IOException
	{
		int writeLength = 0;
		final char[] buff = new char[len];
		for( int i = off; i < off + len; ++i )
		{
			final char c = cbuf[i];
			// invalid unicode char
			if( (c < 0x20 && c != 0x9 && c != 0xA && c != 0xD) || (c > 0xD7FF && c < 0xE000)
				|| (c > 0xFFFD && c < 0x10000) || c > 0x10FFFF )
			{
				// discard
				didDiscard = true;
			}
			else
			{
				buff[writeLength++] = c;
			}
		}
		if( writeLength > 0 )
		{
			wrapped.write(buff, 0, writeLength);
		}
	}

	public boolean didDiscard()
	{
		return didDiscard;
	}
}
