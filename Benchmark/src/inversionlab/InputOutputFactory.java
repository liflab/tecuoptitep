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

/**
 * A factory that produces objects finding inputs for a given output. This lab
 * contains two concrete factories: {@link GeneratorFactory} and
 * {@link SolverFactory}.
 *  
 * @param <T>
 */
public class InputOutputFactory<T>
{
  /**
   * The name of parameter "method".
   */
  public static final String METHOD = "Method";
  
  public static final String MIN_LENGTH = "Min length";
  
  public static final String MAX_LENGTH = "Max length";

  protected final int m_minLength;

  protected final int m_maxLength;
  
  protected int m_seed = 0;
  
  /**
   * The factory used to obtain preconditions based on their name.
   */
  protected final PreconditionFactory<T> m_factory;
  
  public InputOutputFactory(PreconditionFactory<T> factory, int min_length, int max_length)
  {
    super();
    m_factory = factory;
    m_minLength = min_length;
    m_maxLength = max_length;
  }
  
  public InputOutputFactory<T> setSeed(int seed)
  {
    m_seed = seed;
    return this;
  }
  
  public int getSeed()
  {
    return m_seed;
  }

}
