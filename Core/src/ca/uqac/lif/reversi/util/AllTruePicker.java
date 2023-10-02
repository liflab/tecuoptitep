package ca.uqac.lif.reversi.util;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

public class AllTruePicker implements Picker<MathList<Object>>
{
  protected final Picker<Integer> m_length;
  
  public AllTruePicker(Picker<Integer> len)
  {
    super();
    m_length = len;
  }
  
  public AllTruePicker(int len)
  {
    this(new Constant<Integer>(len));
  }

  @Override
  public void reset()
  {
    m_length.reset();
  }

  @Override
  public MathList<Object> pick()
  {
    MathList<Object> list = new MathList<Object>();
    int len = m_length.pick();
    for (int i = 0; i < len; i++)
    {
      list.add(true);
    }
    return list;
  }

  @Override
  public AllTruePicker duplicate(boolean with_state)
  {
    return new AllTruePicker(m_length.duplicate(with_state));
  }
}
