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

package com.tle.core.adminconsole

import javax.inject.Inject

import com.tle.common.adminconsole.RemoteAdminService
import com.tle.core.application.StartupBean
import com.tle.core.guice.Bind
import com.tle.web.resources.ResourcesService
import com.tle.web.settings.{SettingsList, SettingsPage}

@Bind
class AdminConsoleExtensions extends StartupBean {
  @Inject
  var adminService : RemoteAdminService = _

  override def startup(): Unit = {
    SettingsList += SettingsPage(ResourcesService.getResourceHelper(getClass),
      "adminconsole", "general", "admin.link.title", "admin.link.description",
      "jnlp/admin.jnlp", "web", () => !adminService.getAllowedTools.isEmpty)
  }
}
