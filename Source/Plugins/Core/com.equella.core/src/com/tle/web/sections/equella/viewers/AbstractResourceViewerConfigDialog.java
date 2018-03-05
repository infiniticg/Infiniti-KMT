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

package com.tle.web.sections.equella.viewers;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.tle.annotation.NonNullByDefault;
import com.tle.web.sections.SectionTree;
import com.tle.web.sections.equella.dialog.FormDialog;
import com.tle.web.sections.equella.render.ButtonRenderer.ButtonType;
import com.tle.web.sections.events.RenderContext;
import com.tle.web.sections.js.JSCallable;
import com.tle.web.sections.js.generic.OverrideHandler;
import com.tle.web.sections.result.util.KeyLabel;
import com.tle.web.sections.standard.Button;
import com.tle.web.sections.standard.annotations.Component;
import com.tle.web.sections.standard.dialog.renderer.ButtonKeys;
import com.tle.web.sections.standard.js.JSONComponentMappings;
import com.tle.web.sections.standard.js.impl.CollectJSONFunction;
import com.tle.web.sections.standard.js.impl.PopulateFromJSONFunction;
import com.tle.web.viewurl.ResourceViewerConfigDialog;

@NonNullByDefault
public abstract class AbstractResourceViewerConfigDialog extends FormDialog implements ResourceViewerConfigDialog
{
	@Component
	private Button okButton;
	@Component
	private Button cancelButton;

	private CollectJSONFunction collectFunction;
	protected PopulateFromJSONFunction populateFunction;
	protected JSONComponentMappings mappings;

	@Override
	public void registered(String id, SectionTree tree)
	{
		super.registered(id, tree);

		setInline(true);

		okButton.setLabel(new KeyLabel(ButtonKeys.OK));
		okButton.setComponentAttribute(ButtonType.class, ButtonType.SAVE);

		cancelButton.setLabel(new KeyLabel(ButtonKeys.CANCEL));
		cancelButton.setClickHandler(new OverrideHandler(getCloseFunction()));

		mappings = new JSONComponentMappings();
	}

	@Override
	@SuppressWarnings("nls")
	public void treeFinished(String id, SectionTree tree)
	{
		super.treeFinished(id, tree);
		collectFunction = new CollectJSONFunction("collect" + id, mappings);
		populateFunction = new PopulateFromJSONFunction("populate" + id, mappings);
	}

	@Override
	protected Collection<Button> collectFooterActions(RenderContext context)
	{
		return Lists.newArrayList(okButton, cancelButton);
	}

	@Override
	public JSCallable getCollectFunction()
	{
		return collectFunction;
	}

	@Override
	public JSCallable getPopulateFunction()
	{
		return populateFunction;
	}

	@Override
	public Button getOkButton()
	{
		return okButton;
	}
}