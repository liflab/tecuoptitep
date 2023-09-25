package ca.uqac.lif.reversi;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ca.uqac.lif.dag.NodeConnector;

public class GroupTest
{
  @Test
  public void test1()
  {
    List<Object> bool_alph = Arrays.asList(true, false);
    Group g = new Group(2, 1);
    {
      And a = new And();
      Trim t = new Trim(1, bool_alph);
      NodeConnector.connect(t, 0, a, 1);
      g.addNodes(a, t);
      g.associateInput(0, a.getInputPin(0));
      g.associateInput(1, t.getInputPin(0));
      g.associateOutput(0, a.getOutputPin(0));
    }
    g.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(true))));
    List<Suggestion> sugs0 = g.getSuggestions(0);
    System.out.println(sugs0);
    List<Suggestion> sugs1 = g.getSuggestions(1);
    System.out.println(sugs1);
  }
}
