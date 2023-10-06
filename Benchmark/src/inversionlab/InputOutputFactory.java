package inversionlab;

/**
 * A factory that produces objects finding inputs for a given output. This lab
 * contains two concrete factories: {@link GeneratorFactory} and
 * {@link SolverFactory}.
 *  
 * @param <T>
 */
public class InputOutputFactory<T>
{
  /**
   * The name of parameter "method".
   */
  public static final String METHOD = "Method";
  
  public static final String MIN_LENGTH = "Min length";
  
  public static final String MAX_LENGTH = "Max length";

  protected final int m_minLength;

  protected final int m_maxLength;
  
  protected int m_seed = 0;
  
  /**
   * The factory used to obtain preconditions based on their name.
   */
  protected final PreconditionFactory<T> m_factory;
  
  public InputOutputFactory(PreconditionFactory<T> factory, int min_length, int max_length)
  {
    super();
    m_factory = factory;
    m_minLength = min_length;
    m_maxLength = max_length;
  }
  
  public InputOutputFactory<T> setSeed(int seed)
  {
    m_seed = seed;
    return this;
  }
  
  public int getSeed()
  {
    return m_seed;
  }

}
