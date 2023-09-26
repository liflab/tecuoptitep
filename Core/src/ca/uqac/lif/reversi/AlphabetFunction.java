package ca.uqac.lif.reversi;

import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

/**
 * A reversible function that requires a fixed alphabet of input symbols.
 */
public abstract class AlphabetFunction extends ReversibleFunction
{
  protected final List<Object> m_alphabet;

  public AlphabetFunction(int in_arity, List<Object> alphabet, Picker<Boolean> coin)
  {
    super(in_arity, coin);
    m_alphabet = alphabet;
  }
  
  public AlphabetFunction(int in_arity, List<Object> alphabet)
  {
    this(in_arity, alphabet, new Constant<Boolean>(true));
  }

}
