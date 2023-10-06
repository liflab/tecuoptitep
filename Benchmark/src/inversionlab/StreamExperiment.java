package inversionlab;

import ca.uqac.lif.labpal.experiment.Experiment;

/**
 * Base class for experiments that attempt to generate inputs streams based on
 * a precondition.
 */
public abstract class StreamExperiment extends Experiment
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
   * The name of parameter "hit rate".
   */
  public static final String HIT_RATE = "Hit rate";
	
	/**
	 * Creates a new experiment instance and populates the description of some of
	 * its parameters.
	 */
	public StreamExperiment()
	{
		super();
		describe(PROBLEM, "The input generation problem this experiment considers");
		describe(METHOD, "The method or tool used to generate input sequences");
		describe(HIT_RATE, "The fraction of times where calling the method on an output results in a suitable input");
	}
}
