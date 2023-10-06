package inversionlab;

import java.util.Arrays;
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
	
	public ReversibleSolver(Reversible condition)
	{
		super();
		m_condition = condition;
	}
	
	@Override
	public Set<AritalSuggestion> solve(AritalSuggestion outputs)
	{
		m_condition.reset();
		m_condition.setTargetOutputs(0, Arrays.asList(new Suggestion(outputs.get(0))));
		return AritalSuggestion.getSuggestions((Node) m_condition);
	}
}
