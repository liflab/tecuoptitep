package inversionlab;

import java.util.Arrays;
import java.util.HashSet;
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
		  m_condition.setTargetOutputs(0, Arrays.asList(new Suggestion(outputs.get(0))));
		  Set<AritalSuggestion> sols = AritalSuggestion.getSuggestions((Node) m_condition);
		  solutions.addAll(sols);
		  if (!sols.isEmpty())
		  {
		    m_successes++;
		  }
		}
		m_totalTries += m_maxTries;
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
