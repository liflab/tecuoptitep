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

public class WindowSum
{
	public static void main(String[] args)
	{
		List<Object> range = Arrays.asList(1, 2, 3, 4);
		Window win = new Window(3, new Group(1, 1) {{
		  CumulateAddition sum = new CumulateAddition(range, false);
			Trim trim = new Trim(2, range, false);
			NodeConnector.connect(sum, 0, trim, 0);
			addNodes(sum, trim);
			associateInput(0, sum.getInputPin(0));
			associateOutput(0, trim.getOutputPin(0));
		}});
		win.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(4, 3, 5))));
		List<Suggestion> in_sugs = win.getSuggestions(0);
		System.out.println(in_sugs.size());
		System.out.println(in_sugs);
	}
}
