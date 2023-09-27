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
