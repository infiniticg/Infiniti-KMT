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

package com.tle.web.connectors.section;

import static com.tle.common.connectors.ConnectorConstants.PRIV_CREATE_CONNECTOR;
import static com.tle.common.connectors.ConnectorConstants.PRIV_EDIT_CONNECTOR;

import java.util.Arrays;

import javax.inject.Inject;

import com.tle.core.guice.Bind;
import com.tle.core.security.TLEAclManager;
import com.tle.web.entities.section.AbstractRootEntitySection;
import com.tle.web.sections.SectionInfo;
import com.tle.web.sections.equella.annotation.PlugKey;
import com.tle.web.sections.equella.layout.OneColumnLayout.OneColumnLayoutModel;
import com.tle.web.sections.render.Label;
import com.tle.web.sections.standard.model.HtmlLinkState;
import com.tle.web.settings.SettingsList;

@Bind
public class RootConnectorsSection extends AbstractRootEntitySection<OneColumnLayoutModel>
{
	@PlugKey("connectors.page.title")
	private static Label TITLE_LABEL;

	@Inject
	private TLEAclManager aclService;

	@Override
	public Class<OneColumnLayoutModel> getModelClass()
	{
		return OneColumnLayoutModel.class;
	}

	@Override
	protected boolean canView(SectionInfo info)
	{
		return !aclService.filterNonGrantedPrivileges(Arrays.asList(PRIV_EDIT_CONNECTOR, PRIV_CREATE_CONNECTOR))
			.isEmpty();
	}

	@Override
	protected Label getTitleLabel(SectionInfo info)
	{
		return TITLE_LABEL;
	}

	@Override
	protected HtmlLinkState getShowEntitiesLink(SectionInfo info)
	{
		return SettingsList.asLinkOrNull(SettingsList.connectorSettings());
	}
}
