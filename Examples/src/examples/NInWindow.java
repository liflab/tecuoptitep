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
import java.util.List;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.reversi.CumulateAddition;
import ca.uqac.lif.reversi.EqualsConstant;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.IfThenElseConstant;
import ca.uqac.lif.reversi.Suggestion;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.reversi.Window;
import ca.uqac.lif.reversi.util.MathList;

public class NInWindow
{
	public static void main(String[] args)
	{
		List<Object> alphabet = Arrays.asList("a", "b", "c");
		List<Object> range = Arrays.asList(0, 1, 2, 3, 4);
		Window win = new Window(4, new Group(1, 1) {{
			EqualsConstant eq_a = new EqualsConstant("a", alphabet, false);
			IfThenElseConstant itec = new IfThenElseConstant(1, 0);
			NodeConnector.connect(eq_a, 0, itec, 0);
			CumulateAddition sum = new CumulateAddition(range, false);
			NodeConnector.connect(itec, 0, sum, 0);
			Trim trim = new Trim(3, range, false);
			NodeConnector.connect(sum, 0, trim, 0);
			addNodes(eq_a, itec, sum, trim);
			associateInput(0, eq_a.getInputPin(0));
			associateOutput(0, trim.getOutputPin(0));
		}});
		win.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(2, 2, 1, 2))));
		List<Suggestion> in_sugs = win.getSuggestions(0);
		System.out.println(in_sugs.size());
	}
}
