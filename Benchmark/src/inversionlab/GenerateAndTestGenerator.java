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

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class GenerateAndTestGenerator extends GenerateAndTest implements Generator
{
	protected Set<AritalSuggestion> m_pastSuggestions;

	protected AritalSuggestion m_nextElement;
	
	protected final OutputCondition m_condition;
	
	public GenerateAndTestGenerator(Processor p, int min_length, int max_length,
			Picker<MathList<Object>> list_picker, OutputCondition condition)
	{
		super(p, min_length, max_length, list_picker);
		m_pastSuggestions = new HashSet<AritalSuggestion>();
		m_condition = condition;
	}
	
	@Override
	public boolean hasNext()
	{
		if (m_nextElement == null)
		{
			getNextSuggestion();
		}
		return m_nextElement != null;
	}

	@Override
	public AritalSuggestion next()
	{
		if (m_nextElement == null)
		{
			getNextSuggestion();
		}
		if (m_nextElement == null)
		{
			throw new NoSuchElementException("No more element to enumerate");
		}
		AritalSuggestion a = m_nextElement;
		m_nextElement = null;
		return a;
	}
	
	@Override
	public void reset()
	{
		m_pastSuggestions.clear();
		m_nextElement = null;
	}
	
	@SuppressWarnings("unchecked")
	protected void getNextSuggestion()
	{
		MathList<Object>[] candidate = new MathList[m_processor.getInputArity()];
		AritalSuggestion sugg = null;
		do
		{
			sugg = null;
			for (int i = 0; i < candidate.length; i++)
			{
				candidate[i] = m_listPicker.pick();
				//System.out.println("Candidate: " + candidate[i]);
			}
			List<Object> out_list = isValidSuggestion(candidate);
			if (out_list == null)
			{
				//System.out.println("Invalid");
				continue;
			}
			sugg = new AritalSuggestion(candidate.length + 1);
			for (int i = 0; i < candidate.length; i++)
			{
				sugg.set(i, candidate[i]);
				sugg.set(candidate.length, out_list);
			}
		} while (sugg == null || m_pastSuggestions.contains(sugg));
		m_pastSuggestions.add(sugg);
		m_nextElement = sugg;
	}
	
	public List<Object> isValidSuggestion(MathList<Object>[] inputs)
	{
		List<Object> sol = runThrough(inputs);
		if (sol.size() < m_minLength || sol.size() > m_maxLength)
		{
			return null;
		}
		if (m_condition.isValid(sol))
		{
			return sol;
		}
		return null;
	}

}
