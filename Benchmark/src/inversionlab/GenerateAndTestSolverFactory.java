package inversionlab;

import static inversionlab.PreconditionFactory.ALPHABET_SIZE;

import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.Choice;

public class GenerateAndTestSolverFactory extends SolverFactory<GroupProcessor>
{
  protected final int m_maxTries;
  
  public GenerateAndTestSolverFactory(PreconditionFactory<GroupProcessor> factory, int min_length, int max_length, int max_tries)
  {
    super(factory, min_length, max_length);
    m_maxTries = max_tries;
  }

  @Override
  protected boolean instantiateSolver(Point pt, GroupProcessor precondition, SolverExperiment e)
  {
    int alphabet_size = -1;
    {
      Object o = pt.get(ALPHABET_SIZE);
      if (o == null)
      {
        return false;
      }
      alphabet_size = ((Number) o).intValue();
    }
    GroupProcessor g = m_factory.setCondition(pt, e);
    GenerateAndTestSolver gen = new GenerateAndTestSolver(g, m_minLength, m_maxLength, getListPicker(alphabet_size), m_maxTries);
    e.setSolver(gen);
    return true;
  }
  
  protected RandomListPicker getListPicker(int alphabet_size)
  {
    Choice<Object> symbol = new Choice<>(new RandomFloat().setSeed(getSeed()));
    float prob = 1f / alphabet_size;
    for (Object o : PreconditionFactory.getStringAlphabet(alphabet_size))
    {
      symbol.add(o, prob);
    }
    RandomInteger rint = new RandomInteger(0, 1000).setSeed(getSeed() + 10);
    RandomListPicker rpl = new RandomListPicker(rint, symbol);
    return rpl;
  }

}
