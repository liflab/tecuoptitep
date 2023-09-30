package inversionlab;

import java.util.Iterator;

import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;

public class InversionGenerator implements Iterator<AritalSuggestion>
{
	/**
	 * The name of this generation strategy.
	 */
	public static final String NAME = "Inversion";
	
	protected DistinctStreamSolver m_solver;
	
	public InversionGenerator(DistinctStreamSolver solver)
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
