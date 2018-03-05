/*****************************************************************************
 * Java Plug-in Framework (JPF) Copyright (C) 2007 Dmitry Olshansky This library
 * is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2.1 of the License, or (at your option) any later
 * version. This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details. You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *****************************************************************************/
package com.tle.jpfclasspath.parser;

/**
 * Extension point multiplicity constants.
 */
public enum ExtensionMultiplicity
{
	/**
	 * Extension multiplicity constant.
	 */
	ANY
	{
		/**
		 * @see org.java.plugin.registry.ExtensionMultiplicity#toCode()
		 */
		@Override
		public String toCode()
		{
			return "any"; //$NON-NLS-1$
		}
	},

	/**
	 * Extension multiplicity constant.
	 */
	ONE
	{
		/**
		 * @see org.java.plugin.registry.ExtensionMultiplicity#toCode()
		 */
		@Override
		public String toCode()
		{
			return "one"; //$NON-NLS-1$
		}
	},

	/**
	 * Extension multiplicity constant.
	 */
	ONE_PER_PLUGIN
	{
		/**
		 * @see org.java.plugin.registry.ExtensionMultiplicity#toCode()
		 */
		@Override
		public String toCode()
		{
			return "one-per-plugin"; //$NON-NLS-1$
		}
	},

	/**
	 * Extension multiplicity constant.
	 */
	NONE
	{
		/**
		 * @see org.java.plugin.registry.ExtensionMultiplicity#toCode()
		 */
		@Override
		public String toCode()
		{
			return "none"; //$NON-NLS-1$
		}
	};

	/**
	 * @return constant code to be used in plug-in manifest
	 */
	public abstract String toCode();

	/**
	 * Converts plug-in manifest string code to extension multiplicity constant
	 * value.
	 * 
	 * @param code code from plug-in manifest
	 * @return extension multiplicity constant value
	 */
	public static ExtensionMultiplicity fromCode(final String code)
	{
		for( ExtensionMultiplicity item : ExtensionMultiplicity.values() )
		{
			if( item.toCode().equals(code) )
			{
				return item;
			}
		}
		throw new IllegalArgumentException("unknown extension multiplicity code " + code); //$NON-NLS-1$
	}
}