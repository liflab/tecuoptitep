/*
    Inversion of function circuits
    Copyright (C) 2023 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
