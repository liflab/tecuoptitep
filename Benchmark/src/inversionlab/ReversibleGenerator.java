package inversionlab;

import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;

public class ReversibleGenerator implements Generator
{
	/**
	 * The name of this generation strategy.
	 */
	public static final String NAME = "Inversion";
	
	protected DistinctStreamSolver m_solver;
	
	public ReversibleGenerator(DistinctStreamSolver solver)
	{
		super();
		m_solver = solver;
	}

	@Override
	public boolean hasNext()
	{
		return !m_solver.isDone();
	}

	@Override
	public AritalSuggestion next()
	{
		return m_solver.pick();
	}
}
