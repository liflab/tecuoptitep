package ca.uqac.lif.reversi;

import java.util.List;

public interface Reversible
{
  public void setTargetOutputs(int out_index, List<Suggestion> suggestions);
  
  /*@ null @*/ public List<Suggestion> getTargetOutputs(int out_index);
  
  /*@ non_null @*/ public List<Suggestion> getSuggestions(int in_arity);
}
