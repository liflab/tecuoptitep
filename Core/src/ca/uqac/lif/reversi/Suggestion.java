/*
    Inversion of function circuits
    Copyright (C) 2023 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
