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

package com.tle.web.sections.standard.renderers;

import com.tle.web.resources.PluginResourceHelper;
import com.tle.web.resources.ResourcesService;
import com.tle.web.sections.events.PreRenderContext;
import com.tle.web.sections.jquery.libraries.JQueryCore;
import com.tle.web.sections.jquery.libraries.JQueryTimer;
import com.tle.web.sections.js.generic.function.ExternallyDefinedFunction;
import com.tle.web.sections.render.PreRenderable;

/*
 * @author aholland
 */
@SuppressWarnings("nls")
public final class ProgressRenderer implements PreRenderable
{
	public static final ProgressRenderer PRE_RENDERER = new ProgressRenderer(true);
	public static final ProgressRenderer PRE_RENDERER_NOSTYLE = new ProgressRenderer(false);

	public static final ExternallyDefinedFunction SHOW_PROGRESS_FUNCTION = new ExternallyDefinedFunction(
		"showProgress", ProgressRenderer.PRE_RENDERER);
	public static final ExternallyDefinedFunction SHOW_PROGRESS_FUNCTION_NOSTYLE = new ExternallyDefinedFunction(
		"showProgress", ProgressRenderer.PRE_RENDERER_NOSTYLE);

	public static final ExternallyDefinedFunction WEBKIT_PROGRESS_FRAME = new ExternallyDefinedFunction(
		"setupWebkitFrame", ProgressRenderer.PRE_RENDERER_NOSTYLE);

	public static final ExternallyDefinedFunction UNREGISTER_UPLOAD = new ExternallyDefinedFunction("unregisterUpload",
		ProgressRenderer.PRE_RENDERER_NOSTYLE);

	private static final PluginResourceHelper URL_HELPER = ResourcesService.getResourceHelper(ProgressRenderer.class);

	private final boolean styles;

	private ProgressRenderer(boolean includeStyles)
	{
		this.styles = includeStyles;
	}

	@Override
	public void preRender(PreRenderContext info)
	{
		info.preRender(JQueryCore.PRERENDER, JQueryTimer.PRERENDER);
		info.addJs(URL_HELPER.url("jquerylib/jquery.progression.js"));
		info.addJs(URL_HELPER.url("js/upload.js"));
		if( styles )
		{
			info.addCss(URL_HELPER.url("css/uploadprogress.css"));
		}
	}
}
