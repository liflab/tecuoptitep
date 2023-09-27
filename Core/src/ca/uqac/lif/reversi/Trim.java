package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.enumerative.AllElements;
import ca.uqac.lif.synthia.enumerative.AllPickers;
import ca.uqac.lif.synthia.util.Constant;

public class Trim extends AlphabetFunction
{
  protected final int m_numTrim;
  
  public Trim(int num_trim, List<Object> alphabet, Picker<Boolean> coin)
  {
    super(1, alphabet, coin);
    m_numTrim = num_trim;
  }
  
  public Trim(int num_trim, List<Object> alphabet)
  {
  	this(num_trim, alphabet, new Constant<Boolean>(true));
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getSuggestions()
  {
    m_suggestedInputs.put(0, new ArrayList<Suggestion>());
    int sugg_index = 0;
    Bounded<?>[] pickers = new Bounded<?>[m_numTrim];
    for (int i = 0; i < pickers.length; i++)
    {
      pickers[i] = new AllElements<Object>(getAlphabet(), true, false);
    }
    for (int i = 0; i < m_targetOutput.size(); i++)
    {
      Suggestion out_sug = m_targetOutput.get(i);
      AllPickers all = new AllPickers(pickers);
      all.reset();
      while (m_coin.pick() && !all.isDone())
      {
      	Object[] to_append = all.pick();
        MathList<Object> out_list = new MathList<Object>();
        for (Object o : to_append)
        {
          out_list.add(o);
        }
        out_list.addAll((List<Object>) out_sug.m_value);
        Suggestion sugg = new Suggestion(out_list);
        sugg.addLineage(out_sug.getLineage());
        sugg.addLineage(new Association(this, sugg_index));
        m_suggestedInputs.get(0).add(sugg);
        sugg_index++;
      }
    }
  }
}
