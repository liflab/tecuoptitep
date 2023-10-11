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
import ca.uqac.lif.synthia.enumerative.AllElements;
import ca.uqac.lif.synthia.enumerative.AllPickers;
import ca.uqac.lif.synthia.sequence.Playback;

public abstract class ApplyFunction extends ReversibleFunction
{
	public ApplyFunction(int in_arity)
	{
		super(in_arity);
	}
	
	public ApplyFunction(int in_arity, Picker<Boolean> coin)
	{
		super(in_arity, coin);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSuggestions()
	{
		for (int i = 0; i < getInputArity(); i++)
		{
			m_suggestedInputs.put(i, new ArrayList<Suggestion>());
		}
		int sug_cnt = 0;
		for (Suggestion sug : m_targetOutput)
		{
			MathList<?> sequence = (MathList<?>) sug.getValue();
			Bounded<?>[] pickers = new Bounded<?>[sequence.size()];
			for (int i = 0; i < pickers.length; i++)
			{
				List<MathList<Object>> values = getInputValuesFor(sequence.get(i));
				AllElements<MathList<Object>> pb = new AllElements<MathList<Object>>(values, true, false);
				pickers[i] = pb;
			}
			AllPickers all = new AllPickers(pickers);
			while (m_coin.pick() && !all.isDone())
			{
				MathList<Object> list = new MathList<Object>();
				Object[] elems = all.pick();
				for (Object e : elems)
				{
				  list.add(e);
				}
				MathList<Object>[] in_sequences = new MathList[getInputArity()]; 
				for (int i = 0; i < in_sequences.length; i++)
				{
					in_sequences[i] = new MathList<Object>();
				}
				for (Object o_tuple : list)
				{
					MathList<Object> tuple = (MathList<Object>) o_tuple;
					for (int i = 0; i < in_sequences.length; i++)
					{
						in_sequences[i].add(tuple.get(i));
					}
				}
				for (int i = 0; i < in_sequences.length; i++)
				{
					Suggestion in_sug = new Suggestion(in_sequences[i]);
					in_sug.addLineage(sug.getLineage());
					in_sug.addLineage(new Association(this, sug_cnt));
					m_suggestedInputs.get(i).add(in_sug);
				}
				sug_cnt++;
			}
		}
		//System.out.println(m_suggestedInputs.get(0));
	}

	protected abstract List<MathList<Object>> getInputValuesFor(Object o);

}
