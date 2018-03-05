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

package com.tle.web.sections.jquery.libraries;

import com.tle.common.i18n.CurrentLocale;
import com.tle.core.javascript.JavascriptModule;
import com.tle.web.sections.jquery.JQueryLibraryInclude;
import com.tle.web.sections.jquery.JQueryStatement;
import com.tle.web.sections.js.ElementId;
import com.tle.web.sections.js.JSCallable;
import com.tle.web.sections.js.JSExpression;
import com.tle.web.sections.js.generic.expression.FunctionCallExpression;
import com.tle.web.sections.js.generic.function.ExternallyDefinedFunction;
import com.tle.web.sections.render.PreRenderable;

public class JQueryTreeView implements JavascriptModule
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("nls")
	public static final PreRenderable PRERENDER = new JQueryLibraryInclude(new String[]{"jquery.treeview.js",
			"jquery.treeview.async.js",}, "jquery.treeview.css", true);

	public static final JSCallable FUNC_TREEVIEW = new ExternallyDefinedFunction("treeview", PRERENDER); //$NON-NLS-1$

	public static JQueryStatement treeView(ElementId tag, JSExpression params)
	{
		return new JQueryStatement(tag, new FunctionCallExpression(FUNC_TREEVIEW, params));
	}

	@Override
	public String getDisplayName()
	{
		return CurrentLocale.get("com.tle.web.sections.jquery.modules.treeview.name"); //$NON-NLS-1$
	}

	@Override
	public String getId()
	{
		return "treeview"; //$NON-NLS-1$
	}

	@Override
	public Object getPreRenderer()
	{
		return PRERENDER;
	}
}
