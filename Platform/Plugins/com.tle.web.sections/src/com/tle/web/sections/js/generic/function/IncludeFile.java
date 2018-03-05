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

package com.tle.web.sections.js.generic.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.tle.web.sections.events.PreRenderContext;
import com.tle.web.sections.render.PreRenderable;

public class IncludeFile implements PreRenderable
{
	protected List<String> includes = new ArrayList<String>();
	protected List<PreRenderable> preRenderables = new ArrayList<PreRenderable>();

	public IncludeFile()
	{
		// nothing
	}

	public IncludeFile(String include, PreRenderable... preRenderables)
	{
		this.includes.add(include);
		this.preRenderables.addAll(Arrays.asList(preRenderables));
	}

	public IncludeFile(String[] includes)
	{
		this.includes.addAll(Arrays.asList(includes));
	}

	public void addPreRenderers(Collection<PreRenderable> preRenderers)
	{
		preRenderables.addAll(preRenderers);
	}

	public void addPreRenderer(PreRenderable preRenderer)
	{
		preRenderables.add(preRenderer);
	}

	@Override
	public void preRender(PreRenderContext info)
	{
		info.preRender(preRenderables);
		for( String include : includes )
		{
			info.addJs(include);
		}
	}
}
