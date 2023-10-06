package inversionlab;

import ca.uqac.lif.labpal.region.Point;

/**
 * A factory object that produces instances of {@link Generator} from a 
 * {@link Point}.
 * @param <T> The type of generator
 */
public abstract class GeneratorFactory<T> extends InputOutputFactory<T>
{  
  public GeneratorFactory(PreconditionFactory<T> factory, int min_length, int max_length)
  {
    super(factory, min_length, max_length);
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
  
  @Override
  public GeneratorFactory<T> setSeed(int seed)
  {
    super.setSeed(seed);
    return this;
  }
  
  protected abstract boolean instantiateGenerator(Point pt, T precondition, GeneratorExperiment e);
}
