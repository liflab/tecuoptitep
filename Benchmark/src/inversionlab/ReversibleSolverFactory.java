package inversionlab;

import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.Reversible;

public class ReversibleSolverFactory extends SolverFactory<Reversible>
{
  /**
   * Name of parameter "&alpha;".
   */
  public static final String ALPHA = "\u03b1";
  
  protected int m_maxTries = 1;
  
  public ReversibleSolverFactory(PreconditionFactory<Reversible> factory, int min_length, int max_length)
  {
    super(factory, min_length, max_length);
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
    ReversibleSolver solver = new ReversibleSolver(g);
    e.setSolver(solver);
    return true;
  }

}
