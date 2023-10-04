package inversionlab;

import static ca.uqac.lif.labpal.region.ExtensionDomain.extension;
import static ca.uqac.lif.labpal.region.ProductRegion.product;
import static ca.uqac.lif.labpal.table.ExperimentTable.table;
import static ca.uqac.lif.labpal.table.TransformedTable.transform;
import static inversionlab.CircuitFactory.ALPHA;
import static inversionlab.PreconditionFactory.ALPHABET_SIZE;
import static inversionlab.PreconditionFactory.CONDITION;
import static inversionlab.CandidateGenerationExperiment.ELEMENTS;
import static inversionlab.CandidateGenerationExperiment.METHOD;
import static inversionlab.CandidateGenerationExperiment.TIME;


import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.plot.Plot;
import ca.uqac.lif.labpal.region.Region;
import ca.uqac.lif.labpal.table.ExperimentTable;
import ca.uqac.lif.labpal.table.TransformedTable;
import ca.uqac.lif.labpal.util.CliParser;
import ca.uqac.lif.labpal.util.CliParser.Argument;
import ca.uqac.lif.labpal.util.CliParser.ArgumentMap;
import ca.uqac.lif.spreadsheet.chart.gnuplot.GnuplotScatterplot;
import ca.uqac.lif.spreadsheet.functions.ExpandAsColumns;

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
		int trace_limit = intArgOrDefault(args, "traces", 10);
		System.out.println("Trace limit:\t" + trace_limit);
		
		CandidateGenerationExperimentFactory factory = new CandidateGenerationExperimentFactory(this).setSizeLimit(trace_limit);
		factory.add(InversionGenerator.NAME, new CircuitFactory(min_len, max_len).setSeed(getSeed()));
		factory.add(GenerateAndTest.NAME, new PipelineFactory(min_len, max_len).setSeed(getSeed()));
		
		Region big_r = product(
				extension(METHOD, InversionGenerator.NAME, GenerateAndTest.NAME),
				extension(CONDITION, PreconditionFactory.TWO_EQUAL_TRIM, PreconditionFactory.TWO_EQUAL_DECIMATE),
				extension(ALPHABET_SIZE, alphabet_size),
				extension(ALPHA, 0.1f));
		for (Region r : big_r.all(CONDITION, ALPHA, ALPHABET_SIZE))
		{
			ExperimentTable et = table(METHOD, ELEMENTS, TIME);
			et.add(factory.get(r));
			TransformedTable tt = transform(new ExpandAsColumns(METHOD, TIME), et);
			add(tt);
			add(new Plot(tt, new GnuplotScatterplot().setCustomHeader("set datafile missing \"\"")));
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
	
	public static int intArgOrDefault(ArgumentMap args, String name, int int_default)
	{
		if (args.hasOption(name))
		{
			return Integer.parseInt(args.getOptionValue(name).trim());
		}
		return int_default;
	}

}
