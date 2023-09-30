package inversionlab;

import ca.uqac.lif.labpal.region.Point;

public abstract class PreconditionFactory
{
	public static final String PIPELINE = "Pipeline";
	
	public static final String TWO_EQUAL_DECIMATE = "Two equal (decimate)";
	
	public abstract boolean set(Point pt, StreamGenerationExperiment e);
}
