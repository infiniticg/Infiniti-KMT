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

package com.dytech.edge.common;

@SuppressWarnings("nls")
public final class Constants
{
	// MDC LOGGING KEYS //////
	public static final String MDC_SESSION_ID = "SessionID";
	public static final String MDC_ITEM_ID = "ItemID";

	public static final String APPLET_SECRET_ID = "APPLET";

	// really so we don't need ugly NON-NLS tags everywhere
	public static final String BLANK = "";
	public static final String SLASH = "/";
	public static final String SINGLE_QUOTE = "'";
	public static final String DOUBLE_QUOTE = "\"";

	public static final String UTF8 = "UTF-8";

	public static final String XML_TRUE = "true";
	public static final String XML_FALSE = "false";
	public static final String XML_WILD = "*";
	public static final String XML_ROOT = SLASH;

	// we need to make *some* assumptions about the folder structure
	public static final String LEARNINGEDGE_CONFIG_FOLDER = "learningedge-config";
	public static final String MANAGER_FOLDER = "manager";

	public static final String UPGRADE_LOCK = "equella.lock";

	private Constants()
	{
		throw new Error();
	}
}
