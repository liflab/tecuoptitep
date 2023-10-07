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
package ca.uqac.lif.reversi.util;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

public class AllTruePicker implements Picker<MathList<Object>>
{
  protected final Picker<Integer> m_length;
  
  public AllTruePicker(Picker<Integer> len)
  {
    super();
    m_length = len;
  }
  
  public AllTruePicker(int len)
  {
    this(new Constant<Integer>(len));
  }

  @Override
  public void reset()
  {
    m_length.reset();
  }

  @Override
  public MathList<Object> pick()
  {
    MathList<Object> list = new MathList<Object>();
    int len = m_length.pick();
    for (int i = 0; i < len; i++)
    {
      list.add(true);
    }
    return list;
  }

  @Override
  public AllTruePicker duplicate(boolean with_state)
  {
    return new AllTruePicker(m_length.duplicate(with_state));
  }
}
