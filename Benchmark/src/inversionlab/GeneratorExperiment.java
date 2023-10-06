package inversionlab;

import java.util.HashSet;
import java.util.Set;

import ca.uqac.lif.json.JsonList;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.json.JsonNumber;
import ca.uqac.lif.labpal.experiment.ExperimentException;
import ca.uqac.lif.labpal.util.Stopwatch;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;

/**
 * An experiment whose task is to generate a fixed number <i>n</i> of input
 * streams satisfying a precondition, and to count the time it takes to
 * reach this number.
 */
public class GeneratorExperiment extends StreamExperiment
{
	/**
	 * The name of parameter "elements".
	 */
	public static final String ELEMENTS = "Elements";
	
	/**
	 * The maximum number of streams to generate before interrupting the experiment.
	 * A negative value indicates no limit.
	 */
	protected int m_sizeLimit = -1;
	
	/**
	 * An iterator over arrays of streams.
	 */
	protected Generator m_generator = null;
	
	/**
	 * The set of suggestions generated by this experiment.
	 */
	protected final Set<AritalSuggestion> m_suggestions;
	
	/**
	 * Creates a new experiment instance and populates the description of some of
	 * its parameters.
	 */
	public GeneratorExperiment()
	{
		super();
		m_suggestions = new HashSet<AritalSuggestion>();
		describe(TIME, "The time (in ms) since the start of the generation");
		describe(ELEMENTS, "The number of distinct valid input streams generated so far");
		describe(SIZE_LIMIT, "The number of streams to generate in each experiment");
		describe(LENGTH_DISTRIBUTION, "The number of input suggestions of a given length");
		writeOutput(TIME, new JsonList());
		writeOutput(ELEMENTS, new JsonList());
		writeOutput(LENGTH_DISTRIBUTION, new JsonMap());
	}
	
	/**
	 * Sets the maximum number of streams to generate before interrupting the
	 * experiment.
	 * @param limit The number; a negative value indicates no limit
	 */
	public void setSizeLimit(int limit)
	{
		m_sizeLimit = limit;
		writeInput(SIZE_LIMIT, limit);
	}
	
	/**
	 * Sets the stream generator.
	 * @param g An generator of suggestions. Each new value produced by
	 * the generator must be distinct from all the others produced so far. The
	 * experiment assumes this without checking it.
	 */
	public void setGenerator(Generator g)
	{
		m_generator = g;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws ExperimentException
	{
		JsonList l_time = (JsonList) read(TIME);
		JsonList l_elements = (JsonList) read(ELEMENTS);
		l_time.add(0l);
		l_elements.add(0);
		JsonMap distribution = (JsonMap) read(LENGTH_DISTRIBUTION);
		Stopwatch.start(this);
		int elems = 0;
		while (m_generator.hasNext() && (m_sizeLimit < 0 || elems < m_sizeLimit))
		{
			AritalSuggestion sug = m_generator.next();
			m_suggestions.add(sug);
			elems++;
			l_time.add(Stopwatch.lap(this));
			l_elements.add(elems);
			if (m_sizeLimit > 0)
			{
				setProgression((float) elems / (float) m_sizeLimit);
			}
			MathList<Object> len0 = (MathList<Object>) sug.get(0);
			int size = len0.size();
			int cnt = 1;
			String s_size = Integer.toString(size);
			if (distribution.containsKey(s_size))
			{
			  cnt = ((JsonNumber) distribution.get(s_size)).numberValue().intValue() + 1;
			}
			distribution.put(s_size, new JsonNumber(cnt));
		}
	}
	
	@Override
	public String getDescription()
	{
		StringBuilder out = new StringBuilder();
		out.append("<p>An experiment whose task is to generate a fixed number <i>n</i> of input\n"
				+ " streams satisfying a precondition, and to count the time it takes to\n"
				+ " reach this number.</p>\n");
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
