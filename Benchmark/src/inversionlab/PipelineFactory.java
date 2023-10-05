package inversionlab;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;
import ca.uqac.lif.cep.util.Booleans;
import ca.uqac.lif.cep.util.Equals;
import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.region.Point;

/**
 * A {@link GeneratorExperimentFactory} that provides conditions expressed as BeepBeep
 * processor pipelines.
 */
public class PipelineFactory extends PreconditionFactory<GroupProcessor>
{
	public PipelineFactory()
	{
		super();
	}

	@Override
	protected GroupProcessor getTwoEqualDecimate(Point pt, int alphabet_size, Experiment e)
	{
		GroupProcessor g = new GroupProcessor(1, 1) {{
      Fork f = new Fork();
      associateInput(0, f, 0);
      CountDecimate t = new CountDecimate(2);
      Connector.connect(f, 0, t, 0);
      ApplyFunction eq = new ApplyFunction(Equals.instance);
      Connector.connect(t, 0, eq, 0);
      Connector.connect(f, 1, eq, 1);
      Cumulate tr = new Cumulate(Booleans.and);
      Connector.connect(eq, tr);
      associateOutput(0, tr, 0);
      addProcessors(f, t, eq, tr);
    }};
    return g;
	}

	@Override
	protected GroupProcessor getTwoEqualTrim(Point pt, int alphabet_size, Experiment e)
	{
		GroupProcessor g = new GroupProcessor(1, 1) {{
      Fork f = new Fork();
      associateInput(0, f, 0);
      Trim t = new Trim(1);
      Connector.connect(f, 0, t, 0);
      ApplyFunction eq = new ApplyFunction(Equals.instance);
      Connector.connect(t, 0, eq, 0);
      Connector.connect(f, 1, eq, 1);
      associateOutput(0, eq, 0);
      addProcessors(f, t, eq);
    }};
    return g;
	}
}
