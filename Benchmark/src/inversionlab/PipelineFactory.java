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

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.IfThenElse;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;
import ca.uqac.lif.cep.tmf.Window;
import ca.uqac.lif.cep.util.Equals;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.util.AllTruePicker;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.reversi.util.SomeTruePicker;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomInteger;

/**
 * A {@link GeneratorExperimentFactory} that provides conditions expressed as BeepBeep
 * processor pipelines.
 */
public class PipelineFactory extends PreconditionFactory<PipelineCondition>
{
	public PipelineFactory()
	{
		super();
	}
	
	@Override
  public PipelineFactory setLengthBounds(int min, int max)
  {
  	super.setLengthBounds(min, max);
  	return this;
  }

	@Override
	protected PipelineCondition getTwoEqualDecimate(Point pt, int alphabet_size, Experiment e, boolean always)
	{
		return new PipelineCondition(
				new GroupProcessor(1, 1) {{
					Fork f = new Fork();
					associateInput(0, f, 0);
					CountDecimate t = new CountDecimate(2);
					Connector.connect(f, 0, t, 0);
					ApplyFunction eq = new ApplyFunction(Equals.instance);
					Connector.connect(t, 0, eq, 0);
					Connector.connect(f, 1, eq, 1);
					Trim tr = new Trim(1);
					Connector.connect(eq, tr);
					associateOutput(0, tr, 0);
					addProcessors(f, t, eq, tr);
				}})
		{
			@Override
			public boolean isValid(List<? extends Object> output)
			{
				if (always)
				{
					return !output.contains(Boolean.FALSE);
				}
				return output.contains(Boolean.TRUE);
			}
			
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
	protected PipelineCondition getTwoEqualTrim(Point pt, int alphabet_size, Experiment e, boolean always)
	{
		return new PipelineCondition(
				new GroupProcessor(1, 1) {{
					Fork f = new Fork();
					associateInput(0, f, 0);
					Trim t = new Trim(1);
					Connector.connect(f, 0, t, 0);
					ApplyFunction eq = new ApplyFunction(Equals.instance);
					Connector.connect(t, 0, eq, 0);
					Connector.connect(f, 1, eq, 1);
					associateOutput(0, eq, 0);
					addProcessors(f, t, eq);
				}})
		{
			@Override
			public boolean isValid(List<? extends Object> output)
			{
				if (always)
				{
					return !output.contains(Boolean.FALSE);
				}
				return output.contains(Boolean.TRUE);
			}
			
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
	protected PipelineCondition getAtLeastNInWindow(Point pt, int alphabet_size, Experiment e, boolean always)
	{
		// Width of window and minimum number of a's inside
		int n = 2, width = 4;
		return new PipelineCondition(
				new GroupProcessor(1, 1) {{
					Window w = new Window(new GroupProcessor(1, 1) {{
						ApplyFunction is_a = new ApplyFunction(new FunctionTree(Equals.instance, StreamVariable.X, new Constant("a")));
						ApplyFunction ite = new ApplyFunction(new FunctionTree(IfThenElse.instance, StreamVariable.X, new Constant(1), new Constant(0)));
						Connector.connect(is_a, ite);
						Cumulate sum = new Cumulate(Numbers.addition);
						Connector.connect(ite, sum);
						addProcessors(is_a, ite, sum);
						associateInput(0, is_a, 0);
						associateOutput(0, sum, 0);
					}}, width);
					ApplyFunction gt = new ApplyFunction(new FunctionTree(Numbers.isGreaterOrEqual, StreamVariable.X, new Constant(n)));
					Connector.connect(w, gt);
					addProcessors(w, gt);
					associateInput(0, w, 0);
					associateOutput(0, gt, 0);
				}})
		{
			@Override
			public boolean isValid(List<? extends Object> output)
			{
				if (always)
				{
					return !output.contains(Boolean.FALSE);
				}
				return output.contains(Boolean.TRUE);
			}
			
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
}
