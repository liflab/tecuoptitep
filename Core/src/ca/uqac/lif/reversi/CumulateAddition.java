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
import ca.uqac.lif.synthia.relative.BoundedPickIf;

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
			int num_wild = 0, cur_total = 0;
			for (Object o : out_list)
			{
				if (o != AlphabetFunction.WILDCARD)
				{
				  int goal = ((Number) o).intValue();
					pickers.add(getSumPicker(num_wild + 1, goal - cur_total));
					cur_total = goal;
					num_wild = 0;
				}
				else
				{
					num_wild++;
				}
			}
			Bounded<?>[] bounded = new Bounded[pickers.size()];
			for (int i = 0; i < bounded.length; i++)
			{
			  bounded[i] = pickers.get(i);
			}
			AllPickers all = new AllPickers(bounded);
			while (m_coin.pick() && !all.isDone())
			{
				MathList<Object> in_stream = new MathList<Object>();
				Object[] elems = all.pick();
				for (Object e : elems)
				{
				  for (Object in_e : (Object[]) e)
				  {
				    in_stream.add(in_e);
				  }
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
	
	/**
	 * Produces a picker enumerating all sequences of <i>k</i> elements of
	 * the function's alphabet whose sum equals a given total.
	 * @param k The length of the sequences
	 * @param total The total to obtain
	 * @return A picker instance that enumerates exactly those sequences
	 * producing the desired sum
	 */
	protected Bounded<?> getSumPicker(int k, int total)
	{
		Bounded<?>[] pickers = new Bounded<?>[k];
		for (int i = 0; i < k; i++)
		{
			pickers[i] = new AllElements<Object>(m_alphabet, false, false);
		}
		return new SumPicker(new AllPickers(pickers), total);
	}
	
	protected class SumPicker extends BoundedPickIf<Object[]>
	{
	  protected final int m_total;
	  
	  public SumPicker(Bounded<Object[]> picker, int sum)
	  {
	    super(picker);
	    m_total = sum;
	  }
	  
	  @Override
    protected boolean select(Object[] o) 
    {
      int sum = 0;
      for (Object x : o)
      {
        sum += ((Number) x).intValue();
      }
      return sum == m_total; 
    }
	}
}
