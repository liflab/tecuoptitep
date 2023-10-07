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

public class Or extends ApplyFunction
{
	protected static final List<MathList<Object>> s_listTrue;
	
	protected static final List<MathList<Object>> s_listFalse;
	
	static
	{
		s_listTrue = new ArrayList<MathList<Object>>(1);
		s_listTrue.add(MathList.toList(true, true));
		s_listTrue.add(MathList.toList(true, false));
		s_listTrue.add(MathList.toList(false, true));
		s_listFalse = new ArrayList<MathList<Object>>(1);
		s_listFalse.add(MathList.toList(false, false));
	}
	
  public Or()
  {
    super(2);
  }

	@Override
	protected List<MathList<Object>> getInputValuesFor(Object o)
	{
		return Boolean.TRUE.equals(o) ? s_listTrue : s_listFalse;
	}
}
