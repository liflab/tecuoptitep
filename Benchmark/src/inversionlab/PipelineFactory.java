package inversionlab;

import static inversionlab.StreamGenerationExperiment.METHOD;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;
import ca.uqac.lif.cep.util.Booleans;
import ca.uqac.lif.cep.util.Equals;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.Choice;


/**
 * A {@link PreconditionFactory} that provides conditions expressed as BeepBeep
 * processor pipelines.
 */
public class PipelineFactory extends PreconditionFactory
{
	public PipelineFactory(int min_length, int max_length)
	{
		super(min_length, max_length);
	}

	@Override
	protected boolean setTwoEqualDecimate(Point pt, int alphabet_size, StreamGenerationExperiment e)
	{
		e.writeInput(METHOD, RandomGenerator.NAME);
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
    RandomGenerator gen = new RandomGenerator(g, getListPicker(alphabet_size)).setAllTrue(true);
    e.setGenerator(gen);
    return true;
	}

	@Override
	protected boolean setTwoEqualTrim(Point pt, int alphabet_size, StreamGenerationExperiment e)
	{
		e.writeInput(METHOD, RandomGenerator.NAME);
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
    RandomGenerator gen = new RandomGenerator(g, getListPicker(alphabet_size));
    e.setGenerator(gen);
    return true;
	}
	
	protected RandomListPicker getListPicker(int alphabet_size)
	{
		Choice<Object> symbol = new Choice<>(new RandomFloat().setSeed(getSeed()));
    float prob = 1f / alphabet_size;
    for (Object o : getStringAlphabet(alphabet_size))
    {
    	symbol.add(o, prob);
    }
    RandomInteger rint = new RandomInteger(m_minLength, m_maxLength).setSeed(getSeed() + 10);
    RandomListPicker rpl = new RandomListPicker(rint, symbol);
    return rpl;
	}
}
