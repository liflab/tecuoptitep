package inversionlab;

import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;
import ca.uqac.lif.reversi.Reversible;
import ca.uqac.lif.reversi.util.AllTruePicker;
import ca.uqac.lif.synthia.random.RandomInteger;

public class ReversibleGeneratorFactory extends GeneratorFactory<Reversible>
{
  /**
   * Name of parameter "&alpha;".
   */
  public static final String ALPHA = "\u03b1";
  
  /**
   * Name of parameter "simultaneous outputs".
   */
  public static final String NUM_OUTPUTS = "Simultaneous outputs";
  
  protected int m_maxTries = 10;
  
  public ReversibleGeneratorFactory(PreconditionFactory<Reversible> factory, int min_length, int max_length)
  {
    super(factory, min_length, max_length);
  }

  @Override
  protected boolean instantiateGenerator(Point pt, Reversible precondition, GeneratorExperiment e)
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
    int num_outputs = 1;
    {
      Object o = pt.get(NUM_OUTPUTS);
      if (o == null)
      {
        return false;
      }
      num_outputs = ((Number) o).intValue();
    }
    e.describe(NUM_OUTPUTS, "The number of simultaneous outputs to invert");
    e.writeInput(NUM_OUTPUTS, num_outputs);
    e.writeInput(METHOD, ReversibleGenerator.NAME);
    Reversible g = m_factory.setCondition(pt, e);
    RandomInteger rint = new RandomInteger(m_minLength, m_maxLength).setSeed(getSeed() + 10);
    DistinctStreamSolver solver = new DistinctStreamSolver(g, new AllTruePicker(rint), m_maxTries, num_outputs);
    ReversibleGenerator gen = new ReversibleGenerator(solver);
    e.setGenerator(gen);
    return true;
  }

}
