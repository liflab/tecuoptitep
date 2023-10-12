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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.dag.Node;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.Reversible;
import ca.uqac.lif.reversi.Suggestion;

/**
 * A solver that finds solutions by inverting a {@link Reversible} function
 * circuit.
 */
public class ReversibleSolver implements Solver
{
	public static final String NAME = "Inversion";
	
	protected final Reversible m_condition;
	
	protected final int m_maxTries;
	
	protected long m_totalTries;
	
	protected long m_successes;
	
	public ReversibleSolver(Reversible condition, int max_tries)
	{
		super();
		m_condition = condition;
		m_maxTries = max_tries;
		m_totalTries = 0;
		m_successes = 0;
	}
	
	@Override
	public Set<AritalSuggestion> solve(AritalSuggestion outputs)
	{
		Set<AritalSuggestion> solutions = new HashSet<AritalSuggestion>();
		for (int i = 0; i < m_maxTries; i++)
		{
		  m_condition.reset();
		  List<Suggestion> sugs = new ArrayList<Suggestion>(1);
		  sugs.add(new Suggestion(outputs.get(0)));
		  m_condition.setTargetOutputs(0, sugs);
		  Set<AritalSuggestion> sols = AritalSuggestion.getSuggestions((Node) m_condition);
		  solutions.addAll(sols);
		  if (!sols.isEmpty())
		  {
		    m_successes++;
		  }
		}
		m_totalTries += m_maxTries;
		m_condition.reset();
		return solutions;
	}

  @Override
  public double getHitRate()
  {
    if (m_totalTries == 0)
    {
      return 0f;
    }
    return (double) m_successes / (double) m_totalTries;
  }
}
