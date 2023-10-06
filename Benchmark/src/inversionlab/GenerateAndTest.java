package inversionlab;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public abstract class GenerateAndTest
{
	/**
	 * The name of this generation strategy.
	 */
	public static final String NAME = "Generate and test";

	protected Picker<MathList<Object>> m_listPicker;

	protected Processor m_processor;

	protected final int m_minLength;

	protected final int m_maxLength;

	public GenerateAndTest(Processor p, int min_length, int max_length, Picker<MathList<Object>> list_picker)
	{
		super();
		m_processor = p;
		m_listPicker = list_picker;
		m_minLength = min_length;
		m_maxLength = max_length;
	}
	
	/**
	 * Runs a candidate input through the processor pipeline and collects its
	 * output.
	 * @param candidate The candidate input
	 * @return The output from the pipeline
	 */
	protected List<Object> runThrough(MathList<Object>[] candidate)
	{
		List<Object> out = new ArrayList<Object>();
		Processor proc = m_processor.duplicate();
		for (int i = 0; i < proc.getInputArity(); i++)
		{
			QueueSource s = new QueueSource();
			s.setEvents(candidate[i]);
			s.loop(false);
			Connector.connect(s, 0, proc, i);
		}
		Pullable p = proc.getPullableOutput();
		while (p.hasNext())
		{
			Object o = p.next();
			out.add(o);
		}
		return out;
	}
}
