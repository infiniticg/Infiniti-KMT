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

package com.tle.web.viewable.servlet;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.tle.core.guice.Bind;
import com.tle.core.services.user.UserSessionService;
import com.tle.web.viewable.PreviewableItem;
import com.tle.web.viewable.ViewableItem;

@Bind
@Singleton
public class PreviewServlet extends ItemServlet
{
	private static final long serialVersionUID = 1L;

	@Inject
	private UserSessionService sessionService;

	@Override
	protected ItemUrlParser getItemUrlParser()
	{
		return new NewItemUrlParser()
		{
			@Override
			public ViewableItem createViewableItem()
			{
				String uuid = itemId.getUuid();
				PreviewableItem previewableItem = sessionService.getAttribute(uuid);
				if( previewableItem != null )
				{
					ViewableItem viewableItem = previewableItem.getViewableItem();
					viewableItem.setFromRequest(true);
					return viewableItem;
				}
				return null;
			}
		};
	}
}
