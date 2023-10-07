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

public class ReversibleSolverFactory extends SolverFactory<ReversibleCondition>
{
  /**
   * Name of parameter "&alpha;".
   */
  public static final String ALPHA = "\u03b1";
  
  protected final int m_maxTries;
  
  public ReversibleSolverFactory(PreconditionFactory<ReversibleCondition> factory, int min_length, int max_length, int max_tries)
  {
    super(factory, min_length, max_length);
    m_maxTries = max_tries;
  }

  @Override
  protected boolean instantiateSolver(Point pt, ReversibleCondition precondition, SolverExperiment e)
  {
    float alpha = -1;
    {
      Object o = pt.get(ALPHA);
      if (o == null)
      {
        return false;
      }
      alpha = ((Number) o).floatValue();
    }
    e.describe(ALPHA, "The probability given to the biased coin toss");
    e.writeInput(ALPHA, alpha);
    e.writeInput(METHOD, ReversibleSolver.NAME);
    ReversibleCondition g = m_factory.setCondition(pt, e);
    ReversibleSolver solver = new ReversibleSolver(g.getReversible(), m_maxTries);
    e.setSolver(solver);
    return true;
  }

}
