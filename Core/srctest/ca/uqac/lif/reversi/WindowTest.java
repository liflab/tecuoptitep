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

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ca.uqac.lif.reversi.util.MathList;

public class WindowTest
{
	@Test
	public void test1()
	{
		Window w = new Window(3, new Trim(2, Arrays.asList("a", "b", "c")));
		w.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList("a", "b"))));
		List<Suggestion> sugs = w.getSuggestions(0);
		assertEquals(9, sugs.size());
	}
}