package ca.uqac.lif.reversi;

import java.util.List;

public abstract class FixedAlphabetUnaryOutputReversible extends UnaryOutputReversible
{
  protected final List<Object> m_alphabet;

  public FixedAlphabetUnaryOutputReversible(int in_arity, List<Object> alphabet)
  {
    super(in_arity);
    m_alphabet = alphabet;
  }

}
