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

package com.tle.web.viewitem.summary.sidebar.summary;

import com.tle.beans.workflow.WorkflowStatus;
import com.tle.web.sections.SectionInfo;
import com.tle.web.sections.annotations.TreeLookup;
import com.tle.web.sections.equella.annotation.PlugKey;
import com.tle.web.sections.render.Label;
import com.tle.web.viewitem.summary.content.TermsOfUseContentSection;
import com.tle.web.viewitem.summary.section.ItemSummaryContentSection;
import com.tle.web.viewitem.summary.sidebar.actions.GenericMinorActionSection;
import com.tle.web.viewurl.ItemSectionInfo;

public class TermsOfUseLinkSection extends GenericMinorActionSection
{
	@PlugKey("summary.sidebar.summary.termsofuse.title")
	private static Label LINK_LABEL;

	@TreeLookup
	private ItemSummaryContentSection contentSection;
	@TreeLookup
	private TermsOfUseContentSection termsOfUseSection;

	public TermsOfUseLinkSection()
	{
		setShowForPreview(true);
	}

	@Override
	protected Label getLinkLabel()
	{
		return LINK_LABEL;
	}

	@Override
	protected boolean canView(SectionInfo info, ItemSectionInfo itemInfo, WorkflowStatus status)
	{
		return itemInfo.getItem().getDrmSettings() != null;
	}

	@Override
	protected void execute(SectionInfo info)
	{
		contentSection.setSummaryId(info, termsOfUseSection);
	}

	@Override
	public String getLinkText()
	{
		return LINK_LABEL.getText();
	}
}
