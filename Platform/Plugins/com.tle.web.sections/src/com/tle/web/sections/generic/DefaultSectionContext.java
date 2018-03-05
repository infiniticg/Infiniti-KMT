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

package com.tle.web.sections.generic;

import com.tle.annotation.NonNullByDefault;
import com.tle.web.sections.MutableSectionInfo;
import com.tle.web.sections.Section;
import com.tle.web.sections.SectionContext;
import com.tle.web.sections.SectionInfo;
import com.tle.web.sections.SectionTree;

@NonNullByDefault
public class DefaultSectionContext extends WrappedSectionInfo implements SectionContext
{
	private final String id;
	private final Section section;
	private final SectionTree tree;

	public DefaultSectionContext(Section section, MutableSectionInfo info, SectionTree tree, String id)
	{
		super(info);
		this.id = id;
		this.section = section;
		this.tree = tree;
		this.info = info;
	}

	@Override
	public String getSectionId()
	{
		return id;
	}

	@Override
	public SectionTree getTree()
	{
		return tree;
	}

	public String getId()
	{
		return id;
	}

	@Override
	public SectionInfo getInfo()
	{
		return info;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T getModel()
	{
		return (T) info.getModelForId(id);
	}

	@Override
	public Section getSectionObject()
	{
		return section;
	}

	@Override
	public Section getSection()
	{
		return section;
	}
}
