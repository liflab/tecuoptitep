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

import static inversionlab.MainLab.YES;

import java.util.List;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.CountDecimate;
import ca.uqac.lif.reversi.CumulateAddition;
import ca.uqac.lif.reversi.Equals;
import ca.uqac.lif.reversi.EqualsConstant;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.GreaterOrEqualConstant;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.IfThenElseConstant;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.reversi.Window;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.reversi.util.AllTruePicker;
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
	
	/**
   * Name of parameter "wildcards".
   */
  public static final String WILDCARDS = "Wildcards";

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
	protected ReversibleCondition getTwoEqualDecimate(Point pt, int alphabet_size, Experiment e, boolean always)
	{
		List<Object> alphabet = getStringAlphabet(alphabet_size);
		float alpha = getAlpha(pt, e);
		boolean wildcards = getWildcards(pt, e);
		RandomBoolean coin = new RandomBoolean(alpha);
		coin.setSeed(m_seed);
		return new ReversibleCondition(
				new Group(1, 1) {{
					Fork f = new Fork();
					associateInput(0, f.getInputPin(0));
					CountDecimate t = new CountDecimate(2, alphabet, coin, wildcards);
					NodeConnector.connect(f, 0, t, 0);
					Equals eq = new Equals(alphabet, coin, wildcards);
					NodeConnector.connect(t, 0, eq, 0);
					NodeConnector.connect(f, 1, eq, 1);
					Trim tr = new Trim(1, alphabet, coin, wildcards);
					NodeConnector.connect(eq, 0, tr, 0);
					associateOutput(0, tr.getOutputPin(0));
					addNodes(f, t, eq, tr);
				}})
		{
			@Override
			public Picker<MathList<Object>> getPicker()
			{
				//RandomInteger rint = new RandomInteger(m_minLength, m_maxLength).setSeed(m_seed + 10);
				RotateInteger rint = new RotateInteger(m_minLength, m_maxLength);
				Picker<Boolean> rboo = new RandomBoolean().setSeed(m_seed + 47);
				if (always)
				{
					return new AllTruePicker(rint);
				}
				return new SomeTruePicker(rint, rboo);
			}
		};
	}

	@Override
	protected ReversibleCondition getTwoEqualTrim(Point pt, int alphabet_size, Experiment e, boolean always)
	{
		List<Object> alphabet = getStringAlphabet(alphabet_size);
		float alpha = getAlpha(pt, e);
		boolean wildcards = getWildcards(pt, e);
		RandomBoolean coin = new RandomBoolean(alpha);
		coin.setSeed(m_seed);
		return new ReversibleCondition(
				new Group(1, 1) {{
					Fork f = new Fork();
					associateInput(0, f.getInputPin(0));
					Trim t = new Trim(1, alphabet, coin, wildcards);
					NodeConnector.connect(f, 0, t, 0);
					Equals eq = new Equals(alphabet, coin, wildcards);
					NodeConnector.connect(t, 0, eq, 0);
					NodeConnector.connect(f, 1, eq, 1);
					associateOutput(0, eq.getOutputPin(0));
					addNodes(f, t, eq);
				}})
		{
			@Override
			public Picker<MathList<Object>> getPicker()
			{
				//RandomInteger rint = new RandomInteger(m_minLength, m_maxLength).setSeed(m_seed + 10);
				RotateInteger rint = new RotateInteger(m_minLength, m_maxLength);
				Picker<Boolean> rboo = new RandomBoolean().setSeed(m_seed + 47);
				if (always)
				{
					return new AllTruePicker(rint);
				}
				return new SomeTruePicker(rint, rboo);
			}
		};
	}
	
	@Override
	protected ReversibleCondition getAtLeastNInWindow(Point pt, int alphabet_size, Experiment e, boolean always)
	{
		// Width of window and minimum number of a's inside
		int n = 2, width = 4;
		List<Object> range = getIntegerAlphabet(n + 1);
		List<Object> alphabet = getStringAlphabet(alphabet_size);
		float alpha = getAlpha(pt, e);
		RandomBoolean coin = new RandomBoolean(alpha);
		coin.setSeed(m_seed);
		boolean wildcards = getWildcards(pt, e);
		return new ReversibleCondition(
				new Group(1, 1) {{
					Window win = new Window(4, new Group(1, 1, coin) {{
						EqualsConstant eq_a = new EqualsConstant("A", alphabet, wildcards);
						IfThenElseConstant itec = new IfThenElseConstant(1, 0);
						NodeConnector.connect(eq_a, 0, itec, 0);
						CumulateAddition sum = new CumulateAddition(range, wildcards);
						NodeConnector.connect(itec, 0, sum, 0);
						Trim trim = new Trim(3, range, wildcards);
						NodeConnector.connect(sum, 0, trim, 0);
						addNodes(eq_a, itec, sum, trim);
						associateInput(0, eq_a.getInputPin(0));
						associateOutput(0, trim.getOutputPin(0));
					}});
					GreaterOrEqualConstant geqc = new GreaterOrEqualConstant(n, range, coin, wildcards);
					NodeConnector.connect(win, 0, geqc, 0);
					addNodes(win, geqc);
					associateInput(0, win.getInputPin(0));
					associateOutput(0, geqc.getOutputPin(0));
				}})
		{
			@Override
			public Picker<MathList<Object>> getPicker()
			{
				//RandomInteger rint = new RandomInteger(m_minLength, m_maxLength).setSeed(m_seed + 10);
				RotateInteger rint = new RotateInteger(m_minLength, m_maxLength);
				Picker<Boolean> rboo = new RandomBoolean().setSeed(m_seed + 47);
				if (always)
				{
					return new AllTruePicker(rint);
				}
				return new SomeTruePicker(rint, rboo);
			}
		};
	}
	
	protected Float getAlpha(Point pt, Experiment e)
	{
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
    return alpha;
	}
	
	protected boolean getWildcards(Point pt, Experiment e)
	{
	  boolean b = false;
	  e.describe(WILDCARDS, "Whether wilcard events are allowed in the inversion method");
	  Object o = pt.get(WILDCARDS);
    if (o != null && o.toString().compareTo(YES) == 0)
    {
      b = true;
    }
    e.writeInput(WILDCARDS, b ? MainLab.YES : MainLab.NO);
    return b;
	}
}
