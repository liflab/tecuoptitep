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

/**
 * A factory object that produces instances of {@link Generator} from a 
 * {@link Point}.
 * @param <T> The type of generator
 */
public abstract class GeneratorFactory<T> extends InputOutputFactory<T>
{  
  public GeneratorFactory(PreconditionFactory<T> factory, int min_length, int max_length)
  {
    super(factory, min_length, max_length);
  }
  
  public boolean setGenerator(Point pt, GeneratorExperiment e)
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
    return instantiateGenerator(pt, precondition, e);    
  }
  
  @Override
  public GeneratorFactory<T> setSeed(int seed)
  {
    super.setSeed(seed);
    return this;
  }
  
  protected abstract boolean instantiateGenerator(Point pt, T precondition, GeneratorExperiment e);
}
