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
import ca.uqac.lif.synthia.util.Constant;

public class CountDecimate extends AlphabetFunction
{
  protected final int m_numDecimate;
  
  public CountDecimate(int num_trim, List<Object> alphabet, Picker<Boolean> coin)
  {
  	super(1, alphabet, coin);
    m_numDecimate = num_trim;
  }
  
  public CountDecimate(int num_trim, List<Object> alphabet)
  {
    this(num_trim, alphabet, new Constant<Boolean>(true));
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getSuggestions()
  {
  	m_suggestedInputs.put(0, new ArrayList<Suggestion>());
    int sugg_index = 0;
    for (Suggestion sug : m_targetOutput)
    {
    	MathList<Object> sequence = (MathList<Object>) sug.getValue();
    	for (int trace_len = sequence.size() * m_numDecimate - (m_numDecimate - 1); trace_len <= sequence.size() * m_numDecimate; trace_len++)
    	{
    		Bounded<?>[] pickers = new Bounded<?>[trace_len];
        for (int i = 0; i < pickers.length; i++)
        {
          pickers[i] = i % m_numDecimate == 0 ? new Playback<Object>(sequence.get(i / m_numDecimate)) : new AllElements<Object>(getAlphabet(), true, false);
        }
        for (int i = 0; i < m_targetOutput.size(); i++)
        {
          Suggestion out_sug = m_targetOutput.get(i);
          AllPickers all = new AllPickers(pickers);
          while (m_coin.pick() && !all.isDone())
          {
          	Object[] to_append = all.pick();
            MathList<Object> out_list = new MathList<Object>();
            for (Object o : to_append)
            {
              out_list.add(o);
            }
            out_list.addAll((List<Object>) out_sug.m_value);
            Suggestion sugg = new Suggestion(out_list);
            sugg.addLineage(out_sug.getLineage());
            sugg.addLineage(new Association(this, sugg_index));
            m_suggestedInputs.get(0).add(sugg);
            sugg_index++;
          }
        }
    	}
    }
  }
}
