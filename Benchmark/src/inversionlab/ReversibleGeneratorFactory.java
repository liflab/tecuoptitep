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
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;
import ca.uqac.lif.reversi.Reversible;
import ca.uqac.lif.reversi.util.AllTruePicker;
import ca.uqac.lif.synthia.random.RandomInteger;

public class ReversibleGeneratorFactory extends GeneratorFactory<Reversible>
{
  /**
   * Name of parameter "&alpha;".
   */
  public static final String ALPHA = "\u03b1";
  
  /**
   * Name of parameter "simultaneous outputs".
   */
  public static final String NUM_OUTPUTS = "Simultaneous outputs";
  
  protected final int m_maxTries;
  
  public ReversibleGeneratorFactory(PreconditionFactory<Reversible> factory, int min_length, int max_length, int max_tries)
  {
    super(factory, min_length, max_length);
    m_maxTries = max_tries;
  }

  @Override
  protected boolean instantiateGenerator(Point pt, Reversible precondition, GeneratorExperiment e)
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
    int num_outputs = 1;
    {
      Object o = pt.get(NUM_OUTPUTS);
      if (o == null)
      {
        return false;
      }
      num_outputs = ((Number) o).intValue();
    }
    e.describe(NUM_OUTPUTS, "The number of simultaneous outputs to invert");
    e.writeInput(NUM_OUTPUTS, num_outputs);
    e.writeInput(METHOD, ReversibleGenerator.NAME);
    Reversible g = m_factory.setCondition(pt, e);
    RandomInteger rint = new RandomInteger(m_minLength, m_maxLength).setSeed(getSeed() + 10);
    DistinctStreamSolver solver = new DistinctStreamSolver(g, new AllTruePicker(rint), m_maxTries, num_outputs);
    ReversibleGenerator gen = new ReversibleGenerator(solver);
    e.setGenerator(gen);
    return true;
  }

}
