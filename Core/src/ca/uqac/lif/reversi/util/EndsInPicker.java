package ca.uqac.lif.reversi.util;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

public class EndsInPicker implements Picker<MathList<Object>>
{
  protected final Picker<Integer> m_length;
  
  public EndsInPicker(Picker<Integer> len)
  {
    super();
    m_length = len;
  }
  
  public EndsInPicker(int len)
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
      list.add(false);
    }
    list.add(true);
    return list;
  }

  @Override
  public EndsInPicker duplicate(boolean with_state)
  {
    return new EndsInPicker(m_length.duplicate(with_state));
  }
}
