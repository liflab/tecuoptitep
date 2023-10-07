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
package inversionlab;

import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class RandomListPicker implements Picker<MathList<Object>>
{
	protected final Picker<Integer> m_length;

	protected final Picker<Object> m_symbol;

	public RandomListPicker(Picker<Integer> len, Picker<Object> symbol)
	{
		super();
		m_length = len;
		m_symbol = symbol;
	}

	@Override
	public Picker<MathList<Object>> duplicate(boolean arg0)
	{
		throw new UnsupportedOperationException("Cannot duplicate this picker");
	}

	@Override
	public MathList<Object> pick()
	{
		int len = m_length.pick();
		MathList<Object> out = new MathList<Object>();
		for (int i = 0; i < len; i++)
		{
			out.add(m_symbol.pick());
		}
		return out;
	}

	@Override
	public void reset()
	{
		m_length.reset();
		m_symbol.reset();
	}
}