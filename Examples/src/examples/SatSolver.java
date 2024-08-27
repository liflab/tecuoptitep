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
package examples;

import java.util.Arrays;
import java.util.Set;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.reversi.And;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.Not;
import ca.uqac.lif.reversi.Or;
import ca.uqac.lif.reversi.Suggestion;
import ca.uqac.lif.reversi.util.MathList;

public class SatSolver
{
	public static void main(String[] args)
	{
		Group phi = new Group(2, 1) {{
			Fork f = new Fork();
			associateInput(0, f.getInputPin(0));
			Not not = new Not();
			NodeConnector.connect(f, 1, not, 0);
			And and = new And();
			NodeConnector.connect(not, 0, and, 0);
			associateInput(1, and.getInputPin(1));
			Or or = new Or();
			NodeConnector.connect(f, 0, or, 0);
			NodeConnector.connect(and, 0, or, 1);
			associateOutput(0, or.getOutputPin(0));
			addNodes(f, not, and, or);
		}};
		phi.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(true))));
		Set<AritalSuggestion> sols = AritalSuggestion.getSuggestions(phi);
		System.out.println(sols);
	}

}
