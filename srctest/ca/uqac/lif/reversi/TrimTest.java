package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TrimTest
{
  @Test
  public void test1()
  {
    Trim t = new Trim(1, Arrays.asList("a", "b", "c"));
    List<Suggestion> sug_list = new ArrayList<Suggestion>();
    sug_list.add(new Suggestion(MathList.toList("a", "b")));
    t.setTargetOutputs(0, sug_list);
    List<Suggestion> in_sugs = t.getSuggestions(0);
    for (Suggestion s : in_sugs)
    {
      System.out.println(s);
    }
  }
}
