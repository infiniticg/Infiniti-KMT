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

package com.tle.web.sections.standard.renderers.toggle;

import java.io.IOException;

import com.tle.web.sections.SectionWriter;
import com.tle.web.sections.standard.model.HtmlBooleanState;

public class TextTogglerRenderer extends AbstractHiddenToggler
{
	private String checkedText;
	private String uncheckedText;

	public TextTogglerRenderer(HtmlBooleanState state)
	{
		super(state);
	}

	public void setCheckedText(String checkedText)
	{
		this.checkedText = checkedText;
	}

	public void setUncheckedText(String uncheckedText)
	{
		this.uncheckedText = uncheckedText;
	}

	@Override
	protected void writeMiddle(SectionWriter writer) throws IOException
	{
		if( bstate.isChecked() )
		{
			writer.write(checkedText);
		}
		else
		{
			writer.write(uncheckedText);
		}
	}
}
