/*
    Inversion of BeepBeep processor chains
    Copyright (C) 2023-2024 Laboratoire d'informatique formelle
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
package ca.uqac.lif.reversi.assertions;

import java.util.List;
import java.util.Queue;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.tmf.QueueSink;

public class PeebPeebAssertions
{
	public static void assertHolds(Processor P, Processor phi, Processor Q, List<?> ... inputs) throws AssertionError
	{
		QueueSink p_sink = new QueueSink();
		Connector.connect(P, p_sink);
		boolean Q_depends_input = false;
		if (Q.getInputArity() == phi.getOutputArity())
		{
			Connector.connect(phi, Q); // Q only depends on the output(s) of phi
		}
		else if (Q.getInputArity() == phi.getOutputArity() + P.getInputArity())
		{
			// Q depends on inputs and outputs
			Q_depends_input = true;
			for (int i = P.getInputArity(); i < P.getInputArity() + phi.getOutputArity(); i++)
			{
				Connector.connect(phi, i - P.getInputArity(), Q, i);
			}
		}
		QueueSink q_sink = new QueueSink();
		Connector.connect(Q, q_sink);
		int max_len = 0;
		for (List<?> list : inputs)
		{
			max_len = Math.max(list.size(), max_len);
		}
		Queue<?> p_p = p_sink.getQueue();
		Queue<?> p_q = q_sink.getQueue();
		boolean p_true = false, q_true = false;
		for (int i = 0; i < max_len; i++)
		{
			for (int j = 0; j < inputs.length; j++)
			{
				List<?> in_list = inputs[j];
				if (i < in_list.size())
				{
					Object e = in_list.get(i);
					P.getPushableInput(j).push(e);
					if (Q_depends_input)
					{
						Q.getPushableInput(j).push(e);
					}
					phi.getPushableInput(j).push(e);
				}
				while (!p_p.isEmpty() && !p_q.isEmpty())
				{
					Boolean p_value = (Boolean) p_p.remove();
					p_true = p_value || p_true;
				}
				while (!p_q.isEmpty())
				{
					Boolean q_value = (Boolean) p_q.remove();
					q_true = q_value || q_true;
				}
				if (q_true && !p_true)
				{
					throw new AssertionError("Post-condition satisfied before pre-condition");
				}
			}
		}
		if (!p_true)
		{
			throw new AssertionError("Test is vacuously true");
		}
	}
}
