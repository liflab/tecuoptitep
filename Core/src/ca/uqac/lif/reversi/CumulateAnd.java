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

import java.util.List;

import ca.uqac.lif.synthia.Picker;

public class CumulateAnd extends CumulateFunction<Boolean>
{
	public CumulateAnd(List<Object> alphabet, Picker<Boolean> coin)
	{
		super(alphabet, coin);
	}
	
	public CumulateAnd(List<Object> alphabet)
	{
		super(alphabet);
	}

	@Override
	protected Boolean getStartValue()
	{
		return Boolean.TRUE;
	}

	@Override
	protected Boolean getNextOutputValue(Boolean previous, Boolean current)
	{
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
	protected Boolean getNextStoredValue(Boolean previous, Boolean next)
	{
		return Boolean.logicalAnd(previous, next);
	}
}