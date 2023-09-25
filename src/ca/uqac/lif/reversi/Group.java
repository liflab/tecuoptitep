package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.uqac.lif.dag.NestedNode;
import ca.uqac.lif.dag.Node;
import ca.uqac.lif.dag.Pin;

public class Group extends NestedNode implements Reversible
{
  protected final Map<Integer,List<Suggestion>> m_targetOutputs;
  
  public Group(int in_arity, int out_arity)
  {
    super(in_arity, out_arity);
    m_targetOutputs = new HashMap<Integer,List<Suggestion>>(out_arity);
  }

  @Override
  public void setTargetOutputs(int out_arity, List<Suggestion> suggestions)
  {
    m_targetOutputs.put(out_arity, suggestions);
    Pin<? extends Node> pin = m_outputAssociations.get(out_arity);
    Node n = pin.getNode();
    if (n instanceof Reversible)
    {
      ((Reversible) n).setTargetOutputs(pin.getIndex(), suggestions);
    }
  }

  @Override
  public List<Suggestion> getSuggestions(int in_arity)
  {
    getSuggestions();
    Pin<? extends Node> pin = m_inputAssociations.get(in_arity);
    Node n = pin.getNode();
    if (n instanceof Reversible)
    {
      return ((Reversible) n).getSuggestions(pin.getIndex());
    }
    return new ArrayList<Suggestion>();
    
  }
  
  protected void getSuggestions()
  {
    Set<Node> processed_nodes = new HashSet<Node>();
    for (int i = 0; i < getOutputArity(); i++)
    {
      Pin<? extends Node> pin = m_outputAssociations.get(i);
      Node n = pin.getNode();
      getSuggestions(n, processed_nodes);
    }
  }
  
  protected void getSuggestions(Node n, Set<Node> processed_nodes)
  {
    if (processed_nodes.contains(n))
    {
      return;
    }
    processed_nodes.add(n);
    if (!(n instanceof Reversible))
    {
      return;
    }
    for (int i = 0; i < n.getOutputArity(); i++)
    {
      if (((Reversible) n).getTargetOutputs(i) == null)
      {
        Node downstream = getDownstream(n, i);
        getSuggestions(downstream, processed_nodes);
      }
    }
    for (int i = 0; i < n.getInputArity(); i++)
    {
      List<Suggestion> sug = ((Reversible) n).getSuggestions(i);
      Node upstream = getUpstream(n, i);
      if (upstream instanceof Reversible)
      {
        ((Reversible) upstream).setTargetOutputs(n.getInputPin(i).getIndex(), sug);
        getSuggestions(upstream, processed_nodes);
      }
    }
  }

  @Override
  public List<Suggestion> getTargetOutputs(int out_index)
  {
    return m_targetOutputs.get(out_index);
  }
  
  protected Node getUpstream(Node n, int in_arity)
  {
    for (Pin<? extends Node> p : n.getInputLinks(in_arity))
    {
      return p.getNode();
    }
    return null;
  }
  
  protected Node getDownstream(Node n, int in_arity)
  {
    for (Pin<? extends Node> p : n.getOutputLinks(in_arity))
    {
      return p.getNode();
    }
    return null;
  }
}
