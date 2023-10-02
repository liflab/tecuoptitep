package inversionlab;

import java.util.List;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.CountDecimate;
import ca.uqac.lif.reversi.Equals;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;
import ca.uqac.lif.reversi.util.AllTruePicker;
import ca.uqac.lif.reversi.util.EndsInPicker;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomInteger;

import static inversionlab.StreamGenerationExperiment.METHOD;

/**
 * A {@link PreconditionFactory} that provides conditions expressed as
 * invertible function circuits from tecuoP titeP.
 */
public class CircuitFactory extends PreconditionFactory
{
	/**
	 * Name of parameter &alpha;.
	 */
	public static final String ALPHA = "\u03b1";

	public CircuitFactory(Picker<Integer> length)
	{
		super(length);
	}

	@Override
	protected boolean setTwoEqualDecimate(Point pt, int alphabet_size, StreamGenerationExperiment e)
	{
		List<Object> alphabet = getStringAlphabet(alphabet_size);
		float alpha = -1;
		{
			Object o = pt.get(ALPHA);
			if (o == null)
			{
				return false;
			}
			alpha = ((Number) o).floatValue();
		}
		e.describe(ALPHA, "The probability given to the biased coin toss");
		e.writeInput(ALPHA, alpha);
		e.writeInput(METHOD, InversionGenerator.NAME);
		RandomBoolean coin = new RandomBoolean(alpha);
		coin.setSeed(getSeed());
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
		DistinctStreamSolver solver = new DistinctStreamSolver(g, new AllTruePicker(new RandomInteger(4, 20).setSeed(getSeed())));
		InversionGenerator gen = new InversionGenerator(solver);
		e.setGenerator(gen);
		return true;
	}

	@Override
	protected boolean setTwoEqualTrim(Point pt, int alphabet_size, StreamGenerationExperiment e)
	{
		List<Object> alphabet = getStringAlphabet(alphabet_size);
		float alpha = -1;
		{
			Object o = pt.get(ALPHA);
			if (o == null)
			{
				return false;
			}
			alpha = ((Number) o).floatValue();
		}
		e.describe(ALPHA, "The probability given to the biased coin toss");
		e.writeInput(ALPHA, alpha);
		e.writeInput(METHOD, InversionGenerator.NAME);
		RandomBoolean coin = new RandomBoolean(alpha);
		coin.setSeed(getSeed());
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
		DistinctStreamSolver solver = new DistinctStreamSolver(g, new EndsInPicker(m_length.duplicate(false)));
		InversionGenerator gen = new InversionGenerator(solver);
		e.setGenerator(gen);
		return true;
	}




}
