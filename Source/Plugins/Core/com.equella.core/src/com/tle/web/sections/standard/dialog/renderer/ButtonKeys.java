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

package com.tle.web.sections.standard.dialog.renderer;

import com.tle.web.resources.PluginResourceHelper;
import com.tle.web.resources.ResourcesService;

@SuppressWarnings("nls")
public final class ButtonKeys
{
	private static final PluginResourceHelper KEY_HELPER = ResourcesService.getResourceHelper(ButtonKeys.class);

	public static final String OK = KEY_HELPER.key("stdbut.ok");
	public static final String SAVE = KEY_HELPER.key("stdbut.save");
	public static final String CANCEL = KEY_HELPER.key("stdbut.cancel");

	private ButtonKeys()
	{
		throw new Error();
	}
}
