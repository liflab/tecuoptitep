package ca.uqac.lif.reversi;

import java.util.List;

import ca.uqac.lif.dag.Node;

public abstract class UnaryOutputReversible extends Node implements Reversible
{
  protected List<Suggestion> m_targetOutput;
  
  public UnaryOutputReversible(int in_arity)
  {
    super(in_arity, 1);
    m_targetOutput = null;
  }
  
  @Override
  public void setTargetOutputs(int out_index, List<Suggestion> suggestions)
  {
    m_targetOutput = suggestions;
  }
  
  @Override
  public List<Suggestion> getTargetOutputs(int out_index)
  {
    return m_targetOutput;
  }
  
  @SuppressWarnings("unchecked")
  protected MathList<Object> project(int index, MathList<Object> in_list)
  {
    MathList<Object> list = new MathList<Object>();
    for (Object o : in_list)
    {
      MathList<Object> elem = (MathList<Object>) o;
      list.add(elem.get(index));
    }
    return list;
  }
}
