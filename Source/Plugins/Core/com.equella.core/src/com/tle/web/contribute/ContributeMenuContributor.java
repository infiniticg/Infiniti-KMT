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

package com.tle.web.contribute;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.tle.core.collection.service.ItemDefinitionService;
import com.tle.core.guice.Bind;
import com.tle.core.services.user.UserSessionService;
import com.tle.web.resources.ResourcesService;
import com.tle.web.sections.SectionInfo;
import com.tle.web.sections.render.Label;
import com.tle.web.sections.result.util.KeyLabel;
import com.tle.web.sections.standard.model.HtmlLinkState;
import com.tle.web.sections.standard.model.SimpleBookmark;
import com.tle.web.template.section.MenuContributor;

@Bind
@Singleton
@SuppressWarnings("nls")
public class ContributeMenuContributor implements MenuContributor
{
	private static final Label LABEL_KEY = new KeyLabel(
		ResourcesService.getResourceHelper(ContributeMenuContributor.class).key("contribute.menu"));
	private static final String ICON_PATH = ResourcesService.getResourceHelper(ContributeMenuContributor.class)
		.url("images/menu-icon-contribute.png");
	private static final String SESSION_KEY = "CONTRIBUTE-MENU";

	@Inject
	private UserSessionService userSessionService;
	@Inject
	private ItemDefinitionService itemDefinitionService;

	@Override
	public List<MenuContribution> getMenuContributions(SectionInfo info)
	{
		Boolean b = (Boolean) userSessionService.getAttribute(SESSION_KEY);
		if( b == null )
		{
			b = !itemDefinitionService.listCreateable().isEmpty();
			userSessionService.setAttribute(SESSION_KEY, b);
		}

		if( !b.booleanValue() )
		{
			return Collections.emptyList();
		}

		// TODO: We should be generating a bookmark to the section rather than
		// hard-coding the URL. If there is only one wizard, we should go
		// straight to it, like what we do in ContributeSelectable.

		HtmlLinkState hls = new HtmlLinkState(new SimpleBookmark("access/contribute.do"));
		hls.setLabel(LABEL_KEY);
		MenuContribution mc = new MenuContribution(hls, ICON_PATH, 1, 30, "create");
		return Collections.singletonList(mc);
	}

	@Override
	public void clearCachedData()
	{
		userSessionService.removeAttribute(SESSION_KEY);
	}
}
