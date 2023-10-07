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
import java.util.List;
import java.util.Map;

import ca.uqac.lif.dag.Node;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

public abstract class ReversibleFunction extends Node implements Reversible
{
  protected List<Suggestion> m_targetOutput;
  
  protected final Map<Integer,List<Suggestion>> m_suggestedInputs;
  
  protected Picker<Boolean> m_coin;
  
  public ReversibleFunction(int in_arity, Picker<Boolean> coin)
  {
    super(in_arity, 1);
    m_targetOutput = null;
    m_suggestedInputs = new HashMap<Integer,List<Suggestion>>();
    m_coin = coin;
  }
  
  public ReversibleFunction(int in_arity)
  {
  	this(in_arity, new Constant<Boolean>(true));
  }
  
  @Override
  public void reset()
  {
    m_targetOutput = null;
    m_suggestedInputs.clear();
  }
  
  @Override
	public List<Suggestion> getSuggestions(int in_index)
	{
  	if (!m_suggestedInputs.containsKey(in_index))
  	{
  		getSuggestions();
  	}
  	return m_suggestedInputs.get(in_index);
	}
  
  @Override
  public void setTargetOutputs(int out_index, List<Suggestion> suggestions)
  {
    m_targetOutput = suggestions;
  }
  
  @Override
  public List<Suggestion> getTargetOutputs(int out_index)
  {
    return m_targetOutput;
  }
  
  protected abstract void getSuggestions();
  
  @Override
  public String toString()
  {
  	return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
  }
}
