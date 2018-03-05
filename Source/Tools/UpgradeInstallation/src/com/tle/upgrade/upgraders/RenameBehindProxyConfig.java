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

package com.tle.upgrade.upgraders;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.tle.upgrade.PropertyFileModifier;
import com.tle.upgrade.UpgradeDepends;
import com.tle.upgrade.UpgradeResult;

/**
 * Changes loginService.behindProxy to userService.useXForwardedFor
 */
@SuppressWarnings("nls")
public class RenameBehindProxyConfig extends AbstractUpgrader
{
	@Override
	public String getId()
	{
		return "RenameBehindProxyConfig";
	}

	@Override
	public List<UpgradeDepends> getDepends()
	{
		return Collections.emptyList();
	}

	@Override
	public boolean isBackwardsCompatible()
	{
		return false;
	}

	@Override
	public void upgrade(UpgradeResult result, File tleInstallDir) throws Exception
	{
		new PropertyFileModifier(new File(new File(tleInstallDir, CONFIG_FOLDER), PropertyFileModifier.OPTIONAL_CONFIG))
		{
			@Override
			protected boolean modifyProperties(PropertiesConfiguration props)
			{
				String v = props.getString("loginService.behindProxy");
				if( v == null )
				{
					return false;
				}

				props.setProperty("userService.useXForwardedFor", v);
				props.clearProperty("loginService.behindProxy");
				return true;
			}
		}.updateProperties();
	}
}
