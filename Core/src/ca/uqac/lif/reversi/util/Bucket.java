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
package ca.uqac.lif.reversi.util;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Bucket<T> implements Iterator<T>
{
  protected final Queue<T> m_queue;
  
  public Bucket()
  {
    super();
    m_queue = new ArrayDeque<T>();
  }
  
  @Override
  public boolean hasNext()
  {
    return !m_queue.isEmpty();
  }

  @Override
  public T next()
  {
    if (m_queue.isEmpty())
    {
      throw new NoSuchElementException("Bucket is empty");
    }
    return m_queue.remove();
  }
  
  public void fill(Collection<T> elements)
  {
    m_queue.addAll(elements);
  }

}
