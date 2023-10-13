/*
    Inversion of function circuits
    Copyright (C) 2023 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.reversi;

import ca.uqac.lif.synthia.Picker;

public class CumulateAnd extends CumulateFunction
{
	public CumulateAnd(Picker<Boolean> coin, boolean wildcards)
	{
		super(null, coin, wildcards);
	}
	
	public CumulateAnd(boolean wildcards)
	{
		super(null, wildcards);
	}

	@Override
	protected Boolean getStartValue()
	{
		return Boolean.TRUE;
	}

	@Override
	protected Object getNextOutputValue(Object previous, Object current)
	{
		// Incorrect implementation; if previous is false, current can be either true or false
		if (Boolean.FALSE.equals(previous))
		{
			if (Boolean.TRUE.equals(current))
			{
				return null; // Impossible
			}
			return Boolean.FALSE;
		}
		return current;
	}

	@Override
	protected Object getNextStoredValue(Object previous, Object next)
	{
		//return Boolean.logicalAnd(previous, next);
	  return null; // TODO
	}
}
