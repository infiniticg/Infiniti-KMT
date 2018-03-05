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

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dytech.devlib.PropBagEx;
import com.dytech.installer.InstallerException;

public class GHeading extends GuiControl
{
	public GHeading(PropBagEx controlBag) throws InstallerException
	{
		super(controlBag);
	}

	@Override
	public String getSelection()
	{
		return new String();
	}

	@Override
	public void generate(JPanel panel)
	{
		JLabel label = new JLabel(title);
		label.setFont(new Font("Arial", Font.BOLD, 30));

		panel.add(label);
	}

	@Override
	public JComponent generateControl()
	{
		return new JPanel();
	}
}
