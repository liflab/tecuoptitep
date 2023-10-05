package inversionlab;

import static inversionlab.PreconditionFactory.ALPHABET_SIZE;

import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.Choice;

public class GenerateAndTestFactory extends GeneratorFactory<GroupProcessor>
{

  public GenerateAndTestFactory(PreconditionFactory<GroupProcessor> factory, int min_length, int max_length)
  {
    super(factory, min_length, max_length);
  }

  @Override
  protected boolean instantiateGenerator(Point pt, GroupProcessor precondition, GeneratorExperiment e)
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
    GenerateAndTest gen = new GenerateAndTestSat(g, m_minLength, m_maxLength, getListPicker(alphabet_size));
    e.setGenerator(gen);
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
