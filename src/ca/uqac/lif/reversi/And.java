package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.sequence.Playback;

public class And extends UnaryOutputReversible
{
  public And()
  {
    super(2);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Suggestion> getSuggestions(int in_index)
  {
    List<Suggestion> out_list = new ArrayList<Suggestion>();
    for (int i = 0; i < m_targetOutput.size(); i++)
    {
      Suggestion s = m_targetOutput.get(i);
      MathList<Object> sequence = (MathList<Object>) s.m_value;
      Bounded<?>[] pickers = new Bounded<?>[sequence.size()];
      for (int j = 0; j < pickers.length; j++)
      {
        pickers[j] = (Boolean) sequence.get(j) ? new Playback<MathList<?>>(MathList.toList(true, true)).setLoop(false) : new Playback<MathList<?>>(MathList.toList(false, true), MathList.toList(true, false), MathList.toList(false, false)).setLoop(false);
      }
      AllPickersList apl = new AllPickersList(pickers);
      int sug_index = 0;
      while (!apl.isDone())
      {
        MathList<Object> list = (MathList<Object>) apl.pick();
        MathList<Object> bool_list = project(in_index, list);
        Suggestion new_s = new Suggestion(bool_list);
        new_s.addLineage(s.getLineage());
        new_s.addLineage(new Association(this, sug_index));
        out_list.add(new_s);
        sug_index++;
      }
    }
    return out_list;
  }
}
