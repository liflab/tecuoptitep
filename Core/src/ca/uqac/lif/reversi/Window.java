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
package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.sequence.Playback;

/**
 * The inversion of the sliding window produces a correct result under the
 * condition that the inner function outputs a stream of exactly <em>one</em>
 * event when given an input of <i>k</i> events.
 */
public class Window extends ReversibleFunction
{
	protected final int m_width;

	protected final Reversible m_phi;

	public Window(int width, Reversible phi)
	{
		super(1);
		m_width = width;
		m_phi = phi;
	}

	@Override
	protected void getSuggestions()
	{
		List<Suggestion> input_suggestions = new ArrayList<Suggestion>();
		int sug_cnt = 0;
		for (Suggestion sug : m_targetOutput)
		{
			List<?> out_stream = (List<?>) sug.getValue();
			int out_len = out_stream.size();
			List<EventNode> current = new ArrayList<EventNode>();
			EventNode root = new EventNode(null, null);
			current.add(root);
			for (int j = 0; j < out_len; j++)
			{
				List<EventNode> new_current = new ArrayList<EventNode>();
				m_phi.reset();
				m_phi.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(out_stream.get(j)))));
				/*if (j > 0 && m_phi instanceof MonteCarloReversible)
				{
					((MonteCarloReversible) m_phi).setCoin(new Playback<Boolean>(true));
				}*/
				List<Suggestion> in_sugs = m_phi.getSuggestions(0);
				for (Suggestion in_sug: in_sugs)
				{
					List<?> in_stream = (List<?>) in_sug.getValue();
					if (in_stream.size() != m_width)
					{
						// We push exactly k events; an input solution with a different size is impossible
						continue;
					}
					if (j == 0)
					{
						// First event, just append sequence to root
						appendToRoot(root, in_stream, 0, new_current);
					}
					else
					{
						Object last = in_stream.get(m_width - 1);
						for (EventNode n : current)
						{
							if (isValidExtension(n, in_stream, m_width - 1))
							{
								EventNode child = new EventNode(last, n);
								n.m_children.add(child);
								new_current.add(child);
							}
						}
					}
				}
				if (new_current.isEmpty())
				{
					root = null;
					break;
				}
				current = new_current;
			}
			if (root != null)
			{
				List<MathList<Object>> in_sols = new ArrayList<MathList<Object>>();
				expandTree(root, in_sols, new MathList<Object>());
				for (MathList<Object> in_sol : in_sols)
				{
					Suggestion in_sug = new Suggestion(in_sol);
					in_sug.addLineage(sug.getLineage());
					in_sug.addLineage(new Association(this, sug_cnt));
					input_suggestions.add(in_sug);
					sug_cnt++;
				}
			}
		}
		m_suggestedInputs.put(0, input_suggestions);
	}

	protected static boolean isValidExtension(EventNode n, List<?> stream, int depth)
	{
		if (depth == 0)
		{
			return true;
		}
		Object o1 = stream.get(depth - 1);
		Object o2 = n.m_symbol;
		if (!o1.equals(o2))
		{
			return false;
		}
		return isValidExtension(n.m_parent, stream, depth - 1);
	}

	protected static void expandTree(EventNode n, List<MathList<Object>> to, MathList<Object> current)
	{
		if (n.m_children.isEmpty())
		{
			to.add(current);
			return;
		}
		for (EventNode child : n.m_children)
		{
			MathList<Object> new_list = new MathList<Object>();
			new_list.addAll(current);
			new_list.add(child.m_symbol);
			expandTree(child, to, new_list);
		}
	}

	protected static void appendToRoot(EventNode n, List<?> stream, int index, List<EventNode> to)
	{
		Object o = stream.get(index);
		if (index == stream.size() - 1)
		{
			EventNode new_child = new EventNode(o, n);
			n.m_children.add(new_child);
			to.add(new_child);
			return;
		}
		for (EventNode child : n.m_children)
		{
			if (child.m_symbol.equals(o))
			{
				appendToRoot(child, stream, index + 1, to);
				return;
			}
		}
		EventNode new_child = new EventNode(o, n);
		n.m_children.add(new_child);
		if (index == stream.size() - 1)
		{
			to.add(new_child);
		}
		else
		{
			appendToRoot(new_child, stream, index + 1, to);
		}
	}

	protected static class EventNode
	{
		protected final Object m_symbol;

		protected final EventNode m_parent;

		protected final List<EventNode> m_children;

		public EventNode(Object symbol, EventNode parent)
		{
			super();
			m_symbol = symbol;
			m_parent = parent;
			m_children = new ArrayList<EventNode>();
		}

		@Override
		public String toString()
		{
			if (m_symbol == null)
			{
				return "";
			}
			return m_parent.toString() + "<-" + m_symbol.toString();
		}
	}
}
