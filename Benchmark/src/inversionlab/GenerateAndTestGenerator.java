package inversionlab;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class GenerateAndTestGenerator extends GenerateAndTest implements Generator
{
	protected Set<AritalSuggestion> m_pastSuggestions;

	protected AritalSuggestion m_nextElement;
	
	protected boolean m_allTrue = false;
	
	public GenerateAndTestGenerator(Processor p, int min_length, int max_length,
			Picker<MathList<Object>> list_picker)
	{
		super(p, min_length, max_length, list_picker);
		m_pastSuggestions = new HashSet<AritalSuggestion>();
	}
	
	public GenerateAndTest setAllTrue(boolean b)
	{
		m_allTrue = b;
		return this;
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
	
	public boolean isValidSuggestion(MathList<Object>[] inputs)
	{
		List<Object> sol = runThrough(inputs);
		if (sol.size() < m_minLength || sol.size() > m_maxLength)
		{
			return false;
		}
		if (m_allTrue)
		{
			return !sol.contains(Boolean.FALSE);
		}
		return sol.contains(Boolean.TRUE);
	}

}
