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

package com.tle.web.sections.equella.annotation;

import javax.inject.Singleton;

import com.tle.web.sections.Section;
import com.tle.web.sections.SectionTree;
import com.tle.web.sections.registry.handler.CachedScannerHandler;

@Singleton
public class PluginResourceHandler extends CachedScannerHandler<AnnotatedPlugResourceScanner>
{
	private static PluginResourceHandler me;

	public PluginResourceHandler()
	{
		me = this; // NOSONAR
	}

	public static PluginResourceHandler inst()
	{
		return me;
	}

	@Override
	protected AnnotatedPlugResourceScanner newEntry(Class<?> clazz)
	{
		return new AnnotatedPlugResourceScanner(clazz, this);
	}

	@Override
	public void registered(String id, SectionTree tree, Section section)
	{
		getForClass(section.getClass()).setupLabels(section);
	}

	public static void init(Class<?> callerClass)
	{
		inst().getForClass(callerClass);
	}
}
