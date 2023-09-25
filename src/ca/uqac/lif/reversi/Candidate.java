package ca.uqac.lif.reversi;

import java.util.Iterator;

public interface Candidate
{
  /*@ non_null @*/ public Object getFor();
  
  /*@ non_null @*/ public Iterator<?> getSuggestions();
}
