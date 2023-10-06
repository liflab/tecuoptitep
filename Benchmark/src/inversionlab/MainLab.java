package inversionlab;

import static ca.uqac.lif.labpal.region.ExtensionDomain.extension;
import static ca.uqac.lif.labpal.region.ProductRegion.product;
import static ca.uqac.lif.labpal.table.ExperimentTable.table;
import static ca.uqac.lif.labpal.table.TransformedTable.transform;
import static inversionlab.CircuitFactory.ALPHA;
import static inversionlab.ReversibleGeneratorFactory.NUM_OUTPUTS;
import static inversionlab.PreconditionFactory.ALPHABET_SIZE;
import static inversionlab.PreconditionFactory.CONDITION;
import static inversionlab.GeneratorExperiment.ELEMENTS;
import static inversionlab.GeneratorExperiment.METHOD;
import static inversionlab.SolverExperiment.RATIO;
import static inversionlab.SolverExperiment.SUCCESSES;
import static inversionlab.StreamExperiment.HIT_RATE;
import static inversionlab.StreamExperiment.TIME;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentGroup;
import ca.uqac.lif.labpal.plot.Plot;
import ca.uqac.lif.labpal.region.Region;
import ca.uqac.lif.labpal.table.ExperimentTable;
import ca.uqac.lif.labpal.table.TransformedTable;
import ca.uqac.lif.labpal.util.CliParser;
import ca.uqac.lif.labpal.util.CliParser.Argument;
import ca.uqac.lif.labpal.util.CliParser.ArgumentMap;
import ca.uqac.lif.spreadsheet.chart.gnuplot.GnuplotScatterplot;
import ca.uqac.lif.spreadsheet.functions.ExpandAsColumns;

@SuppressWarnings("unused")
public class MainLab extends Laboratory
{
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
		
		/* The maximum number of tries for the reversible solver on each output */
		int max_tries = 10000;

		// Generator experiments
		{
			ExperimentGroup g = new ExperimentGroup("Generator experiments", "Experiments where each input generation method is asked to produce a fixed number of inputs satisfying a given precondition.");
			add(g);
			GeneratorExperimentFactory factory = new GeneratorExperimentFactory(this).setSizeLimit(trace_limit);
			factory.add(ReversibleGenerator.NAME, new ReversibleGeneratorFactory(new CircuitFactory(), min_len, max_len).setSeed(getSeed()));
			factory.add(GenerateAndTest.NAME, new GenerateAndTestGeneratorFactory(new PipelineFactory(), min_len, max_len).setSeed(getSeed()));

			Region big_r = product(
					extension(METHOD, ReversibleGenerator.NAME, GenerateAndTestGenerator.NAME),
					extension(CONDITION, PreconditionFactory.TWO_EQUAL_TRIM, PreconditionFactory.TWO_EQUAL_DECIMATE),
					extension(ALPHABET_SIZE, alphabet_size),
					extension(ALPHA, 0.1f),
					extension(NUM_OUTPUTS, 20));
			for (Region r : big_r.all(CONDITION, ALPHA, ALPHABET_SIZE))
			{
				ExperimentTable et = table(METHOD, ELEMENTS, TIME);
				et.add(factory.get(r));
				TransformedTable tt = transform(new ExpandAsColumns(METHOD, TIME), et);
				add(tt);
				g.add(factory.get(r));
				add(new Plot(tt, new GnuplotScatterplot().setCustomHeader("set datafile missing \"\"")));
			}
		}

		// Solver experiments
		{
			ExperimentGroup g = new ExperimentGroup("Solver experiments", "Experiments where each input generation method is asked to produce an input producing a precise output.");
			add(g);
			SolverExperimentFactory factory = new SolverExperimentFactory(this);
			factory.add(ReversibleSolver.NAME, new ReversibleSolverFactory(new CircuitFactory(), min_len, max_len, max_tries).setSeed(getSeed()));
			factory.add(GenerateAndTestSolver.NAME, new GenerateAndTestSolverFactory(new PipelineFactory(), min_len, max_len, max_tries).setSeed(getSeed()));
			
			Region big_r = product(
					extension(METHOD, ReversibleSolver.NAME, GenerateAndTestSolver.NAME),
					extension(CONDITION, PreconditionFactory.TWO_EQUAL_TRIM, PreconditionFactory.TWO_EQUAL_DECIMATE),
					extension(ALPHABET_SIZE, alphabet_size),
					extension(ALPHA, 0.5f));
			for (Region r : big_r.all(ALPHA, ALPHABET_SIZE))
			{
				ExperimentTable et = table(METHOD, CONDITION, RATIO, HIT_RATE, TIME);
				et.setTitle("Success ratio for the solving problem, |\u03a3|=" + getInt(r, ALPHABET_SIZE) + ", \u03b1=" + getFloat(r, ALPHA));
				et.add(factory.get(r));
				g.add(factory.get(r));
				add(et);
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
