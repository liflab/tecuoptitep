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

import ca.uqac.lif.labpal.region.Point;

public abstract class SolverFactory<T> extends InputOutputFactory<T>
{
	public SolverFactory(PreconditionFactory<T> factory, int min_length, int max_length)
	{
		super(factory, min_length, max_length);
	}
	
	public boolean setSolver(Point pt, SolverExperiment e)
  {
    T precondition = m_factory.setCondition(pt, e);
    if (precondition == null)
    {
      return false;
    }
    e.describe(MIN_LENGTH, "The minimum length of the streams to generate");
    e.writeInput(MIN_LENGTH, m_minLength);
    e.describe(MAX_LENGTH, "The maximum length of the streams to generate");
    e.writeInput(MAX_LENGTH, m_maxLength);
    return instantiateSolver(pt, precondition, e);    
  }
	
  @Override
  public SolverFactory<T> setSeed(int seed)
  {
    super.setSeed(seed);
    return this;
  }
  
  protected abstract boolean instantiateSolver(Point pt, T precondition, SolverExperiment e);

}
