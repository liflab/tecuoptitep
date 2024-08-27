/*
    Inversion of BeepBeep processor chains
    Copyright (C) 2023-2024 Laboratoire d'informatique formelle
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
package testing;

import org.junit.Test;

import static ca.uqac.lif.reversi.assertions.PeebPeebAssertions.assertHolds;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.functions.TurnInto;
import ca.uqac.lif.cep.tmf.Prefix;
import ca.uqac.lif.cep.tmf.Trim;
import ca.uqac.lif.cep.util.Booleans;
import ca.uqac.lif.cep.util.Equals;
import ca.uqac.lif.reversi.util.MathList;

public class TrueIfAIncorrect
{
	@Test
	public void test()
	{
		Processor phi = new GroupProcessor(1, 1) {{
			Trim t = new Trim(1);
			TurnInto t_true = new TurnInto(true);
			Prefix p = new Prefix(1);
			Connector.connect(t, t_true, p);
			addProcessors(t, t_true, p);
			associateInput(t).associateOutput(p);
		}};
		assertHolds(
				new GroupProcessor(1, 1) {{
					ApplyFunction eq_a = new ApplyFunction(new FunctionTree(
							Equals.instance, StreamVariable.X, new Constant("a")));
					Cumulate or = new Cumulate(Booleans.or);
					Connector.connect(eq_a, or);
					addProcessors(eq_a, or);
					associateInput(eq_a).associateOutput(or);
				}},
				phi, 
				new Cumulate(Booleans.or), 
				MathList.toList("c", "b", "a"));
	}
}
