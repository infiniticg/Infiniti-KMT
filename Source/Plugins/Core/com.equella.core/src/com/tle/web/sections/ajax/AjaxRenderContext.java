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

import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import com.tle.web.sections.events.BookmarkEvent;
import com.tle.web.sections.events.PreRenderContext;
import com.tle.web.sections.render.PreRenderable;

public interface AjaxRenderContext extends PreRenderContext
{
	Writer startCapture(Writer out, String divId, Map<String, Object> captureParams, boolean collection);

	void endCapture(String divId);

	FullDOMResult getFullDOMResult();

	void setFormBookmarkEvent(BookmarkEvent event);

	void setJSONResponseCallback(JSONResponseCallback jsonResponseCallback);

	void addAjaxDivs(String... divIds);

	void addAjaxDivs(Collection<String> ajaxIds);

	boolean isCurrentlyCapturing();

	boolean isRenderingAjaxDiv(String divId);

	Map<String, FullAjaxCaptureResult> getAllCaptures();

	void captureResources(PreRenderable... preRenderables);

}
