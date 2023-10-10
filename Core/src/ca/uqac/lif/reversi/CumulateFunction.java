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
import ca.uqac.lif.synthia.Picker;

public abstract class CumulateFunction<T> extends AlphabetFunction
{
	public CumulateFunction(List<Object> alphabet, Picker<Boolean> coin)
	{
		super(1, alphabet, coin);
	}
	
	public CumulateFunction(List<Object> alphabet)
	{
		super(1, alphabet);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void getSuggestions()
	{
		int sug_cnt = 0;
		List<Suggestion> inputs = new ArrayList<Suggestion>(m_targetOutput.size());
		for (Suggestion out_sugg : m_targetOutput)
		{
			List<?> out_stream = (List<?>) out_sugg.getValue();
			List<Object> in_stream = new MathList<Object>();
			T o = getStartValue();
			for (Object x : out_stream)
			{
				T next = getNextOutputValue(o, (T) x);
				if (next == null) // No solution
				{
					//System.out.println("Impossible for " + out_stream);
					in_stream = null;
					break;
				}
				in_stream.add(next);
				o = getNextStoredValue(o, next);
			}
			if (in_stream != null)
			{
				//System.out.println("Possible for " + out_stream);
				Suggestion s = new Suggestion(in_stream);
				s.addLineage(out_sugg.getLineage());
				s.addLineage(new Association(this, sug_cnt));
				inputs.add(s);
				sug_cnt++;
			}
		}
		m_suggestedInputs.put(0, inputs);
		
	}
	
	protected abstract T getStartValue();
					
	protected abstract T getNextOutputValue(T previous, T current);
	
	protected abstract T getNextStoredValue(T previous, T next);

}
