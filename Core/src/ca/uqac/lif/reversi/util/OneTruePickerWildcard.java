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
package ca.uqac.lif.reversi.util;

import ca.uqac.lif.reversi.AlphabetFunction;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

public class OneTruePickerWildcard implements Picker<MathList<Object>>
{
	protected final Picker<Integer> m_length;

	protected final Picker<Boolean> m_coin;

	public OneTruePickerWildcard(Picker<Integer> len, Picker<Boolean> coin)
	{
		super();
		m_length = len;
		m_coin = coin;
	}

	public OneTruePickerWildcard(int len, Picker<Boolean> coin)
	{
		this(new Constant<Integer>(len), coin);
	}

	@Override
	public void reset()
	{
		m_length.reset();
	}

	@Override
	public MathList<Object> pick()
	{
		MathList<Object> list = new MathList<Object>();
		int len = m_length.pick();
		boolean has_true = false;
		for (int i = 0; i < len; i++)
		{
			/*if (has_true)
			{
				list.add(AlphabetFunction.WILDCARD);
			}
			else*/
			{
				boolean b = m_coin.pick();
				list.add(b ? true : AlphabetFunction.WILDCARD);
				has_true = has_true || b;
			}
		}
		if (!has_true)
		{
			list.set(len - 1, true);
		}
		return list;
	}

	@Override
	public OneTruePickerWildcard duplicate(boolean with_state)
	{
		return new OneTruePickerWildcard(m_length.duplicate(with_state), m_coin.duplicate(with_state));
	}
}
