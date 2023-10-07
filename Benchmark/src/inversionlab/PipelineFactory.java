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
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;
import ca.uqac.lif.cep.util.Equals;
import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.region.Point;

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
	protected PipelineCondition getTwoEqualDecimate(Point pt, int alphabet_size, Experiment e)
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
				return output.contains(Boolean.TRUE);
			}
		};
	}

	@Override
	protected PipelineCondition getTwoEqualTrim(Point pt, int alphabet_size, Experiment e)
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
				return output.contains(Boolean.TRUE);
			}
		};
	}
}
