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

package com.tle.web.sections.js.generic;

import com.tle.annotation.NonNullByDefault;
import com.tle.web.sections.SectionInfo;
import com.tle.web.sections.SectionTree;
import com.tle.web.sections.SectionUtils;
import com.tle.web.sections.js.ElementId;

@NonNullByDefault
public class TreeUniqueId implements ElementId
{
	private String id;
	private boolean used;

	public TreeUniqueId(SectionTree tree)
	{
		id = "t" + SectionUtils.getTreeUniqueId(tree); //$NON-NLS-1$
	}

	@Override
	public String getElementId(SectionInfo info)
	{
		return id;
	}

	@Override
	public void registerUse()
	{
		used = true;
	}

	@Override
	public boolean isStaticId()
	{
		return true;
	}

	@Override
	public boolean isElementUsed()
	{
		return used;
	}

}
