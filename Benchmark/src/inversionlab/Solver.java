package inversionlab;

import java.util.Set;

import ca.uqac.lif.reversi.AritalSuggestion;

/**
 * An object that attempts to find one or more input suggestions for a
 * precondition, given a specific output suggestion.
 */
public interface Solver
{
	public Set<AritalSuggestion> solve(AritalSuggestion outputs); 
}
