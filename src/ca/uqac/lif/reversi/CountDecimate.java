package ca.uqac.lif.reversi;

import java.util.List;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.enumerative.AllPickers;
import ca.uqac.lif.synthia.sequence.Playback;

public class CountDecimate extends FixedAlphabetUnaryOutputReversible
{
  protected final int m_numDecimate;
  
  public CountDecimate(int num_trim, List<Object> alphabet)
  {
    super(1, alphabet);
    m_numDecimate = num_trim;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Suggestion> getSuggestions(int in_arity)
  {
    List<Suggestion> suggs = new MathList<Suggestion>();
    int sugg_index = 0;
    Bounded<?>[] pickers = new Bounded<?>[m_numDecimate];
    for (int i = 0; i < pickers.length; i++)
    {
      pickers[i] = new Playback<Object>(m_alphabet.toArray()).setLoop(false);
    }
    for (int i = 0; i < m_targetOutput.size(); i++)
    {
      Suggestion out_sug = m_targetOutput.get(i);
      AllPickers all = new AllPickers(pickers);
      all.reset();
      while (!all.isDone())
      {
        MathList<Object> out_list = new MathList<Object>();
        Object[] to_append = all.pick();
        for (Object o : to_append)
        {
          out_list.add(o);
        }
        out_list.addAll((List<Object>) out_sug.m_value);
        Suggestion sugg = new Suggestion(out_list);
        sugg.addLineage(out_sug.getLineage());
        sugg.addLineage(new Association(this, sugg_index));
        suggs.add(sugg);
        sugg_index++;
      }
    }
    return suggs;
  }
}
