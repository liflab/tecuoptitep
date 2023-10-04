package inversionlab;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public abstract class GenerateAndTest implements Iterator<AritalSuggestion>
{
	/**
	 * The name of this generation strategy.
	 */
	public static final String NAME = "Random";

	protected Set<AritalSuggestion> m_pastSuggestions;

	protected AritalSuggestion m_nextElement;

	protected Picker<MathList<Object>> m_listPicker;

	protected Processor m_processor;

	protected final int m_minLength;

	protected final int m_maxLength;

	public GenerateAndTest(Processor p, int min_length, int max_length, Picker<MathList<Object>> list_picker)
	{
		super();
		m_processor = p;
		m_pastSuggestions = new HashSet<AritalSuggestion>();
		m_listPicker = list_picker;
		m_minLength = min_length;
		m_maxLength = max_length;
	}

	@Override
	public boolean hasNext()
	{
		if (m_nextElement == null)
		{
			getNextSuggestion();
		}
		return m_nextElement != null;
	}

	@Override
	public AritalSuggestion next()
	{
		if (m_nextElement == null)
		{
			getNextSuggestion();
		}
		if (m_nextElement == null)
		{
			throw new NoSuchElementException("No more element to enumerate");
		}
		AritalSuggestion a = m_nextElement;
		m_nextElement = null;
		return a;
	}

	@SuppressWarnings("unchecked")
	protected void getNextSuggestion()
	{
		MathList<Object>[] candidate = new MathList[m_processor.getInputArity()];
		AritalSuggestion sugg = null;
		do
		{
			sugg = null;
			for (int i = 0; i < candidate.length; i++)
			{
				candidate[i] = m_listPicker.pick();
				//System.out.println("Candidate: " + candidate[i]);
			}
			if (!isValidSuggestion(candidate))
			{
				//System.out.println("Invalid");
				continue;
			}
			sugg = new AritalSuggestion(candidate.length);
			for (int i = 0; i < candidate.length; i++)
			{
				sugg.set(i, candidate[i]);
			}
		} while (sugg == null || m_pastSuggestions.contains(sugg));
		m_pastSuggestions.add(sugg);
		m_nextElement = sugg;
	}

	protected abstract boolean isValidSuggestion(MathList<Object>[] candidate);

}