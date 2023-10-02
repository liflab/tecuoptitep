package inversionlab;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.json.JsonList;
import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.experiment.ExperimentException;
import ca.uqac.lif.labpal.util.Stopwatch;
import ca.uqac.lif.reversi.AritalSuggestion;

import static inversionlab.PreconditionFactory.ALPHABET_SIZE;
import static inversionlab.PreconditionFactory.CONDITION;

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
	
	protected Set<AritalSuggestion> m_suggestions;
	
	public StreamGenerationExperiment()
	{
		super();
		m_suggestions = new HashSet<AritalSuggestion>();
		describe(TIME, "The time (in ms) since the start of the generation");
		describe(ELEMENTS, "The number of distinct valid input streams generated so far");
		describe(METHOD, "The method or tool used to generate input sequences");
		describe(CONDITION, "The precondition to generate input sequences for");
		describe(ALPHABET_SIZE, "The number of possible distinct events found in a stream");
		writeOutput(TIME, new JsonList());
		writeOutput(ELEMENTS, new JsonList());
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
	
	/**
	 * Sets the maximum number of streams to generate before interrupting the
	 * experiment.
	 * @param limit The number; a negative value indicates no limit
	 */
	public void setSizeLimit(int limit)
	{
		m_sizeLimit = limit;
	}
	
	@Override
	public void execute() throws ExperimentException
	{
		JsonList l_time = (JsonList) read(TIME);
		JsonList l_elements = (JsonList) read(ELEMENTS);
		l_time.add(0l);
		l_elements.add(0);
		Stopwatch.start(this);
		int elems = 0;
		while (m_generator.hasNext() && (m_sizeLimit < 0 || elems < m_sizeLimit))
		{
			m_suggestions.add(m_generator.next());
			elems++;
			l_time.add(Stopwatch.lap(this));
			l_elements.add(elems);
			if (m_sizeLimit > 0)
			{
				setProgression((float) elems / (float) m_sizeLimit);
			}
		}
		System.out.println("Done");
	}
	
	@Override
	public String getDescription()
	{
		StringBuilder out = new StringBuilder();
		out.append("<p>Generates a set of input streams satisfying a given precondition.</p>\n");
		if (!m_suggestions.isEmpty())
		{
			out.append("<p>Generated suggestions:</p>\n");
			out.append("<ol>\n");
			for (AritalSuggestion s : m_suggestions)
			{
				out.append("<li>").append(s).append("</li>\n");
			}
			out.append("</ol>\n");
		}
		return out.toString();
	}
}
