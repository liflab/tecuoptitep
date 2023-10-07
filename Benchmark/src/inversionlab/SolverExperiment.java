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

import java.util.List;
import java.util.Set;

import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.json.JsonNumber;
import ca.uqac.lif.labpal.experiment.ExperimentException;
import ca.uqac.lif.labpal.util.Stopwatch;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.synthia.Picker;

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
public class SolverExperiment extends StreamExperiment
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
	 * The name of parameter "ratio".
	 */
	public static final String RATIO = "Success ratio";
	
	/**
   * The name of parameter "success distribution".
   */
  public static final String LENGTH_DISTRIBUTION = "Length distribution";
	
	/**
	 * The name of parameter "success distribution".
	 */
	public static final String SUCCESS_DISTRIBUTION = "Success distribution";
	
	/**
	 * The picker object that generates the output suggestions to give to the
	 * solver.
	 */
	protected Picker<AritalSuggestion> m_outputPicker;
	
	/**
	 * The solver that finds solutions for a given output.
	 */
	protected Solver m_solver;
	
	/**
	 * Creates a new experiment instance and populates the description of some of
	 * its parameters.
	 */
	public SolverExperiment()
	{
		super();
		describe(SIZE_LIMIT, "The number of outputs for which the experiment is asked to find a solution");
		describe(SUCCESSES, "The number of outputs for which at least one correct solution has been found");
		describe(FOUND, "The total number of solutions found");
		describe(RATIO, "The ratio of the number of solutions found to the total number of attempts");
		describe(LENGTH_DISTRIBUTION, "The number of outputs of a given length submitted to the solver");
		describe(SUCCESS_DISTRIBUTION, "The number of outputs of a given length for which a solution has been cound");
	}
	
	/**
	 * Sets the picker object that generates the output suggestions to give to
	 * the solver.
	 * @param picker The picker
	 */
	public void setOutputPicker(Picker<AritalSuggestion> picker)
	{
		m_outputPicker = picker;
	}
	
	/**
	 * Sets the solver that finds solutions for a given output. 
	 * @param s The solver
	 */
	public void setSolver(Solver s)
	{
		m_solver = s;
	}
	
	@Override
	public void execute() throws ExperimentException
	{
		JsonMap len_dist = new JsonMap();
		writeOutput(LENGTH_DISTRIBUTION, len_dist);
		JsonMap suc_dist = new JsonMap();
		writeOutput(SUCCESS_DISTRIBUTION, suc_dist);
		int total_tries = readInt(SIZE_LIMIT);
		Stopwatch.start(this);
		for (int i = 0; i < total_tries; i++)
		{
		  setProgression((float) i / (float) total_tries);
			AritalSuggestion out = m_outputPicker.pick();
			String s_out_len = Integer.toString(((List<?>) out.get(0)).size());
			Set<AritalSuggestion> ins = m_solver.solve(out);
			int len_entry = 0, suc_entry = 0, num_sols = ins.size();
			if (len_dist.containsKey(s_out_len))
			{
				len_entry = ((JsonNumber) len_dist.get(s_out_len)).numberValue().intValue();
			}
			if (suc_dist.containsKey(s_out_len))
			{
				suc_entry = ((JsonNumber) suc_dist.get(s_out_len)).numberValue().intValue();
			}
			len_dist.put(s_out_len, len_entry + 1);
			suc_dist.put(s_out_len, suc_entry + num_sols);
			if (num_sols > 0)
			{
				writeOutput(SUCCESSES, readInt(SUCCESSES) + 1);
			}
			writeOutput(FOUND, readInt(FOUND) + num_sols);
			writeOutput(RATIO, (float) readInt(SUCCESSES) / (float) (i + 1));
		}
		writeOutput(HIT_RATE, m_solver.getHitRate());
		writeOutput(DURATION, Stopwatch.stop(this));
	}
}
