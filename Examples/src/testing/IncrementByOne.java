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

import static ca.uqac.lif.reversi.assertions.PeebPeebAssertions.assertHolds;

import org.junit.Test;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;
import ca.uqac.lif.cep.util.Booleans;
import ca.uqac.lif.cep.util.Equals;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.reversi.util.MathList;

/**
 * Checks an assertion where the postcondition depends on both the input and
 * the output. The precondition states that if two successive (numerical)
 * events differ by 1, the event at index <i>n</i> in the output stream
 * is the value of event at index <i>n</i> in the input stream plus one.
 * 
 * @author Sylvain Hallé
 */
public class IncrementByOne
{
	@Test
	public void test()
	{
		Processor P = new GroupProcessor(1, 1) {{
			Fork f = new Fork();
			ApplyFunction eq_plus_one = new ApplyFunction(new FunctionTree(Equals.instance,
					StreamVariable.Y, new FunctionTree(Numbers.addition,
							StreamVariable.X, new Constant(1)))); // y = x + 1
			Connector.connect(f, 0, eq_plus_one, 0);
			Trim trim = new Trim(1);
			Connector.connect(f, 1, trim, 0);
			Connector.connect(trim, 0, eq_plus_one, 1);
			Cumulate always = new Cumulate(Booleans.and);
			Connector.connect(eq_plus_one, always);
			addProcessors(f, eq_plus_one, trim, always);
			associateInput(f);
			associateOutput(always);
		}};
		Processor Q = new ApplyFunction(new FunctionTree(Equals.instance,
					StreamVariable.Y, new FunctionTree(Numbers.addition,
							StreamVariable.X, new Constant(1)))); // same
		Processor phi = new Trim(1);
		assertHolds(P, phi, Q, MathList.toList(1, 3, 3));
	}
}
