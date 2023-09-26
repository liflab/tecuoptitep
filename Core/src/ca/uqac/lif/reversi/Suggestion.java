package ca.uqac.lif.reversi;

import java.util.Collection;
import java.util.Set;

import ca.uqac.lif.reversi.util.MathSet;

public class Suggestion
{
  protected final Object m_value;
  
  protected MathSet<Association> m_lineage;
  
  public Suggestion(Object value)
  {
    super();
    m_value = value;
    m_lineage = new MathSet<Association>();
  }
  
  @Override
  public int hashCode()
  {
    return m_lineage.hashCode() + m_value.hashCode();
  }
  
  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof Suggestion))
    {
      return false;
    }
    Suggestion s = (Suggestion) o;
    return s.m_lineage.equals(m_lineage) && s.m_value.equals(m_value);
  }
  
  public void addLineage(Association a)
  {
    m_lineage.add(a);
  }
  
  public void addLineage(Collection<Association> a)
  {
    m_lineage.addAll(a);
  }
  
  public Set<Association> getLineage()
  {
    return m_lineage;
  }
  
  public Object getValue()
  {
  	return m_value;
  }
  
  @Override
  public String toString()
  {
    return m_value + ":" + m_lineage;
  }
}
