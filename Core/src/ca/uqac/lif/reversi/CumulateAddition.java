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
import java.util.Arrays;
import java.util.List;

import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.enumerative.AllPickers;
import ca.uqac.lif.synthia.sequence.Playback;

public class CumulateAddition extends AlphabetFunction
{
	public CumulateAddition(List<Object> alphabet, Picker<Boolean> coin, boolean wildcards)
	{
		super(1, alphabet, coin, wildcards);
	}
	
	public CumulateAddition(List<Object> alphabet, boolean wildcards)
	{
		super(1, alphabet, wildcards);
	}

	@Override
	protected void getSuggestions()
	{
		List<Suggestion> in_sugs = new ArrayList<Suggestion>();
		int sug_cnt = 0;
		for (Suggestion out_sug : m_targetOutput)
		{
			List<?> out_list = (List<?>) out_sug.getValue();
			List<Bounded<?>> pickers = new ArrayList<Bounded<?>>();
			// TODO: partition input
			int num_wild = 0;
			for (Object o : out_list)
			{
				if (o != AlphabetFunction.WILDCARD)
				{
					pickers.add(getSumPicker(num_wild, ((Number) o).intValue()));
					num_wild = 0;
				}
				else
				{
					num_wild++;
				}
			}
			AllPickers all = new AllPickers((Bounded<?>[]) pickers.toArray());
			while (m_coin.pick() && !all.isDone())
			{
				MathList<Object> in_stream = new MathList<Object>();
				Object[] elems = all.pick();
				for (Object e : elems)
				{
					in_stream.addAll((List<?>) e);
				}
				Suggestion in_sug = new Suggestion(in_stream);
				in_sug.addLineage(out_sug.getLineage());
				in_sug.addLineage(new Association(this, sug_cnt));
				sug_cnt++;
				in_sugs.add(in_sug);
			}
		}
		m_suggestedInputs.put(0, in_sugs);
	}
	
	protected Bounded<?> getSumPicker(int num_wild, int total)
	{
		Bounded<?>[] pickers = new Bounded<?>[num_wild + 1];
		for (int i = 0; i < num_wild; i++)
		{
			pickers[i] = getAnyPicker();
		}
		pickers[num_wild] = new Playback<Object>(0, Arrays.asList(total)).setLoop(false);
		AllPickers all = new AllPickers(pickers);
	}
}
