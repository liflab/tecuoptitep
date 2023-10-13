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

import static org.junit.Assert.*;
import static ca.uqac.lif.reversi.AlphabetFunction.WILDCARD; 

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Bounded;

public class CumulateAdditionTest
{
	@Test
	public void test1()
	{
		CumulateAddition sum = new CumulateAddition(Arrays.asList(0, 1, 2, 3, 4), false);
		sum.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(3, 5, 6))));
		List<Suggestion> in_sugs = sum.getSuggestions(0);
		assertEquals(1, in_sugs.size());
		List<?> in_stream = (List<?>) in_sugs.get(0).getValue();
		assertEquals(3, in_stream.size());
		assertEquals(3, in_stream.get(0));
		assertEquals(2, in_stream.get(1));
		assertEquals(1, in_stream.get(2));
	}
	
	@Test
  public void test2()
  {
    CumulateAddition sum = new CumulateAddition(Arrays.asList(0, 1, 2, 3, 4), false);
    sum.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(WILDCARD, 5, 6))));
    List<Suggestion> in_sugs = sum.getSuggestions(0);
    assertEquals(4, in_sugs.size()); // 141, 231, 321, 411
  }
	
	@Test
  public void test3()
  {
    CumulateAddition sum = new CumulateAddition(Arrays.asList(0, 1, 2, 3, 4), false);
    sum.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(WILDCARD, WILDCARD, 6))));
    List<Suggestion> in_sugs = sum.getSuggestions(0);
    for (Suggestion s : in_sugs)
    {
      List<?> list = (List<?>) s.getValue();
      int total = 0;
      for (Object o : list)
      {
        total += ((Number) o).intValue();
      }
      assertEquals(6, total);
    }
  }
	
	@Test
	public void testInternal1()
	{
	  CumulateAddition sum = new CumulateAddition(Arrays.asList(0, 1, 2, 3, 4), false);
	  Bounded<?> picker = sum.getSumPicker(1, 3);
	  assertFalse(picker.isDone());
	  assertEquals(3, ((Object[]) picker.pick())[0]);
	  assertTrue(picker.isDone());
	}
	
	@Test
  public void testInternal2()
  {
    CumulateAddition sum = new CumulateAddition(Arrays.asList(0, 1, 2, 3, 4), false);
    Bounded<?> picker = sum.getSumPicker(2, 3);
    int pick_cnt = 0;
    while (!picker.isDone())
    {
      picker.pick();
      pick_cnt++;
    }
    assertEquals(4, pick_cnt);
  }
}
