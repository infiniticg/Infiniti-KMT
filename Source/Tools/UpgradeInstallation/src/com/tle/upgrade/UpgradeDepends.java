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

package com.tle.upgrade;

public class UpgradeDepends
{
	private final String id;
	private boolean obsoletes;
	private boolean fixes;

	public UpgradeDepends(String id)
	{
		this.id = id;
	}

	public boolean isObsoletes()
	{
		return obsoletes;
	}

	public void setObsoletes(boolean obsoletes)
	{
		this.obsoletes = obsoletes;
	}

	public boolean isFixes()
	{
		return fixes;
	}

	public void setFixes(boolean fixes)
	{
		this.fixes = fixes;
	}

	public String getId()
	{
		return id;
	}
}