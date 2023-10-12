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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqac.lif.dag.Node;
import ca.uqac.lif.reversi.util.MathList;

public class Fork extends Node implements Reversible
{
	protected List<Suggestion> m_targetInputs;

	protected final Map<Integer,List<Suggestion>> m_targetOutputs;

	public Fork()
	{
		super(1, 2);
		m_targetInputs = null;
		m_targetOutputs = new HashMap<Integer,List<Suggestion>>(2);
	}
	
	@Override
  public void reset()
  {
	  m_targetInputs = null;
	  m_targetOutputs.clear();
  }

	@Override
	public void setTargetOutputs(int out_index, List<Suggestion> suggestions)
	{
		m_targetOutputs.put(out_index, suggestions);
	}

	//@Override
	public List<Suggestion> getTargetOutputs(int out_index)
	{
		return m_targetOutputs.get(out_index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Suggestion> getSuggestions(int in_arity)
	{
		if (m_targetInputs != null)
		{
			return m_targetInputs;
		}
		m_targetInputs = new ArrayList<Suggestion>();
		int sug_index = 0;
		for (Suggestion s0 : m_targetOutputs.get(0))
		{
			for (Suggestion s1 : m_targetOutputs.get(1))
			{
				if (!AritalSuggestion.isCompatible(s0.getLineage(), s1.getLineage()))
				{
					continue;
				}
				MathList<Object> seq0 = (MathList<Object>) s0.getValue();
				MathList<Object> seq1 = (MathList<Object>) s1.getValue();
				MathList<Object> suffix = common(seq0, seq1);
				if (suffix == null)
				{
					continue;
				}
				Suggestion new_s = new Suggestion(suffix);
				new_s.addLineage(s0.getLineage());
				new_s.addLineage(s1.getLineage());
				new_s.addLineage(new Association(this, sug_index));
				if (!m_targetInputs.contains(new_s))
				{
					m_targetInputs.add(new_s);
					sug_index++;
				}
			}
		}
		return m_targetInputs;
	}
	
	protected static MathList<Object> common(MathList<Object> l1, MathList<Object> l2)
	{
	  MathList<Object> out_list = new MathList<Object>();
		if (l1.size() < l2.size())
		{
			for (int i = 0; i < l1.size(); i++)
			{
			  Object o = compatibleEvents(l1.get(i), l2.get(i));
				if (o == null)
				{
					return null;
				}
				out_list.add(o);
			}
			for (int i = l1.size(); i < l2.size(); i++)
			{
			  out_list.add(l2.get(i));
			}
			return out_list;
		}
		for (int i = 0; i < l2.size(); i++)
		{
		  Object o = compatibleEvents(l1.get(i), l2.get(i));
		  if (o == null)
      {
        return null;
      }
      out_list.add(o);
		}
		for (int i = l2.size(); i < l1.size(); i++)
    {
      out_list.add(l1.get(i));
    }
		return out_list;
	}
	
	protected static Object compatibleEvents(Object o1, Object o2)
	{
	  if (o1 == AlphabetFunction.WILDCARD)
	  {
	    return o2;
	  }
	  if (o2 == AlphabetFunction.WILDCARD)
	  {
	    return o1;
	  }
	  if (o1.equals(o2))
	  {
	    return o1;
	  }
	  return null;
	}
}
