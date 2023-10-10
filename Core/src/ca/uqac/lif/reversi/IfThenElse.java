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

import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.enumerative.AllPickers;
import ca.uqac.lif.synthia.enumerative.Merge;
import ca.uqac.lif.synthia.sequence.Playback;

/**
 * Reversible function shadowing BeepBeep's.
 */
public class IfThenElse extends ApplyFunction
{
	protected final List<Object> m_alphabet;
	
	public IfThenElse(List<Object> alphabet, Picker<Boolean> coin)
	{
		super(3, coin);
		m_alphabet = alphabet;
	}
	
	public IfThenElse(List<Object> alphabet)
	{
		super(3);
		m_alphabet = alphabet;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<MathList<Object>> getInputValuesFor(Object o)
	{
		Merge<Object[]> merge = new Merge<Object[]>(
				// First case: 1st input is true, 2nd input is o, and 3rd input is anything
				new AllPickers(new Bounded[] {
						new Playback<Boolean>(true).setLoop(false),
						new Playback<Object>(o).setLoop(false),
						new Playback<Object>(0, m_alphabet).setLoop(false)}), // 0 is important, otherwise wrong constructor is called
				// Second case: 1st input is false, 2nd input is anything, and 3rd input is o
				new AllPickers(new Bounded[] {
						new Playback<Boolean>(false).setLoop(false),
						new Playback<Object>(0, m_alphabet).setLoop(false), // 0 is important, otherwise wrong constructor is called
						new Playback<Object>(o).setLoop(false)})
		);
		List<MathList<Object>> ins = new ArrayList<MathList<Object>>();
		while (!merge.isDone())
		{
			Object[] tuple = merge.pick();
			MathList<Object> l_tuple = new MathList<Object>();
			for (Object x : tuple)
			{
				l_tuple.add(x);
			}
			ins.add(l_tuple);
		}
		return ins;
	}
}
