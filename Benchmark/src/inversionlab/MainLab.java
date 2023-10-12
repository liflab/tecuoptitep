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

import static ca.uqac.lif.labpal.region.ExtensionDomain.extension;
import static ca.uqac.lif.labpal.region.ProductRegion.product;
import static ca.uqac.lif.labpal.table.ExperimentTable.table;
import static ca.uqac.lif.labpal.table.TransformedTable.transform;
import static inversionlab.InputOutputFactory.PROBLEM;
import static inversionlab.ReversibleFactory.ALPHA;
import static inversionlab.ReversibleFactory.WILDCARDS;
import static inversionlab.ReversibleGeneratorFactory.NUM_OUTPUTS;
import static inversionlab.PreconditionFactory.ALPHABET_SIZE;
import static inversionlab.PreconditionFactory.CONDITION;
import static inversionlab.GeneratorExperiment.ELEMENTS;
import static inversionlab.GeneratorExperiment.METHOD;
import static inversionlab.GeneratorExperiment.TIME;
import static inversionlab.SolverExperiment.RATIO;
import static inversionlab.SolverExperiment.SUCCESSES;
import static inversionlab.StreamExperiment.DURATION;
import static inversionlab.StreamExperiment.HIT_RATE;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentGroup;
import ca.uqac.lif.labpal.plot.Plot;
import ca.uqac.lif.labpal.region.Region;
import ca.uqac.lif.labpal.table.ExperimentTable;
import ca.uqac.lif.labpal.table.TransformedTable;
import ca.uqac.lif.labpal.util.CliParser;
import ca.uqac.lif.labpal.util.CliParser.Argument;
import ca.uqac.lif.labpal.util.CliParser.ArgumentMap;
import ca.uqac.lif.spreadsheet.chart.Chart.Axis;
import ca.uqac.lif.spreadsheet.chart.gnuplot.GnuplotHistogram;
import ca.uqac.lif.spreadsheet.chart.gnuplot.GnuplotScatterplot;
import ca.uqac.lif.spreadsheet.functions.ExpandAsColumns;
import ca.uqac.lif.spreadsheet.functions.Sort;

@SuppressWarnings("unused")
public class MainLab extends Laboratory
{
  /**
   * The name of the constant "yes".
   */
  public static final String YES = "Yes";
  
  /**
   * The name of the constant "no".
   */
  public static final String NO = "No";
  
