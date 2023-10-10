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

public class CumulateAddition extends CumulateFunction<Number>
{
	public CumulateAddition(List<Object> alphabet, Picker<Boolean> coin)
	{
		super(alphabet, coin);
	}
	
	public CumulateAddition(List<Object> alphabet)
	{
		super(alphabet);
	}

	@Override
	protected Number getStartValue()
	{
		return 0;
	}

	@Override
	protected Number getNextOutputValue(Number previous, Number current)
	{
		int new_value = current.intValue() - previous.intValue();
		if (!m_alphabet.contains(new_value))
		{
			return null;
		}
		return new_value;
	}

	@Override
	protected Number getNextStoredValue(Number previous, Number next)
	{
		return previous.intValue() + next.intValue();
	}
}
