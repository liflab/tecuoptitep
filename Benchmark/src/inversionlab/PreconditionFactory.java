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

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.region.Point;

/**
 * The conditions are:
 * <ul>
 * <li><i>Two equal decimate (F)</i>:</li>
 * <li><i>Two equal decimate (G)</i>:</li>
 * <li><i>Two equal trim (F)</i>:</li>
 * <li><i>Two equal trim (G)</i>:</li>
 * </ul>
 * @param <T>
 */
public abstract class PreconditionFactory<T>
{
  public static final String CONDITION = "Condition";

  public static final String ALPHABET_SIZE = "Alphabet size";
  
  public static final String AT_LEAST_N_IN_WINDOW = "At least n in window";

  public static final String TWO_EQUAL_DECIMATE_F = "Two equal decimate (F)";

  public static final String TWO_EQUAL_TRIM_F = "Two equal trim (F)";
  
  public static final String TWO_EQUAL_DECIMATE_G = "Two equal decimate (G)";

  public static final String TWO_EQUAL_TRIM_G = "Two equal trim (G)";
  
  protected int m_seed = 0;
  
  protected int m_minLength = 0;
  
  protected int m_maxLength = 0;
  
  public PreconditionFactory<T> setLengthBounds(int min, int max)
  {
  	m_minLength = min;
  	m_maxLength = max;
  	return this;
  }
  
  public T setCondition(Point pt, Experiment e)
  {
    String name = pt.getString(CONDITION);
    e.writeInput(CONDITION, name);
    int alphabet_size = -1;
    {
      Object o = pt.get(ALPHABET_SIZE);
      if (o != null)
      {
        alphabet_size = ((Number) o).intValue();
      }
    }
    e.describe(ALPHABET_SIZE, "The number of possible distinct events found in a stream");
    e.writeInput(ALPHABET_SIZE, alphabet_size);
    switch (name)
    {
    case AT_LEAST_N_IN_WINDOW:
      return getAtLeastNInWindow(pt, alphabet_size, e, true);
    case TWO_EQUAL_DECIMATE_F:
      return getTwoEqualDecimate(pt, alphabet_size, e, false);
    case TWO_EQUAL_TRIM_F:
      return getTwoEqualTrim(pt, alphabet_size, e, false);
    case TWO_EQUAL_DECIMATE_G:
      return getTwoEqualDecimate(pt, alphabet_size, e, true);
    case TWO_EQUAL_TRIM_G:
      return getTwoEqualTrim(pt, alphabet_size, e, true);
    }
    return null;
  }
  
  protected abstract T getTwoEqualDecimate(Point pt, int alphabet_size, Experiment e, boolean always);

  protected abstract T getTwoEqualTrim(Point pt, int alphabet_size, Experiment e, boolean always);
  
  protected abstract T getAtLeastNInWindow(Point pt, int alphabet_size, Experiment e, boolean always);
  
  /**
   * Gets an instance of an alphabet of input events made of single characters.
   * @param size The number of characters in the alphabet
   * @return The alphabet
   */
  protected static List<Object> getStringAlphabet(int size)
  {
    List<Object> list = new ArrayList<Object>(size);
    for (int i = 0; i < size; i++)
    {
      list.add(Character.toString((char) (i + 65)));
    }
    return list;
  }
  
  /**
   * Gets an instance of an alphabet of input events made of the range
   * of integers from 0 to n (inclusive).
   * @param size The size of the range
   * @return The alphabet
   */
  protected static List<Object> getIntegerAlphabet(int size)
  {
    List<Object> list = new ArrayList<Object>(size);
    for (int i = 0; i < size; i++)
    {
      list.add(i);
    }
    return list;
  }
}
