package inversionlab;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.reversi.AritalSuggestion;

/**
 * Base class for experiments that attempt to generate inputs streams based on
 * a precondition.
 */
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
	 * The name of parameter "size limit".
	 */
	public static final String SIZE_LIMIT = "Size limit";
	
	/**
	 * Creates a new experiment instance and populates the description of some of
	 * its parameters.
	 */
	public GenerationExperiment()
	{
		super();
		describe(PROBLEM, "The input generation problem this experiment considers");
		describe(METHOD, "The method or tool used to generate input sequences");
	}
}
