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

package com.tle.web.viewurl;

import com.tle.web.sections.render.Label;
import com.tle.web.sections.render.LabelRenderer;
import com.tle.web.sections.render.SectionRenderable;

public class AttachmentDetail
{
	private final Label name;
	private final SectionRenderable description;

	public AttachmentDetail(Label name, Label description)
	{
		this(name, new LabelRenderer(description));
	}

	public AttachmentDetail(Label name, SectionRenderable description)
	{
		this.name = name;
		this.description = description;
	}

	public SectionRenderable getDescription()
	{
		return description;
	}

	public Label getName()
	{
		return name;
	}
}
