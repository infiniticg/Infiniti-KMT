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

package com.tle.web.api.interfaces.beans.security;

import java.util.List;

public class DynamicRuleBean
{
	private String name;
	private String path;
	private String type;
	private List<TargetListEntryBean> targetList;

	public String getName()
	{
		return name;
	}

	public DynamicRuleBean setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getPath()
	{
		return path;
	}

	public DynamicRuleBean setPath(String path)
	{
		this.path = path;
		return this;
	}

	public String getType()
	{
		return type;
	}

	public DynamicRuleBean setType(String type)
	{
		this.type = type;
		return this;
	}

	public List<TargetListEntryBean> getTargetList()
	{
		return targetList;
	}

	public DynamicRuleBean setTargetList(List<TargetListEntryBean> targetList)
	{
		this.targetList = targetList;
		return this;
	}
}
