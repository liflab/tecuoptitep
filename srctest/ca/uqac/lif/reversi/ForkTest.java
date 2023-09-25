package ca.uqac.lif.reversi;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ForkTest
{
  @Test
  public void test1()
  {
    Fork f = new Fork(2);
    f.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(0, 1, 2))));
    f.setTargetOutputs(1, Arrays.asList(new Suggestion(MathList.toList(1, 2, 3)), new Suggestion(MathList.toList(0, 1, 2))));
    List<Suggestion> suggs = f.getSuggestions(0);
    System.out.println(suggs);
  }
}
