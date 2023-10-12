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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ca.uqac.lif.reversi.util.MathList;

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
}
