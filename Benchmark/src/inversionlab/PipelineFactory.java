package inversionlab;

import ca.uqac.lif.labpal.region.Point;

public class PipelineFactory extends PreconditionFactory
{
	@Override
	public boolean set(Point pt, StreamGenerationExperiment e)
	{
		String name = pt.getString(PIPELINE);
		switch (name)
		{
		case TWO_EQUAL_DECIMATE:
			e.writeInput(PIPELINE, TWO_EQUAL_DECIMATE);
			return setTwoEqualDecimate(pt, e);
		}
		return false;
	}
	
	protected static boolean setTwoEqualDecimate(Point pt, StreamGenerationExperiment e)
	{
		return true;
	}
}
