package inversionlab;

import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class OutputPicker implements Picker<AritalSuggestion>
{
  protected final int m_arity;
  
  protected final Picker<MathList<Object>> m_picker;
  
  public OutputPicker(int arity, Picker<MathList<Object>> picker)
  {
    super();
    m_picker = picker;
    m_arity = arity;
  }
  
  @Override
  public void reset()
  {
    m_picker.reset();
  }

  @Override
  public AritalSuggestion pick()
  {
    AritalSuggestion s = new AritalSuggestion(m_arity);
    for (int i = 0; i < m_arity; i++)
    {
      s.set(i, m_picker.pick());
    }
    return s;
  }

  @Override
  public Picker<AritalSuggestion> duplicate(boolean with_state)
  {
    throw new UnsupportedOperationException();
  }
}
