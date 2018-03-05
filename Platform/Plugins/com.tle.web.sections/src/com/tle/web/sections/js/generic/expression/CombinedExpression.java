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

package com.tle.web.sections.js.generic.expression;

import com.tle.web.sections.events.PreRenderContext;
import com.tle.web.sections.events.RenderContext;
import com.tle.web.sections.js.JSExpression;

public class CombinedExpression extends AbstractExpression
{
	private JSExpression first;
	private JSExpression second;

	public CombinedExpression(JSExpression first, JSExpression second)
	{
		this.first = first;
		this.second = second;
	}

	@Override
	public String getExpression(RenderContext info)
	{
		return first.getExpression(info) + second.getExpression(info);
	}

	@Override
	public void preRender(PreRenderContext info)
	{
		info.preRender(first, second);
	}
}
