package inversionlab;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class GenerateAndTestSat extends GenerateAndTest
{
	protected boolean m_allTrue = false;
	
	public GenerateAndTestSat(Processor p, int min_length, int max_length,
			Picker<MathList<Object>> list_picker)
	{
		super(p, min_length, max_length, list_picker);
	}
	
	public GenerateAndTest setAllTrue(boolean b)
	{
		m_allTrue = b;
		return this;
	}
	
	@Override
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
		int len = 0;
		boolean all_true = true;
		boolean some_true = false;
		while (p.hasNext())
		{
			Object o = p.next();
			len++;
			if (Boolean.TRUE.equals(o))
			{
				some_true = true;
			}
			else
			{
				all_true = false;
			}
		}
		if (len < m_minLength || len > m_maxLength)
		{
		  return false; // Invalid because output outside of length range
		}
		if (m_allTrue)
		{
			return all_true;
		}
		return some_true;
	}

}
