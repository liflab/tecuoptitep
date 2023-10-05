package inversionlab;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.region.Point;

public class CandidateGenerationExperimentFactory extends GenerationExperimentFactory<CandidateGenerationExperiment>
{	
	protected int m_sizeLimit = -1;

	public CandidateGenerationExperimentFactory(Laboratory lab)
	{
		super(lab);
		m_factories = new HashMap<String,GeneratorFactory>();
	}
	
	public CandidateGenerationExperimentFactory setSizeLimit(int limit)
	{
		m_sizeLimit = limit;
		return this;
	}
	
	@Override
	/*@ pure null @*/ protected CandidateGenerationExperiment createExperiment(Point p)
	{
		CandidateGenerationExperiment e = super.createExperiment(p);
		e.setSizeLimit(m_sizeLimit);
		return e;
	}

	@Override
	protected Constructor<? extends CandidateGenerationExperiment> getPointConstructor(Point p)
	{
		// Not needed
		return null;
	}

	@Override
	protected Constructor<? extends CandidateGenerationExperiment> getEmptyConstructor(Point p)
	{
		// Not needed
		return null;
	}

	@Override
	protected Class<? extends CandidateGenerationExperiment> getClass(Point p)
	{
		// Not needed
		return null;
	}

	@Override
	protected CandidateGenerationExperiment getExperimentInstance()
	{
		return new CandidateGenerationExperiment();
	}
}
