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

package com.tle.client.impl;

import java.util.TimeZone;

import com.tle.annotation.NonNullByDefault;
import com.tle.common.i18n.CurrentTimeZone.AbstractCurrentTimeZone;

/**
 * @author aholland
 */
@NonNullByDefault
public class CurrentTimeZoneClientSide extends AbstractCurrentTimeZone
{
	private final TimeZone zone;

	public CurrentTimeZoneClientSide(TimeZone zone)
	{
		this.zone = zone;
	}

	@Override
	public TimeZone get()
	{
		return zone;
	}
}
