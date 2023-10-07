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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.dag.Node;
import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.enumerative.AllPickers;
import ca.uqac.lif.synthia.sequence.Playback;

public class AritalSuggestion
{
	protected final Object[] m_suggestions;
	
	public AritalSuggestion(int arity)
	{
		super();
		m_suggestions = new Object[arity];
	}
	
	public AritalSuggestion set(int index, Object s)
	{
		m_suggestions[index] = s;
		return this;
	}
	
	public Object get(int index)
	{
		return m_suggestions[index];
	}
	
	@Override
	public int hashCode()
	{
		return 0;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof AritalSuggestion))
		{
			return false;
		}
		AritalSuggestion a = (AritalSuggestion) o;
		if (m_suggestions.length != a.m_suggestions.length)
		{
			return false;
		}
		for (int i = 0; i < m_suggestions.length; i++)
		{
			if (!m_suggestions[i].equals(a.m_suggestions[i]))
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		StringBuilder out = new StringBuilder();
		out.append("{");
		for (int i = 0; i < m_suggestions.length; i++)
		{
			if (i > 0)
			{
				out.append(",");
			}
			out.append(i).append("\u21a6").append(m_suggestions[i]);
		}
		out.append("}");
		return out.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static Set<AritalSuggestion> getSuggestions(Node n)
	{
		if (!(n instanceof Reversible))
		{
			return null;
		}
		List<Suggestion>[] sugs = new List[n.getInputArity()];
		for (int i = 0; i < sugs.length; i++)
		{
			sugs[i] = ((Reversible) n).getSuggestions(i);
		}
		return getSuggestions(sugs);
	}
	
	@SuppressWarnings("unchecked")
	public static Set<AritalSuggestion> getSuggestions(List<Suggestion> ... suggestions)
	{
		Set<AritalSuggestion> grouped_sugs = new HashSet<>();
  	Bounded<?>[] pickers = new Bounded[suggestions.length];
  	for (int i = 0; i < pickers.length; i++)
  	{
  		pickers[i] = new Playback<Suggestion>(suggestions[i]);
  	}
  	AllPickers all = new AllPickers(pickers);
  	while (!all.isDone())
  	{
  		Object[] sol = all.pick();
  		if (isValid(sol))
  		{
  			AritalSuggestion candidate = new AritalSuggestion(suggestions.length);
  			for (int i = 0; i < sol.length; i++)
  			{
  				candidate.set(i, ((Suggestion) sol[i]).getValue());
  			}
  			grouped_sugs.add(candidate);
  		}
  	}
  	return grouped_sugs;
	}
	
	@SuppressWarnings("unchecked")
	protected static boolean isValid(Object[] solution)
  {
  	Set<Association>[] lin = new Set[solution.length];
  	for (int i = 0; i < solution.length; i++)
  	{
  		lin[i] = ((Suggestion) solution[i]).getLineage();
  	}
  	for (int i = 0; i < lin.length - 1; i++)
  	{
  		for (int j = i + 1; j < lin.length; j++)
  		{
  			if (!isCompatible(lin[i], lin[j]))
  			{
  				return false;
  			}
  		}
  	}
  	return true;
  }
  
  protected static boolean isCompatible(Set<Association> lin1, Set<Association> lin2)
  {
  	for (Association a1 : lin1)
  	{
  		for (Association a2 : lin2)
  		{
  			if (!a1.isCompatible(a2))
  			{
  				return false;
  			}
  		}
  	}
  	return true;
  }
	
}
