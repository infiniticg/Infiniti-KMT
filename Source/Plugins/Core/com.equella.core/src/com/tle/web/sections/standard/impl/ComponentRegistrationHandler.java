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

package com.tle.web.sections.standard.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.tle.core.guice.Bind;
import com.tle.web.sections.Section;
import com.tle.web.sections.SectionTree;
import com.tle.web.sections.registry.handler.CachedScannerHandler;
import com.tle.web.sections.standard.ComponentFactory;

@Bind
@Singleton
public class ComponentRegistrationHandler extends CachedScannerHandler<ComponentScanner>
{
	@Inject
	private ComponentFactory factory;

	@Override
	protected ComponentScanner newEntry(Class<?> clazz)
	{
		return new ComponentScanner(clazz, this);
	}

	@Override
	public void registered(String id, SectionTree tree, Section section)
	{
		ComponentScanner scanner = getForClass(section.getClass());
		scanner.registerComponents(id, tree, section, factory);
	}
}
