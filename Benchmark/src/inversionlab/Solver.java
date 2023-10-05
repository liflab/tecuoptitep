package inversionlab;

import java.util.Set;

import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;

/**
 * An object that attempts to find one ore more input suggestions for a
 * precondition, given a specific output suggestion.
 */
public interface Solver
{
	public Set<AritalSuggestion> solve(MathList<Object>[] outputs); 
}
