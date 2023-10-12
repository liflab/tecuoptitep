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
  /**
   * A single visible instance of the "wildcard" event.
   */
  public static final Wildcard WILDCARD = new Wildcard();
  
  /**
   * The collection of symbols that can be possible input events.
   */
  protected final List<Object> m_alphabet;
  
  /**
   * A flag indicating whether wildcards are allowed in the input streams
   * given as suggestions.
   */
  protected final boolean m_wildcardsAllowed;

  public AlphabetFunction(int in_arity, List<Object> alphabet, Picker<Boolean> coin, boolean wildcards)
  {
    super(in_arity, coin);
    m_alphabet = alphabet;
    m_wildcardsAllowed = wildcards;
  }
  
  public AlphabetFunction(int in_arity, List<Object> alphabet, boolean wildcards)
  {
    this(in_arity, alphabet, new Constant<Boolean>(true), wildcards);
  }
  
  public static class Wildcard
  {
    private Wildcard()
    {
      super();
    }
    
    @Override
    public String toString()
    {
      return "*";
    }
  }
}
