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

import ca.uqac.lif.reversi.Reversible;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

/**
 * An object containing a reversible function and providing a picker to
 * generate outputs satisfying a condition.
 */
public class ReversibleCondition
{
	protected final Reversible m_group;
	
	public ReversibleCondition(Reversible group)
	{
		super();
		m_group = group;
	}
	
	public Reversible getReversible()
	{
		return m_group;
	}
	
	public Picker<MathList<Object>> getPicker()
	{
		return null;
	}
}
