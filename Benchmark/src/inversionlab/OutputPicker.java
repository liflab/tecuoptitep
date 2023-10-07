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
package inversionlab;

import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class OutputPicker implements Picker<AritalSuggestion>
{
  protected final int m_arity;
  
  protected final Picker<MathList<Object>> m_picker;
  
  public OutputPicker(int arity, Picker<MathList<Object>> picker)
  {
    super();
    m_picker = picker;
    m_arity = arity;
  }
  
  @Override
  public void reset()
  {
    m_picker.reset();
  }

  @Override
  public AritalSuggestion pick()
  {
    AritalSuggestion s = new AritalSuggestion(m_arity);
    for (int i = 0; i < m_arity; i++)
    {
      s.set(i, m_picker.pick());
    }
    return s;
  }

  @Override
  public Picker<AritalSuggestion> duplicate(boolean with_state)
  {
    throw new UnsupportedOperationException();
  }
}
