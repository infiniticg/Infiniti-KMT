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

public class TargetListEntryBean
{
	private boolean granted;
	private boolean override;
	private String privilege;
	private String who;

	public boolean isGranted()
	{
		return granted;
	}

	public TargetListEntryBean setGranted(boolean granted)
	{
		this.granted = granted;
		return this;
	}

	public boolean isOverride()
	{
		return override;
	}

	public TargetListEntryBean setOverride(boolean override)
	{
		this.override = override;
		return this;
	}

	public String getPrivilege()
	{
		return privilege;
	}

	public TargetListEntryBean setPrivilege(String privilege)
	{
		this.privilege = privilege;
		return this;
	}

	public String getWho()
	{
		return who;
	}

	public TargetListEntryBean setWho(String who)
	{
		this.who = who;
		return this;
	}
}
