/*
    Inversion of function circuits
    Copyright (C) 2023 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
	public static final String NAME = "Random";

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
	
	public static interface OutputCondition
	{
		public boolean isValid(List<? extends Object> output);
	}
}
