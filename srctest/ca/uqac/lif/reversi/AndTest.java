package ca.uqac.lif.reversi;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class AndTest
{
  @Test
  public void test1()
  {
    And a = new And();
    a.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(true, true, false, true))));
    List<Suggestion> suggs = a.getSuggestions(1);
    for (Suggestion s : suggs)
    {
      System.out.println(s);
    }
  }
}
