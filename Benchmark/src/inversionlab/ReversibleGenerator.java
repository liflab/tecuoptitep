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

import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;

public class ReversibleGenerator implements Generator
{
	/**
	 * The name of this generation strategy.
	 */
	public static final String NAME = "Inversion";
	
	protected DistinctStreamSolver m_solver;
	
	public ReversibleGenerator(DistinctStreamSolver solver)
	{
		super();
		m_solver = solver;
	}

	@Override
	public boolean hasNext()
	{
		return !m_solver.isDone();
	}

	@Override
	public AritalSuggestion next()
	{
		return m_solver.pick();
	}
}
