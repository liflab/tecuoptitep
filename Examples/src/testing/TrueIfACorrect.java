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
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Prefix;
import ca.uqac.lif.cep.util.Booleans;
import ca.uqac.lif.cep.util.Equals;
import ca.uqac.lif.reversi.util.MathList;

public class TrueIfACorrect
{
	@Test
	public void test()
	{
		Processor P = new GroupProcessor(1, 1) {{
			ApplyFunction eq_a = new ApplyFunction(new FunctionTree(Equals.instance, StreamVariable.X, new Constant("a")));
			Cumulate or = new Cumulate(Booleans.or);
			Connector.connect(eq_a, or);
			addProcessors(eq_a, or);
			associateInput(eq_a).associateOutput(or);
		}};
		Processor phi = new GroupProcessor(1, 1) {{
			Fork f = new Fork();
			ApplyFunction eq_a = new ApplyFunction(new FunctionTree(Equals.instance, StreamVariable.X, new Constant("a")));
			Connector.connect(f, 0, eq_a, 0);
			Filter fil = new Filter();
			Connector.connect(eq_a, 0, fil, 1);
			Connector.connect(f, 1, fil, 0);
			TurnInto ti = new TurnInto(true);
			Connector.connect(fil, ti);
			Prefix p = new Prefix(1);
			Connector.connect(ti, p);
			addProcessors(f, eq_a, fil, ti, p);
			associateInput(f).associateOutput(p);
		}};
		Processor Q = new Cumulate(Booleans.or);
		assertHolds(P, phi, Q, MathList.toList("c", "b", "a"));
	}
}
