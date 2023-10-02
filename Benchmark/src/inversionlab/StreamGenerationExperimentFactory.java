package inversionlab;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentFactory;
import ca.uqac.lif.labpal.region.Point;

import static inversionlab.StreamGenerationExperiment.METHOD;

public class StreamGenerationExperimentFactory extends ExperimentFactory<StreamGenerationExperiment>
{
	protected Map<String,PreconditionFactory> m_factories;
	
	protected int m_sizeLimit = -1;

	public StreamGenerationExperimentFactory(Laboratory lab)
	{
		super(lab);
		m_factories = new HashMap<String,PreconditionFactory>();
	}
	
	public StreamGenerationExperimentFactory setSizeLimit(int limit)
	{
		m_sizeLimit = limit;
		return this;
	}
	
	public StreamGenerationExperimentFactory add(String name, PreconditionFactory f)
	{
		m_factories.put(name, f);
		return this;
	}
	
	@Override
	/*@ pure null @*/ protected StreamGenerationExperiment createExperiment(Point p)
	{
		String method = p.getString(METHOD);
		if (!m_factories.containsKey(method))
		{
			return null;
		}
		PreconditionFactory factory = m_factories.get(method);
		StreamGenerationExperiment e = new StreamGenerationExperiment();
		if (!factory.set(p, e))
		{
			return null;
		}
		e.setSizeLimit(m_sizeLimit);
		return e;
	}

	@Override
	protected Constructor<? extends StreamGenerationExperiment> getPointConstructor(Point p)
	{
		// Not needed
		return null;
	}

	@Override
	protected Constructor<? extends StreamGenerationExperiment> getEmptyConstructor(Point p)
	{
		// Not needed
		return null;
	}

	@Override
	protected Class<? extends StreamGenerationExperiment> getClass(Point p)
	{
		// Not needed
		return null;
	}

}
