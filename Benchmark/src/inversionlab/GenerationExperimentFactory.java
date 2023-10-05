package inversionlab;

import static inversionlab.GenerationExperiment.METHOD;

import java.util.HashMap;
import java.util.Map;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentFactory;
import ca.uqac.lif.labpal.region.Point;

public abstract class GenerationExperimentFactory<T extends GenerationExperiment> extends ExperimentFactory<T>
{
	protected Map<String,GeneratorFactory> m_factories;
	
	public GenerationExperimentFactory(Laboratory lab)
	{
		super(lab);
		m_factories = new HashMap<String,GeneratorFactory>();
	}
	
	public GenerationExperimentFactory<T> add(String name, GeneratorFactory f)
	{
		m_factories.put(name, f);
		return this;
	}
	
	@Override
	/*@ pure null @*/ protected T createExperiment(Point p)
	{
		String method = p.getString(METHOD);
		if (!m_factories.containsKey(method))
		{
			return null;
		}
		GeneratorFactory factory = m_factories.get(method);
		T e = getExperimentInstance();
		if (!factory.set(p, e))
		{
			return null;
		}
		return e;
	}
	
	protected abstract T getExperimentInstance();
}
