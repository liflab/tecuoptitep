package ca.uqac.lif.reversi;

import java.util.Arrays;

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
}
