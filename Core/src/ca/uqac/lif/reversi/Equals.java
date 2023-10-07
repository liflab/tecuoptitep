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
import ca.uqac.lif.synthia.util.Constant;

public class Equals extends ApplyFunction
{
	protected final List<MathList<Object>> m_equalPairs;
	
	protected final List<MathList<Object>> m_unequalPairs;
	
  public Equals(List<Object> alphabet, Picker<Boolean> coin)
  {
    super(2, coin);
    m_equalPairs = new ArrayList<>();
    m_unequalPairs = new ArrayList<>();
    for (int i = 0; i < alphabet.size(); i++)
    {
    	for (int j = 0; j < alphabet.size(); j++)
    	{
    		if (i == j)
    		{
    			m_equalPairs.add(MathList.toList(alphabet.get(i), alphabet.get(j)));
    		}
    		else
    		{
    			m_unequalPairs.add(MathList.toList(alphabet.get(i), alphabet.get(j)));
    		}
    	}
    }
  }
  
  public Equals(List<Object> alphabet)
  {
  	this(alphabet, new Constant<Boolean>(true));
  }
  
	@Override
	protected List<MathList<Object>> getInputValuesFor(Object o)
	{
		if (Boolean.TRUE.equals(o))
		{
			return m_equalPairs;
		}
		return m_unequalPairs;
	}
}
