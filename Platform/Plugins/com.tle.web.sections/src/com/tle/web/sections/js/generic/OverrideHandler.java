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

package com.tle.web.sections.js.generic;

import com.tle.web.sections.events.js.JSHandler;
import com.tle.web.sections.js.JSCallable;
import com.tle.web.sections.js.JSStatements;

/**
 * A handler which always returns false in order to stop further event handling.
 */
public class OverrideHandler extends StatementHandler
{
	public OverrideHandler()
	{
		// nothing
	}

	public OverrideHandler(JSStatements... statements)
	{
		super(statements);
	}

	public OverrideHandler(JSStatements statements)
	{
		super(statements);
	}

	public OverrideHandler(JSCallable callable, Object... args)
	{
		super(callable, args);
	}

	public OverrideHandler(JSHandler handler, JSStatements statements)
	{
		super(handler, statements);
	}

	@Override
	public boolean isOverrideDefault()
	{
		return true;
	}
}
