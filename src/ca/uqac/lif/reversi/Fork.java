package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqac.lif.dag.Node;

public class Fork extends Node implements Reversible
{
  protected final Map<Integer,List<Suggestion>> m_targetOutputs;
  
  public Fork(int out_arity)
  {
    super(1, out_arity);
    m_targetOutputs = new HashMap<Integer,List<Suggestion>>(out_arity);
  }

  @Override
  public void setTargetOutputs(int out_index, List<Suggestion> suggestions)
  {
    m_targetOutputs.put(out_index, suggestions);
  }

  @Override
  public List<Suggestion> getTargetOutputs(int out_index)
  {
    return m_targetOutputs.get(out_index);
  }

  @Override
  public List<Suggestion> getSuggestions(int in_arity)
  {
    List<Suggestion> sugs = new ArrayList<Suggestion>();
    for (int i = 0; i < getOutputArity(); i++)
    {
      List<Suggestion> to = m_targetOutputs.get(i);
      for (Suggestion s : to)
      {
        if (!sugs.contains(s) && isCompatible(i, s))
        {
          sugs.add(s);
        }
      }
    }
    return sugs;
  }
  
  protected boolean isCompatible(int index, Suggestion s)
  {
    for (int i = 0; i < getOutputArity(); i++)
    {
      if (i == index)
      {
        continue;
      }
      boolean ok = false;
      List<Suggestion> to = m_targetOutputs.get(i);
      for (Suggestion to_s : to)
      {
        if (s.m_value.equals(to_s.m_value))
        {
          ok = true;
        }
      }
      if (!ok)
      {
        return false;
      }
    }
    return true;
  }
}