	@Override
	public void setup()
	{
		/* Get command line arguments if any */
		ArgumentMap args = this.getCliArguments();

		/* The range of trace lengths to generate */
		int min_len = intArgOrDefault(args, "minlen", 4);
		int max_len = intArgOrDefault(args, "maxlen", 20);
		System.out.println("Trace length:\t[" + min_len + "," + max_len + "]");

		/* The size of the alphabet */
		int alphabet_size = intArgOrDefault(args, "alphabet", 4);
		System.out.println("Alphabet size:\t" + alphabet_size);

		/* The maximum number of traces to generate */
		int trace_limit = intArgOrDefault(args, "traces", 20);
		System.out.println("Trace limit:\t" + trace_limit);
		
		/* The maximum number of tries for the reversible generator on each output */
		int max_tries_generate = 10000;

		/* The maximum number of tries for the reversible solver on each output */
		int max_tries_solve_inv = 10;
		
		/* The maximum number of tries for the generate-and-test solver on each output */
		int max_tries_solve_gnt = max_tries_solve_inv * 1000;

		/* The number of outputs given to the inversion method in the generator
		 * experiments. */
		int num_outputs = 1;

		Region big_r = product(
				extension(METHOD, ReversibleGenerator.NAME, GenerateAndTestGenerator.NAME),
				extension(PROBLEM, GeneratorExperiment.NAME, SolverExperiment.NAME),
				//extension(WILDCARDS, YES, NO),
				extension(CONDITION, 
						PreconditionFactory.TWO_EQUAL_TRIM_F, PreconditionFactory.TWO_EQUAL_DECIMATE_F,
						PreconditionFactory.TWO_EQUAL_TRIM_G, PreconditionFactory.TWO_EQUAL_DECIMATE_G,
						PreconditionFactory.AT_LEAST_N_IN_WINDOW),
				extension(ALPHABET_SIZE, alphabet_size),
				extension(ALPHA, 0.05f, 0.1f, 0.25f, 0.5f, 0.75f, 1f),
				extension(NUM_OUTPUTS, num_outputs));

		// Generator experiments
		{
			ExperimentGroup g = new ExperimentGroup("Generator experiments", "Experiments where each input generation method is asked to produce a fixed number of inputs satisfying a given precondition.");
			add(g);
			GeneratorExperimentFactory factory = new GeneratorExperimentFactory(this).setSizeLimit(trace_limit);
			factory.add(ReversibleGenerator.NAME, new ReversibleGeneratorFactory(new ReversibleFactory().setLengthBounds(min_len, max_len), min_len, max_len, max_tries_generate).setSeed(getSeed()));
			factory.add(GenerateAndTest.NAME, new GenerateAndTestGeneratorFactory(new PipelineFactory().setLengthBounds(min_len, max_len), min_len, max_len).setSeed(getSeed()));

			for (Region r : big_r.set(PROBLEM, GeneratorExperiment.NAME)/*.set(WILDCARDS, NO)*/.set(ALPHA, 0.1f).all(CONDITION, ALPHA, ALPHABET_SIZE))
			{
				{
					// Comparison of running time and number of generated streams
					ExperimentTable et = table(METHOD, ELEMENTS, TIME);
					et.setTitle("Time to find " + trace_limit + " inputs, " + r.asPoint().getString(CONDITION) + ", |\u03a3|=" + getInt(r, ALPHABET_SIZE) + ", \u03b1=" + getFloat(r, ALPHA));
					et.add(factory.get(r));
					TransformedTable tt = transform(new ExpandAsColumns(METHOD, TIME), et);
					add(tt);
					g.add(factory.get(r));
					add(new Plot(tt, new GnuplotScatterplot().setCustomHeader("set datafile missing \"\"")));
				}

				{
					// Length distribution of found solutions
					LengthHistogram lh = new LengthHistogram().add(factory.get(r));
					lh.setTitle("Distribution of solutions per stream length, " + r.asPoint().getString(CONDITION) + ", |\u03a3|=" + getInt(r, ALPHABET_SIZE) + ", \u03b1=" + getFloat(r, ALPHA));
					add(lh);
					add(new Plot(lh, new GnuplotHistogram()));
				}
			}

			for (Region r : big_r.set(PROBLEM, GeneratorExperiment.NAME)/*.set(WILDCARDS, NO)*/.set(METHOD, ReversibleGenerator.NAME).set(NUM_OUTPUTS, num_outputs).all(ALPHABET_SIZE))
			{
				{
					// Impact of alpha on found solutions
					ExperimentTable et = table(CONDITION, ALPHA, DURATION);
					et.setTitle("Impact of \u03b1 on generation time");
					et.add(factory.get(r));
					TransformedTable tt = transform(transform(et, new ExpandAsColumns(CONDITION, DURATION)), new Sort().by(0));
					add(tt);
					g.add(factory.get(r));
					add(new Plot(tt, new GnuplotScatterplot().withLines(false).setCustomHeader("set datafile missing \"\"")));
				}
			}
		}

		// Solver experiments
		{
			ExperimentGroup g = new ExperimentGroup("Solver experiments", "Experiments where each input generation method is asked to produce an input producing a precise output.");
			add(g);
			SolverExperimentFactory factory = new SolverExperimentFactory(this).setTraceLimit(trace_limit);
			factory.add(ReversibleSolver.NAME, new ReversibleSolverFactory(new ReversibleFactory().setLengthBounds(min_len, max_len), min_len, max_len, max_tries_solve_inv).setSeed(getSeed()));
			factory.add(GenerateAndTestSolver.NAME, new GenerateAndTestSolverFactory(new PipelineFactory().setLengthBounds(min_len, max_len), min_len, max_len, max_tries_solve_gnt).setSeed(getSeed()));

			{
				Region in_r = big_r.set(PROBLEM, SolverExperiment.NAME)/*.set(WILDCARDS, NO)*/.set(ALPHA, 0.5f);
				for (Region r : in_r.all(ALPHA, ALPHABET_SIZE))
				{
					ExperimentTable et = table(METHOD, CONDITION, RATIO, HIT_RATE, DURATION);
					et.setTitle("Success ratio for the solving problem, |\u03a3|=" + getInt(r, ALPHABET_SIZE) + ", \u03b1=" + getFloat(r, ALPHA));
					et.add(factory.get(r));
					g.add(factory.get(r));
					add(et);
				}
			}

			// Impact of alpha on hit rate
			{
				for (Region r : big_r.set(PROBLEM, SolverExperiment.NAME)/*.set(WILDCARDS, NO)*/.set(METHOD, ReversibleSolver.NAME).all(ALPHABET_SIZE, METHOD))
				{
					ExperimentTable et = table(CONDITION, ALPHA, HIT_RATE);
					et.setTitle("Impact of \u03b1 on hit rate, |\u03a3|=" + getInt(r, ALPHABET_SIZE));
					et.add(factory.get(r));
					g.add(factory.get(r));
					TransformedTable tt = transform(et, new ExpandAsColumns(CONDITION, HIT_RATE));
					add(tt);
					add(new Plot(tt, new GnuplotScatterplot().setCustomHeader("set datafile missing \"\"").setCaption(Axis.X, ALPHA).setCaption(Axis.Y, HIT_RATE)));
				}
			}

		}

	}

	public static void main(String[] args)
	{
		initialize(args, MainLab.class);
	}

	@Override
	public void setupCli(CliParser parser)
	{
		parser.addArgument(new Argument().withLongName("minlen").withArgument("x").withDescription("Set minimum trace length to x"));
		parser.addArgument(new Argument().withLongName("maxlen").withArgument("x").withDescription("Set maximum trace length to x"));
		parser.addArgument(new Argument().withLongName("traces").withArgument("x").withDescription("Stop after generating x traces"));
		parser.addArgument(new Argument().withLongName("alphabet").withArgument("x").withDescription("Set alphabet size to x"));
	}

	protected static float getFloat(Region r, String name)
	{
		return ((Number) r.asPoint().get(name)).floatValue();
	}

	protected static int getInt(Region r, String name)
	{
		return ((Number) r.asPoint().get(name)).intValue();
	}

	protected static int intArgOrDefault(ArgumentMap args, String name, int int_default)
	{
		if (args.hasOption(name))
		{
			return Integer.parseInt(args.getOptionValue(name).trim());
		}
		return int_default;
	}

}
