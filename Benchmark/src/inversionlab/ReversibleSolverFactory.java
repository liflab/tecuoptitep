package inversionlab;

import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.Reversible;

public class ReversibleSolverFactory extends SolverFactory<Reversible>
{
  /**
   * Name of parameter "&alpha;".
   */
  public static final String ALPHA = "\u03b1";
  
  protected final int m_maxTries;
  
  public ReversibleSolverFactory(PreconditionFactory<Reversible> factory, int min_length, int max_length, int max_tries)
  {
    super(factory, min_length, max_length);
    m_maxTries = max_tries;
  }

  @Override
  protected boolean instantiateSolver(Point pt, Reversible precondition, SolverExperiment e)
  {
    float alpha = -1;
    {
      Object o = pt.get(ALPHA);
      if (o == null)
      {
        return false;
      }
      alpha = ((Number) o).floatValue();
    }
    e.describe(ALPHA, "The probability given to the biased coin toss");
    e.writeInput(ALPHA, alpha);
    e.writeInput(METHOD, ReversibleSolver.NAME);
    Reversible g = m_factory.setCondition(pt, e);
    ReversibleSolver solver = new ReversibleSolver(g, m_maxTries);
    e.setSolver(solver);
    return true;
  }

}
