package ca.uqac.lif.reversi;

public class Association
{
  protected final Object m_node;
  
  protected final int m_suggestionIndex;
  
  public Association(Object node, int index)
  {
    super();
    m_node = node;
    m_suggestionIndex = index;
  }
  
  public boolean isCompatible(Association a)
  {
    if (a.m_node.equals(m_node))
    {
      return a.m_suggestionIndex == m_suggestionIndex;
    }
    return true;
  }
  
  @Override
  public int hashCode()
  {
    return m_node.hashCode() + m_suggestionIndex;
  }
  
  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof Association))
    {
      return false;
    }
    Association a = (Association) o;
    return a.m_node.equals(m_node) && a.m_suggestionIndex == m_suggestionIndex;
  }
  
  @Override
  public String toString()
  {
    return m_node + "#" + m_suggestionIndex;
  }
}
