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
