package inversionlab;

import java.lang.reflect.Constructor;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentFactory;
import ca.uqac.lif.labpal.region.Point;

import static inversionlab.StreamGenerationExperiment.METHOD;

public class StreamGenerationExperimentFactory extends ExperimentFactory<StreamGenerationExperiment>
{
	protected PreconditionFactory m_pipelineFactory;
	
	protected PreconditionFactory m_inversionFactory;

	public StreamGenerationExperimentFactory(Laboratory lab, PreconditionFactory pipeline_factory, PreconditionFactory inversion_factory)
	{
		super(lab);
		m_pipelineFactory = pipeline_factory;
	}
	
	@Override
	/*@ pure null @*/ protected StreamGenerationExperiment createExperiment(Point p)
	{
		String method = p.getString(METHOD);
		PreconditionFactory factory = null;
		switch (method)
		{
		case InversionGenerator.NAME:
			factory = m_inversionFactory;
			break;
		case RandomGenerator.NAME:
			factory = m_pipelineFactory;
			break;
		default:
				factory = null;
				break;
		}
		if (method == null)
		{
			return null;
		}
		StreamGenerationExperiment e = new StreamGenerationExperiment();
		if 
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
