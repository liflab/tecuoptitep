package inversionlab;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import static inversionlab.GeneratorFactory.METHOD;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentFactory;
import ca.uqac.lif.labpal.region.Point;

/**
 * An object that provides instances of a precondition based on a name
 * contained in a {@link Point}.
 */
public class GeneratorExperimentFactory extends ExperimentFactory<GeneratorExperiment>
{ 
  protected Map<String,GeneratorFactory<?>> m_factories;
  
  protected int m_sizeLimit = 10;

  public GeneratorExperimentFactory(Laboratory lab)
  {
    super(lab);
    m_factories = new HashMap<String,GeneratorFactory<?>>();
  }

  public GeneratorExperimentFactory add(String name, GeneratorFactory<?> f)
  {
    m_factories.put(name, f);
    return this;
  }
  
  public GeneratorExperimentFactory setSizeLimit(int size)
  {
    m_sizeLimit = size;
    return this;
  }
  
  @Override
  public GeneratorExperiment createExperiment(Point pt)
  {
    GeneratorExperiment e = new GeneratorExperiment();
    if (!set(pt, e))
    {
      return null;
    }
    return e;
  }

  public boolean set(Point pt, GeneratorExperiment e)
  {
    String name = pt.getString(METHOD);
    e.describe(METHOD, "The method used to generate input streams");
    e.writeInput(METHOD, name);
    GeneratorFactory<?> factory = m_factories.get(name);
    if (!factory.setGenerator(pt, e))
    {
      return false;
    }
    e.setSizeLimit(m_sizeLimit);
    return true;
  }

  @Override
  protected Class<? extends GeneratorExperiment> getClass(Point arg0)
  {
    // Not needed
    return null;
  }

  @Override
  protected Constructor<? extends GeneratorExperiment> getEmptyConstructor(Point arg0)
  {
    // Not needed
    return null;
  }

  @Override
  protected Constructor<? extends GeneratorExperiment> getPointConstructor(Point arg0)
  {
    // Not needed
    return null;
  }
}
