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

import static ca.uqac.lif.reversi.AlphabetFunction.WILDCARD;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.Equals;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.Suggestion;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.util.Constant;

public class TwoEqualTrimWildcard
{
	public static void main(String[] args)
	{
	  boolean wildcards = true;
		//RandomBoolean coin = new RandomBoolean(0.95);
		Constant<Boolean> coin = new Constant<Boolean>(true);
		List<Object> alphabet = Arrays.asList("a", "b", "c", "d");
		Group g = new Group(1, 1) {{
			Fork f = new Fork();
			associateInput(0, f.getInputPin(0));
			Trim t = new Trim(1, alphabet, coin, wildcards);
			NodeConnector.connect(f, 0, t, 0);
			Equals eq = new Equals(alphabet, coin, wildcards);
			NodeConnector.connect(t, 0, eq, 0);
			NodeConnector.connect(f, 1, eq, 1);
			associateOutput(0, eq.getOutputPin(0));
			addNodes(f, t, eq);
		}};
		g.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(WILDCARD, WILDCARD, WILDCARD, true))));
		Set<AritalSuggestion> sols = AritalSuggestion.getSuggestions(g);
		System.out.println(sols);
	}
}
