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

package com.tle.web.api.item.equella.interfaces.beans;

import com.tle.common.interfaces.I18NString;

public class DisplayField
{
    private final String type;
    private final I18NString name;
    private final I18NString html;

    public DisplayField(String type, I18NString name, I18NString html)
    {
        this.type = type;
        this.name = name;
        this.html = html;
    }

    public I18NString getName()
    {
        return name;
    }

    public I18NString getHtml()
    {
        return html;
    }

    public String getType()
    {
        return type;
    }
}
