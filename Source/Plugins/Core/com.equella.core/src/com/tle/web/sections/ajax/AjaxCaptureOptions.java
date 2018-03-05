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

package com.tle.web.sections.ajax;

import java.util.Map;

public class AjaxCaptureOptions
{
	private final String ajaxId;
	private final boolean includeTag;
	private final boolean collection;
	private final Map<String, Object> params;

	public AjaxCaptureOptions(String ajaxId, boolean includeTag, boolean collection, Map<String, Object> params)
	{
		this.ajaxId = ajaxId;
		this.includeTag = includeTag;
		this.collection = collection;
		this.params = params;
	}

	public String getAjaxId()
	{
		return ajaxId;
	}

	public boolean isIncludeTag()
	{
		return includeTag;
	}

	public boolean isCollection()
	{
		return collection;
	}

	public Map<String, Object> getParams()
	{
		return params;
	}
}
