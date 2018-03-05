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

package com.tle.web.sections.events;

import java.util.EventListener;

import com.tle.web.sections.SectionId;
import com.tle.web.sections.SectionInfo;

public class RespondingEvent extends AbstractSectionEvent<RespondingListener>
{

	@Override
	public void fire(SectionId sectionId, SectionInfo info, RespondingListener listener) throws Exception
	{
		listener.responding(info);
	}

	@Override
	public Class<? extends EventListener> getListenerClass()
	{
		return RespondingListener.class;
	}

}
