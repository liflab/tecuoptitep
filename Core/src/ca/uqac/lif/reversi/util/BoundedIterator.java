package ca.uqac.lif.reversi.util;

import java.util.Iterator;

import ca.uqac.lif.synthia.Bounded;

public class BoundedIterator<T> implements Iterator<T>
{
  protected final Bounded<T> m_bounded;
  
  public BoundedIterator(Bounded<T> bounded)
  {
    super();
    m_bounded = bounded;
  }
  
  @Override
  public boolean hasNext()
  {
    return !m_bounded.isDone();
  }

  @Override
  public T next()
  {
    return m_bounded.pick();
  }

}
