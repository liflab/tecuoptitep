package inversionlab;

import java.util.List;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.CountDecimate;
import ca.uqac.lif.reversi.Equals;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.Reversible;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.synthia.random.RandomBoolean;

/**
 * A {@link GeneratorExperimentFactory} that provides conditions expressed as
 * invertible function circuits from tecuoP titeP.
 */
public class CircuitFactory extends PreconditionFactory<Reversible>
{
	/**
	 * Name of parameter "&alpha;".
	 */
	public static final String ALPHA = "\u03b1";

	public CircuitFactory()
	{
		super();
	}

	@Override
	protected Group getTwoEqualDecimate(Point pt, int alphabet_size, Experiment e)
	{
		List<Object> alphabet = getStringAlphabet(alphabet_size);
		float alpha = -1;
		{
			Object o = pt.get(ALPHA);
			if (o == null)
			{
				return null;
			}
			alpha = ((Number) o).floatValue();
		}
		e.describe(ALPHA, "The probability given to the biased coin toss");
		e.writeInput(ALPHA, alpha);
		RandomBoolean coin = new RandomBoolean(alpha);
		coin.setSeed(m_seed);
		Group g = new Group(1, 1) {{
			Fork f = new Fork();
			associateInput(0, f.getInputPin(0));
			CountDecimate t = new CountDecimate(2, alphabet, coin);
			NodeConnector.connect(f, 0, t, 0);
			Equals eq = new Equals(alphabet, coin);
			NodeConnector.connect(t, 0, eq, 0);
			NodeConnector.connect(f, 1, eq, 1);
			Trim tr = new Trim(1, alphabet, coin);
			NodeConnector.connect(eq, 0, tr, 0);
			associateOutput(0, tr.getOutputPin(0));
			addNodes(f, t, eq, tr);
		}};
		return g;
	}

	@Override
	protected Group getTwoEqualTrim(Point pt, int alphabet_size, Experiment e)
	{
		List<Object> alphabet = getStringAlphabet(alphabet_size);
		float alpha = -1;
		{
			Object o = pt.get(ALPHA);
			if (o == null)
			{
				return null;
			}
			alpha = ((Number) o).floatValue();
		}
		e.describe(ALPHA, "The probability given to the biased coin toss");
		e.writeInput(ALPHA, alpha);
		RandomBoolean coin = new RandomBoolean(alpha);
		coin.setSeed(m_seed);
		Group g = new Group(1, 1) {{
			Fork f = new Fork();
			associateInput(0, f.getInputPin(0));
			Trim t = new Trim(1, alphabet, coin);
			NodeConnector.connect(f, 0, t, 0);
			Equals eq = new Equals(alphabet, coin);
			NodeConnector.connect(t, 0, eq, 0);
			NodeConnector.connect(f, 1, eq, 1);
			associateOutput(0, eq.getOutputPin(0));
			addNodes(f, t, eq);
		}};
		return g;
	}
}
