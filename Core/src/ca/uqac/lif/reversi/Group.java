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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.uqac.lif.dag.NestedNode;
import ca.uqac.lif.dag.Node;
import ca.uqac.lif.dag.Pin;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

public class Group extends NestedNode implements MonteCarloReversible
{
	protected final Map<Integer,List<Suggestion>> m_targetInputs;
	
	protected final Map<Integer,List<Suggestion>> m_targetOutputs;
	
	protected final Picker<Boolean> m_originalCoin;
	
	protected Picker<Boolean> m_coin;

	public Group(int in_arity, int out_arity, Picker<Boolean> coin)
	{
		super(in_arity, out_arity);
		m_targetInputs = new HashMap<Integer,List<Suggestion>>(in_arity);
		m_targetOutputs = new HashMap<Integer,List<Suggestion>>(out_arity);
		m_originalCoin = coin.duplicate(false);
		m_coin = coin;
	}
	
	public Group(int in_arity, int out_arity)
	{
		this(in_arity, out_arity, new Constant<Boolean>(true));
	}
	
	@Override
	public void setCoin(Picker<Boolean> coin)
	{
		m_coin = coin;
		for (Node n : m_internalNodes)
	  {
			if (n instanceof MonteCarloReversible)
			{
				((MonteCarloReversible) n).setCoin(coin);
			}
	  }
	}
	
	@Override
	public void reset()
	{
	  m_targetInputs.clear();
	  m_targetOutputs.clear();
	  //m_coin = m_originalCoin.duplicate(false);
	  for (Node n : m_internalNodes)
	  {
	    if (n instanceof Reversible)
	    {
	      ((Reversible) n).reset();
	    }
	  }
	}

	@Override
	public void setTargetOutputs(int out_arity, List<Suggestion> suggestions)
	{
		m_targetOutputs.put(out_arity, suggestions);
		Pin<? extends Node> pin = m_outputAssociations.get(out_arity);
		Node n = pin.getNode();
		if (n instanceof Reversible)
		{
			((Reversible) n).setTargetOutputs(pin.getIndex(), suggestions);
		}
	}

	@Override
	public List<Suggestion> getSuggestions(int in_arity)
	{
		if (!m_targetInputs.containsKey(in_arity))
		{
			getSuggestions();	
		}
		return m_targetInputs.get(in_arity);
	}

	protected void getSuggestions()
	{
		Set<Node> processed_nodes = new HashSet<Node>();
		for (int i = 0; i < getOutputArity(); i++)
		{
			Pin<? extends Node> pin = m_outputAssociations.get(i);
			Node n = pin.getNode();
			getSuggestions(n, processed_nodes);
		}
		for (int i = 0; i < getInputArity(); i++)
		{
			Pin<? extends Node> pin = m_inputAssociations.get(i);
			Node n = pin.getNode();
			if (n instanceof Reversible)
			{
				m_targetInputs.put(i, ((Reversible) n).getSuggestions(pin.getIndex()));
			}
		}
		// We can free this memory once the inputs have been calculated
		m_targetOutputs.clear();
	}

	protected void getSuggestions(Node n, Set<Node> processed_nodes)
	{
		if (n == null || processed_nodes.contains(n))
		{
			return;
		}
		if (!(n instanceof Reversible))
		{
			return;
		}
		for (int i = 0; i < n.getOutputArity(); i++)
		{
			if (!processed_nodes.contains(n))
			{
				Pin<? extends Node> pin = getDownstream(n, i);
				if (pin != null)
				{
					getSuggestions(pin.getNode(), processed_nodes);
				}
			}
		}
		for (int i = 0; i < n.getInputArity(); i++)
		{
			// Must be done in two phases: first set target outputs to upstream...
			List<Suggestion> sug = ((Reversible) n).getSuggestions(i);
			Pin<? extends Node> pin = getUpstream(n, i);
			if (pin != null)
			{
				Node upstream = pin.getNode();
				if (upstream instanceof Reversible)
				{
					((Reversible) upstream).setTargetOutputs(pin.getIndex(), sug);
				}
			}
			else
			{
				//int in_index = getGroupInputPin(n);
				//m_targetInputs.put(in_index, sug);
			}
		}
		processed_nodes.add(n);
		for (int i = 0; i < n.getInputArity(); i++)
		{
			// ...then recurse on each upstream node
			Pin<? extends Node> pin = getUpstream(n, i);
			if (pin != null)
			{
				Node upstream = pin.getNode();
				getSuggestions(upstream, processed_nodes);
			}
		}
	}

	//@Override
	public List<Suggestion> getTargetOutputs(int out_index)
	{
		return m_targetOutputs.get(out_index);
	}

	protected Pin<? extends Node> getUpstream(Node n, int in_arity)
	{
		for (Pin<? extends Node> p : n.getInputLinks(in_arity))
		{
			return p;
		}
		return null;
	}

	protected Pin<? extends Node> getDownstream(Node n, int in_arity)
	{
		for (Pin<? extends Node> p : n.getOutputLinks(in_arity))
		{
			return p;
		}
		return null;
	}
}
