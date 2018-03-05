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

import javax.inject.Singleton;

import com.tle.core.guice.Bind;
import com.tle.web.viewable.NewDefaultViewableItem;
import com.tle.web.viewable.NewViewableItemState;
import com.tle.web.viewable.ViewableItem;

@Bind
@Singleton
public class IntegrationItemServlet extends ItemServlet
{
	private boolean requireDRM = true;
	private boolean forceLatest;

	public boolean isForceLatest()
	{
		return forceLatest;
	}

	public void setForceLatest(boolean forceLatest)
	{
		this.forceLatest = forceLatest;
	}

	public void setRequireDRM(boolean requireDRM)
	{
		this.requireDRM = requireDRM;
	}

	@Override
	protected ItemUrlParser getItemUrlParser()
	{
		return new IntegrationUrlParser();
	}

	public class IntegrationUrlParser extends ItemServlet.NewItemUrlParser
	{
		private String extra;

		@Override
		public ViewableItem createViewableItem()
		{
			NewDefaultViewableItem viewableItem = (NewDefaultViewableItem) super.createViewableItem();
			NewViewableItemState state = viewableItem.getState();
			state.setRequireDRM(requireDRM);
			state.setItemId(itemId);
			state.setContext(context);
			state.setIntegrationType(extra);
			return viewableItem;
		}

		@Override
		protected void setupContext()
		{
			extra = partList.get(0);
			context = request.getServletPath().substring(1) + '/' + extra + '/';
			partList = partList.subList(1, partList.size());
		}

		@Override
		protected void checkForRedirect()
		{
			if( forceLatest )
			{
				redirToLatest();
				return;
			}
			super.checkForRedirect();
		}
	}
}
