package ca.uqac.lif.reversi.util;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.enumerative.AllPickers;

public class AllPickersList implements Bounded<MathList<Object>>
{
  protected AllPickers m_all;

  public AllPickersList(Bounded<?>[] enum_pickers)
  {
    super();
    m_all = new AllPickers(enum_pickers);
  }
  
  @Override
  public MathList<Object> pick()
  {
    Object[] array = m_all.pick();
    MathList<Object> list = new MathList<Object>();
    for (Object o : array)
    {
      list.add(o);
    }
    return list;
  }

  @Override
  public void reset()
  {
    m_all.reset();
  }

  @Override
  public Picker<MathList<Object>> duplicate(boolean with_state)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isDone()
  {
    return m_all.isDone();
  }

}
