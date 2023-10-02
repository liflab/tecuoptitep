package inversionlab;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class RandomGenerator implements Iterator<AritalSuggestion>
{
	/**
	 * The name of this generation strategy.
	 */
	public static final String NAME = "Random";
	
	protected Set<AritalSuggestion> m_pastSuggestions;
	
	protected AritalSuggestion m_nextElement;
	
	protected Picker<MathList<Object>> m_listPicker;
	
	protected Processor m_processor;
	
	protected boolean m_allTrue = false;
	
	public RandomGenerator(Processor p, Picker<MathList<Object>> list_picker)
	{
		super();
		m_processor = p;
		m_pastSuggestions = new HashSet<AritalSuggestion>();
		m_listPicker = list_picker;
	}
	
	public RandomGenerator setAllTrue(boolean b)
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
		Processor proc = m_processor.duplicate();
		for (int i = 0; i < proc.getInputArity(); i++)
		{
			QueueSource s = new QueueSource();
			s.setEvents(inputs[i]);
			s.loop(false);
			Connector.connect(s, 0, proc, i);
		}
		Pullable p = proc.getPullableOutput();
		while (p.hasNext())
		{
			Object o = p.next();
			if (Boolean.TRUE.equals(o))
			{
				if (!m_allTrue)
				{
					return true;
				}
			}
			else
			{
				if (m_allTrue)
				{
					return false;
				}
			}
		}
		if (m_allTrue)
		{
			return true;
		}
		return false;
	}
	
}
