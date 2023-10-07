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

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.CountDecimate;
import ca.uqac.lif.reversi.Equals;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.reversi.util.EndsInPicker;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.reversi.util.SomeTruePicker;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomInteger;

/**
 * A {@link GeneratorExperimentFactory} that provides conditions expressed as
 * invertible function circuits from tecuoP titeP.
 */
@SuppressWarnings("unused")
public class ReversibleFactory extends PreconditionFactory<ReversibleCondition>
{
	/**
	 * Name of parameter "&alpha;".
	 */
	public static final String ALPHA = "\u03b1";

	public ReversibleFactory()
	{
		super();
	}
	
  @Override
  public ReversibleFactory setLengthBounds(int min, int max)
  {
  	super.setLengthBounds(min, max);
  	return this;
  }

	@Override
	protected ReversibleCondition getTwoEqualDecimate(Point pt, int alphabet_size, Experiment e)
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
		return new ReversibleCondition(
				new Group(1, 1) {{
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
				}})
		{
			@Override
			public Picker<MathList<Object>> getPicker()
			{
				RandomInteger rint = new RandomInteger(m_minLength, m_maxLength).setSeed(m_seed + 10);
				Picker<Boolean> rboo = new RandomBoolean().setSeed(m_seed + 47);
				return new SomeTruePicker(rint, rboo);
			}
		};
	}

	@Override
	protected ReversibleCondition getTwoEqualTrim(Point pt, int alphabet_size, Experiment e)
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
		return new ReversibleCondition(
				new Group(1, 1) {{
					Fork f = new Fork();
					associateInput(0, f.getInputPin(0));
					Trim t = new Trim(1, alphabet, coin);
					NodeConnector.connect(f, 0, t, 0);
					Equals eq = new Equals(alphabet, coin);
					NodeConnector.connect(t, 0, eq, 0);
					NodeConnector.connect(f, 1, eq, 1);
					associateOutput(0, eq.getOutputPin(0));
					addNodes(f, t, eq);
				}})
		{
			@Override
			public Picker<MathList<Object>> getPicker()
			{
				RandomInteger rint = new RandomInteger(m_minLength, m_maxLength).setSeed(m_seed + 10);
				Picker<Boolean> rboo = new RandomBoolean().setSeed(m_seed + 47);
				return new SomeTruePicker(rint, rboo);
			}
		};
	}
}
