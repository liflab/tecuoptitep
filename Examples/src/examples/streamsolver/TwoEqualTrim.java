package examples.streamsolver;

import java.util.Arrays;
import java.util.List;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.reversi.Equals;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;
import ca.uqac.lif.reversi.util.EndsInPicker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomInteger;

public class TwoEqualTrim
{

  public static void main(String[] args)
  {
    List<Object> alphabet = Arrays.asList("a", "b", "c", "d");
    RandomBoolean coin = new RandomBoolean(0.5);
    coin.setSeed(10);
    DistinctStreamSolver solver = new DistinctStreamSolver(new Group(1, 1) {{
      Fork f = new Fork();
      associateInput(0, f.getInputPin(0));
      Trim t = new Trim(1, alphabet, coin);
      NodeConnector.connect(f, 0, t, 0);
      Equals eq = new Equals(alphabet, coin);
      NodeConnector.connect(t, 0, eq, 0);
      NodeConnector.connect(f, 1, eq, 1);
      associateOutput(0, eq.getOutputPin(0));
      addNodes(f, t, eq);
    }}, new EndsInPicker(new RandomInteger(4, 20)));
    while (!solver.isDone())
    {
      System.out.println(solver.pick());
    }
  }

}
