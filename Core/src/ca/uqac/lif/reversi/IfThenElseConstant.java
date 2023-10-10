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

public class IfThenElseConstant extends ReversibleFunction
{
	protected final Object m_oIf;
	
	protected final Object m_oElse;
	
	public IfThenElseConstant(Object o_if, Object o_else, Picker<Boolean> coin)
	{
		super(1, coin);
		m_oIf = o_if;
		m_oElse = o_else;
	}
	
	public IfThenElseConstant(Object o_if, Object o_else)
	{
		super(1);
		m_oIf = o_if;
		m_oElse = o_else;
	}

	@Override
	protected void getSuggestions()
	{
		int sug_cnt = 0;
		List<Suggestion> in_sugs = new ArrayList<Suggestion>(m_targetOutput.size());
		for (Suggestion out_sug : m_targetOutput)
		{
			List<?> out_stream = (List<?>) out_sug.getValue();
			MathList<Object> in_stream = new MathList<Object>();
			for (Object e : out_stream)
			{
				if (m_oIf.equals(e))
				{
					in_stream.add(true);
				}
				else if (m_oElse.equals(e))
				{
					in_stream.add(false);
				}
				else
				{
					in_stream = null;
					break;
				}
			}
			if (in_stream != null)
			{
				Suggestion in_sug = new Suggestion(in_stream);
				in_sug.addLineage(out_sug.getLineage());
				in_sug.addLineage(new Association(this, sug_cnt));
				in_sugs.add(in_sug);
				sug_cnt++;
			}
		}
		m_suggestedInputs.put(0, in_sugs);
	}
}
