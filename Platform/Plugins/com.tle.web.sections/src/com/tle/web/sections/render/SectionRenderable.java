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

package com.tle.web.sections.render;

import java.io.IOException;

import com.tle.annotation.NonNullByDefault;
import com.tle.web.sections.SectionWriter;

@NonNullByDefault
public interface SectionRenderable extends PreRenderable
{
	/**
	 * Render this result to the given writer. <b>This method should have no
	 * side effects.</b> You should be able to render a result as many times as
	 * you want.
	 * 
	 * @param writer
	 * @throws IOException
	 */
	void realRender(SectionWriter writer) throws IOException;
}
