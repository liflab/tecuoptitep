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

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.sequence.Playback;

/**
 * An unary variant of {@link Equals} that checks equality against a constant.
 */
public class EqualsConstant extends UnaryComparison<Object>
{
	public EqualsConstant(Object constant, List<Object> alphabet, Picker<Boolean> coin)
	{
		super(constant, alphabet, coin);
	}
	
	public EqualsConstant(Object constant, List<Object> alphabet)
	{
		super(constant, alphabet);
	}

	@Override
	protected Bounded<?> getPickerForInput(boolean o)
	{
		if (Boolean.TRUE.equals(o))
		{
			return new Playback<Object>(m_constant).setLoop(false);
		}
		return new Playback<Object>(0, getAlphabetWithout(m_constant)).setLoop(false);
	}
	
	protected List<Object> getAlphabetWithout(Object o)
	{
		List<Object> new_alph = new ArrayList<Object>(m_alphabet.size() - 1);
		for (Object a : m_alphabet)
		{
			if (!o.equals(a))
			{
				new_alph.add(a);
			}
		}
		return new_alph;
	}
}
