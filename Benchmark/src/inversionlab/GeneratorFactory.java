package inversionlab;

import ca.uqac.lif.labpal.region.Point;

public abstract class GeneratorFactory<T>
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
  
  public GeneratorFactory(PreconditionFactory<T> factory, int min_length, int max_length)
  {
    super();
    m_factory = factory;
    m_minLength = min_length;
    m_maxLength = max_length;
  }
  
  public GeneratorFactory<T> setSeed(int seed)
  {
    m_seed = seed;
    return this;
  }
  
  public int getSeed()
  {
    return m_seed;
  }
  
  public boolean setGenerator(Point pt, GeneratorExperiment e)
  {
    T precondition = m_factory.setCondition(pt, e);
    if (precondition == null)
    {
      return false;
    }
    e.describe(MIN_LENGTH, "The minimum length of the streams to generate");
    e.writeInput(MIN_LENGTH, m_minLength);
    e.describe(MAX_LENGTH, "The maximum length of the streams to generate");
    e.writeInput(MAX_LENGTH, m_maxLength);
    return instantiateGenerator(pt, precondition, e);    
  }
  
  protected abstract boolean instantiateGenerator(Point pt, T precondition, GeneratorExperiment e);
}
