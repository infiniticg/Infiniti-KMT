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

package com.tle.web.manualdatafixes;

import com.tle.core.services.TaskStatus;
import com.tle.web.sections.render.Label;

public abstract class ManualDataFixModel
{
	protected boolean checkedStatus;
	protected boolean inProgress;
	protected TaskStatus taskStatus;
	protected Label taskLabel;

	public boolean isInProgress()
	{
		return inProgress;
	}

	public void setInProgress(boolean inProgress)
	{
		this.inProgress = inProgress;
	}

	public abstract TaskStatus getTaskStatus();

	public Label getTaskLabel()
	{
		return taskLabel;
	}

	public void setTaskLabel(Label taskLabel)
	{
		this.taskLabel = taskLabel;
	}

	public boolean isCheckedStatus()
	{
		return checkedStatus;
	}

	public void setCheckedStatus(boolean checkedStatus)
	{
		this.checkedStatus = checkedStatus;
	}
}
