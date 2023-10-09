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

import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

/**
 * A reversible function that requires a fixed alphabet of input symbols.
 */
public abstract class AlphabetFunction extends ReversibleFunction
{
  private final List<Object> m_alphabet;

  public AlphabetFunction(int in_arity, List<Object> alphabet, Picker<Boolean> coin)
  {
    super(in_arity, coin);
    m_alphabet = alphabet;
  }
  
  public AlphabetFunction(int in_arity, List<Object> alphabet)
  {
    this(in_arity, alphabet, new Constant<Boolean>(true));
  }
  
  protected List<Object> getAlphabet()
  {
    return m_alphabet;
  }

}
