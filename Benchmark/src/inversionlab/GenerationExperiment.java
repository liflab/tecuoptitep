package inversionlab;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.reversi.AritalSuggestion;

public abstract class GenerationExperiment extends Experiment
{
	/**
	 * The name of parameter "problem".
	 */
	public static final String PROBLEM = "Problem";
	
	/**
	 * The name of parameter "method".
	 */
	public static final String METHOD = "Method";
	
	/**
	 * The name of parameter "time".
	 */
	public static final String TIME = "Time";
	
	/**
	 * An iterator over arrays of streams.
	 */
	protected Iterator<AritalSuggestion> m_generator = null;
	
	protected Set<AritalSuggestion> m_suggestions;
	
	public GenerationExperiment()
	{
		super();
		m_suggestions = new HashSet<AritalSuggestion>();
		describe(PROBLEM, "The input generation problem this experiment considers");
		describe(METHOD, "The method or tool used to generate input sequences");
	}
	
	/**
	 * Sets the stream generator.
	 * @param g An iterator over suggestions. Each new value produced by
	 * the iterator must be distinct from all the others produced so far. The
	 * experiment assumes this without checking it.  
	 */
	public void setGenerator(Iterator<AritalSuggestion> g)
	{
		m_generator = g;
	}
}
