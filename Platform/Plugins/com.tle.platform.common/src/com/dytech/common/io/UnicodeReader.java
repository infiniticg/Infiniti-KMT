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

package com.dytech.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;

/**
 * Generic unicode textreader, which will use BOM mark to identify the encoding
 * to be used.
 */
public class UnicodeReader extends Reader
{
	private static final int BOM_SIZE = 4;

	private PushbackInputStream internalIn;
	private InputStreamReader internalIn2 = null;
	private String defaultEnc;

	public UnicodeReader(InputStream in, String defaultEnc)
	{
		internalIn = new PushbackInputStream(in, BOM_SIZE);
		this.defaultEnc = defaultEnc;
	}

	public String getDefaultEncoding()
	{
		return defaultEnc;
	}

	public String getEncoding()
	{
		if( internalIn2 == null )
		{
			return null;
		}
		return internalIn2.getEncoding();
	}

	/**
	 * Read-ahead four bytes and check for BOM marks. Extra bytes are unread
	 * back to the stream, only BOM bytes are skipped.
	 */
	@SuppressWarnings("nls")
	protected void init() throws IOException
	{
		if( internalIn2 != null )
		{
			return;
		}

		String encoding;
		int unread;
		byte bom[] = new byte[BOM_SIZE];
		int n = internalIn.read(bom, 0, bom.length);

		if( (bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB) && (bom[2] == (byte) 0xBF) )
		{
			encoding = "UTF-8";
			unread = n - 3;
		}
		else if( (bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF) )
		{
			encoding = "UTF-16BE";
			unread = n - 2;
		}
		else if( (bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE) )
		{
			encoding = "UTF-16LE";
			unread = n - 2;
		}
		else if( (bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00) && (bom[2] == (byte) 0xFE)
			&& (bom[3] == (byte) 0xFF) )
		{
			encoding = "UTF-32BE";
			unread = n - 4;
		}
		else if( (bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE) && (bom[2] == (byte) 0x00)
			&& (bom[3] == (byte) 0x00) )
		{
			encoding = "UTF-32LE";
			unread = n - 4;
		}
		else
		{
			// Unicode BOM mark not found, unread all bytes
			encoding = defaultEnc;
			unread = n;
		}

		if( unread > 0 )
		{
			internalIn.unread(bom, (n - unread), unread);
		}
		else if( unread < -1 )
		{
			internalIn.unread(bom, 0, 0);
			// Use given encoding
		}
		if( encoding == null )
		{
			internalIn2 = new InputStreamReader(internalIn);
		}
		else
		{
			internalIn2 = new InputStreamReader(internalIn, encoding);
		}
	}

	@Override
	public void close() throws IOException
	{
		init();
		internalIn2.close();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException
	{
		init();
		return internalIn2.read(cbuf, off, len);
	}
}