package inversionlab;

import ca.uqac.lif.labpal.region.Point;

public abstract class SolverFactory<T> extends InputOutputFactory<T>
{
	public SolverFactory(PreconditionFactory<T> factory, int min_length, int max_length)
	{
		super(factory, min_length, max_length);
	}
	
	public boolean setSolver(Point pt, SolverExperiment e)
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
    return instantiateSolver(pt, precondition, e);    
  }
	
  @Override
  public SolverFactory<T> setSeed(int seed)
  {
    super.setSeed(seed);
    return this;
  }
  
  protected abstract boolean instantiateSolver(Point pt, T precondition, SolverExperiment e);

}
