package inversionlab;

import ca.uqac.lif.labpal.experiment.ExperimentException;

/**
 * An experiment whose task is to try to find an input producing a specific
 * output. The experiment is given a set of <i>k</i> such outputs, and counts
 * for how many of these a suitable input has been successfully found.
 * <p>
 * A solver may find more than one solution for a given output in a single
 * calculation (this is the case for example of the inversion method). In such
 * a case the experiment also keeps track the total number of solutions found.
 * In some cases, this number may exceed the number of outputs.
 */
public class SolutionGenerationExperiment extends GenerationExperiment
{
	/**
	 * The name of parameter "found".
	 */
	public static final String FOUND = "Found";
	
	/**
	 * The name of parameter "successes".
	 */
	public static final String SUCCESSES = "Successes";
	
	/**
	 * The name of parameter "found".
	 */
	public static final String RATIO = "Success ratio";
	
	/**
	 * Creates a new experiment instance and populates the description of some of
	 * its parameters.
	 */
	public SolutionGenerationExperiment()
	{
		super();
		describe(SIZE_LIMIT, "The number of outputs for which the experiment is asked to find a solution");
		describe(SUCCESSES, "The number of outputs for which at least one correct solution has been found");
		describe(FOUND, "The total number of solutions found");
		describe(RATIO, "The ratio of the number of solutions found to the total number of attempts");
	}
	
	@Override
	public void execute() throws ExperimentException
	{
		
	}
	
	
}
