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
import ca.uqac.lif.synthia.util.Constant;

public class Trim extends AlphabetFunction
{
  protected final int m_numTrim;
  
  public Trim(int num_trim, List<Object> alphabet, Picker<Boolean> coin)
  {
    super(1, alphabet, coin);
    m_numTrim = num_trim;
  }
  
  public Trim(int num_trim, List<Object> alphabet)
  {
  	this(num_trim, alphabet, new Constant<Boolean>(true));
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getSuggestions()
  {
    m_suggestedInputs.put(0, new ArrayList<Suggestion>());
    int sugg_index = 0;
    Bounded<?>[] pickers = new Bounded<?>[m_numTrim];
    for (int i = 0; i < pickers.length; i++)
    {
      pickers[i] = new AllElements<Object>(getAlphabet(), true, false);
    }
    for (int i = 0; i < m_targetOutput.size(); i++)
    {
      Suggestion out_sug = m_targetOutput.get(i);
      AllPickers all = new AllPickers(pickers);
      all.reset();
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
