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
import ca.uqac.lif.synthia.enumerative.AllElements;

/**
 * An unary function that checks if a value is greater than or equal to a
 * constant.
 */
public class GreaterOrEqualConstant extends UnaryComparison<Integer>
{
	protected final List<Integer> m_falseRange;
	
	protected final List<Integer> m_trueRange;
	
	public GreaterOrEqualConstant(Integer constant, List<Object> alphabet, Picker<Boolean> coin, boolean wildcards)
	{
		super(constant, alphabet, coin, wildcards);
		m_falseRange = getFalseRange();
		m_trueRange = getTrueRange();
	}
	
	public GreaterOrEqualConstant(Integer constant, List<Object> alphabet, boolean wildcards)
	{
		super(constant, alphabet, wildcards);
		m_falseRange = getFalseRange();
		m_trueRange = getTrueRange();
	}
	
	@Override
	protected Bounded<?> getPickerForInput(boolean b)
	{
		if (b)
		{
			return new AllElements<Integer>(m_trueRange, true, false);
		}
		return new AllElements<Integer>(m_falseRange, true, false);
	}
	
	protected List<Integer> getTrueRange()
	{
		List<Integer> new_alph = new ArrayList<Integer>();
		for (Object a : m_alphabet)
		{
			if (((Number) a).intValue() >= m_constant.intValue())
			{
				new_alph.add((Integer) a);
			}
		}
		return new_alph;
	}
	
	protected List<Integer> getFalseRange()
	{
		List<Integer> new_alph = new ArrayList<Integer>();
		for (Object a : m_alphabet)
		{
			if (((Number) a).intValue() < m_constant.intValue())
			{
				new_alph.add((Integer) a);
			}
		}
		return new_alph;
	}
}
