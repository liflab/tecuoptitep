package inversionlab;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.experiment.ExperimentException;
import ca.uqac.lif.labpal.util.Stopwatch;
import ca.uqac.lif.reversi.AritalSuggestion;

import static inversionlab.PreconditionFactory.PIPELINE;

public class StreamGenerationExperiment extends Experiment
{
	public static final String TIME = "Time";
	
	public static final String ELEMENTS = "Elements";
	
	public static final String METHOD = "Method";
	
	/**
	 * The maximum number of streams to generate before interrupting the experiment.
	 * A negative value indicates no limit.
	 */
	protected int m_sizeLimit = -1;
	
	/**
	 * An iterator over arrays of streams.
	 */
	protected Iterator<AritalSuggestion> m_generator;
	
	public StreamGenerationExperiment()
	{
		super();
		describe(TIME, "The time (in ms) since the start of the generation");
		describe(ELEMENTS, "The number of distinct valid input streams generated so far");
		describe(METHOD, "The method or tool used to generate input sequences");
		describe(PIPELINE, "The precondition to generate input sequences for");
		writeOutput(TIME, new ArrayList<Long>());
		writeOutput(ELEMENTS, new ArrayList<Integer>());
	}
	
	/**
	 * Sets the stream generator.
	 * @param g An iterator over suggestions. Each new value produced by
	 * the iterator must be distinct from all the others produced so far. The
	 * experiment assumes this without checking it.  
	 */
	public void setGenerator(Iterator<AritalSuggestion> g)
	{
		m_generator = g;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws ExperimentException
	{
		List<Long> l_time = (List<Long>) read(TIME);
		List<Integer> l_elements = (List<Integer>) read(ELEMENTS);
		l_time.add(0l);
		l_elements.add(0);
		Stopwatch.start(this);
		int elems = 0;
		while (m_generator.hasNext() && (m_sizeLimit < 0 || elems < m_sizeLimit))
		{
			m_generator.next();
			elems++;
			l_time.add(Stopwatch.lap(this));
			l_elements.add(elems);
		}
	}
}
