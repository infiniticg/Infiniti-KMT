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

package com.tle.web.oauth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse
{
	private String error;
	private String errorDescription;
	private String errorUri;
	private String state;

	@JsonProperty("error")
	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	@JsonProperty("error_description")
	public String getErrorDescription()
	{
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription)
	{
		this.errorDescription = errorDescription;
	}

	public String getErrorUri()
	{
		return errorUri;
	}

	@JsonProperty("error_uri")
	public void setErrorUri(String errorUri)
	{
		this.errorUri = errorUri;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}
}