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

package com.dytech.installer.controls;

import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import com.dytech.devlib.PropBagEx;
import com.dytech.installer.InstallerException;
import com.dytech.installer.Item;

public class GRadioButtonGroup extends GAbstractButtonGroup
{
	public GRadioButtonGroup(PropBagEx controlBag) throws InstallerException
	{
		super(controlBag);
	}

	@Override
	public AbstractButton generateButton(String name, ButtonGroup group)
	{
		AbstractButton button = new JRadioButton(name);
		group.add(button);
		return button;
	}

	@Override
	public String getSelection()
	{
		Iterator i = items.iterator();
		while( i.hasNext() )
		{
			Item item = (Item) i.next();
			if( item.getButton().isSelected() )
				return item.getValue();
		}

		// We should hopefully never reach here.
		// Maybe we should throw an exception?
		return new String();
	}
}