package inversionlab;

import static inversionlab.InputOutputFactory.METHOD;
import static inversionlab.StreamExperiment.SIZE_LIMIT;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentFactory;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.reversi.util.EndsInPicker;
import ca.uqac.lif.synthia.random.RandomInteger;

public class SolverExperimentFactory extends ExperimentFactory<SolverExperiment>
{
	protected Map<String,SolverFactory<?>> m_factories;
	
	public SolverExperimentFactory(Laboratory lab)
	{
		super(lab);
		m_factories = new HashMap<String,SolverFactory<?>>();
	}
	
	public SolverExperimentFactory add(String name, SolverFactory<?> f)
  {
    m_factories.put(name, f);
    return this;
  }
	
	@Override
  public SolverExperiment createExperiment(Point pt)
  {
    SolverExperiment e = new SolverExperiment();
    if (!set(pt, e))
    {
      return null;
    }
    return e;
  }
	
	public boolean set(Point pt, SolverExperiment e)
  {
    String name = pt.getString(METHOD);
    e.describe(METHOD, "The method used to generate input streams");
    e.writeInput(METHOD, name);
    SolverFactory<?> factory = m_factories.get(name);
    if (!factory.setSolver(pt, e))
    {
      return false;
    }
    RandomInteger rint = new RandomInteger(factory.m_minLength, factory.m_maxLength).setSeed(factory.getSeed());
    EndsInPicker out_picker = new EndsInPicker(rint);
    e.setOutputPicker(new OutputPicker(1, out_picker));
    e.writeInput(SIZE_LIMIT, 10);
    return true;
  }

	@Override
	protected Class<? extends SolverExperiment> getClass(Point arg0)
	{
		// Not needed
		return null;
	}

	@Override
	protected Constructor<? extends SolverExperiment> getEmptyConstructor(Point arg0)
	{
		// Not needed
		return null;
	}

	@Override
	protected Constructor<? extends SolverExperiment> getPointConstructor(Point arg0)
	{
		// Not needed
		return null;
	}
}
