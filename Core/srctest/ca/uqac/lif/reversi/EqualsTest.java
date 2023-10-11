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
package ca.uqac.lif.reversi;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.reversi.util.MathList;

public class EqualsTest
{
	@Test
	public void test1()
	{
		Equals eq = new Equals(MathList.toList("a", "b", "c"));
		eq.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(false, true, false))));
		System.out.println(AritalSuggestion.getSuggestions(eq));
	}
	
	@Test
	public void test2()
	{
		Group g = new Group(1, 1) {{
			Fork f = new Fork();
			associateInput(0, f.getInputPin(0));
			Equals eq = new Equals(MathList.toList("a", "b", "c"));
			NodeConnector.connect(f, 0, eq, 0);
			NodeConnector.connect(f, 1, eq, 1);
			associateOutput(0, eq.getOutputPin(0));
			addNodes(f, eq);
		}};
		g.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(true, true, true))));
		System.out.println(AritalSuggestion.getSuggestions(g));
	}
	
	@Test
	public void test3()
	{
		Equals eq = new Equals(MathList.toList("a", "b", "c", "d"));
		eq.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(false, true, false, true))));
		Set<AritalSuggestion> sugs = AritalSuggestion.getSuggestions(eq);
		assertEquals(2304, sugs.size());
	}
}
