package examples;

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

public class TwoEqualTrim
{
	public static void main(String[] args)
	{
		RandomBoolean coin = new RandomBoolean(0.95);
		//Constant<Boolean> coin = new Constant<Boolean>(true);
		List<Object> alphabet = Arrays.asList("a", "b", "c", "d", "e", "f");
		Group g = new Group(1, 1) {{
			Fork f = new Fork();
			associateInput(0, f.getInputPin(0));
			Trim t = new Trim(1, alphabet, coin);
			NodeConnector.connect(f, 0, t, 0);
			Equals eq = new Equals(alphabet, coin);
			NodeConnector.connect(t, 0, eq, 0);
			NodeConnector.connect(f, 1, eq, 1);
			associateOutput(0, eq.getOutputPin(0));
			addNodes(f, t, eq);
		}};
		g.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(false, true))));
		Set<AritalSuggestion> sols = AritalSuggestion.getSuggestions(g);
		System.out.println(sols);
	}
}
