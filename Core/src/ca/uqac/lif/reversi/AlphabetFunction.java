package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

/**
 * A reversible function that requires a fixed alphabet of input symbols.
 */
public abstract class AlphabetFunction extends ReversibleFunction
{
  private final List<Object> m_alphabet;

  public AlphabetFunction(int in_arity, List<Object> alphabet, Picker<Boolean> coin)
  {
    super(in_arity, coin);
    m_alphabet = alphabet;
  }
  
  public AlphabetFunction(int in_arity, List<Object> alphabet)
  {
    this(in_arity, alphabet, new Constant<Boolean>(true));
  }
  
  protected List<Object> getAlphabet()
  {
    return m_alphabet;
    /*
    List<Object> alph = new ArrayList<Object>();
    for (Object o : m_alphabet)
    {
      if (m_coin.pick())
      {
        alph.add(o);
      }
    }
    return alph;
    */
  }

}
