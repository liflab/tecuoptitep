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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.reversi.util.MathList;

public class GroupTest
{
  @Test
  public void test1()
  {
    List<Object> bool_alph = Arrays.asList(true, false);
    Group g = new Group(2, 1);
    {
      And a = new And();
      Trim t = new Trim(1, bool_alph, false);
      NodeConnector.connect(t, 0, a, 1);
      g.addNodes(a, t);
      g.associateInput(0, a.getInputPin(0));
      g.associateInput(1, t.getInputPin(0));
      g.associateOutput(0, a.getOutputPin(0));
    }
    g.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(true))));
    List<Suggestion> sugs0 = g.getSuggestions(0);
    System.out.println(sugs0);
    List<Suggestion> sugs1 = g.getSuggestions(1);
    System.out.println(sugs1);
  }
  
  @Test
  public void test2()
  {
    List<Object> bool_alph = Arrays.asList(true, false);
    Group g = new Group(2, 1);
    {
      And a = new And();
      Trim t = new Trim(1, bool_alph, false);
      NodeConnector.connect(t, 0, a, 1);
      g.addNodes(a, t);
      g.associateInput(0, a.getInputPin(0));
      g.associateInput(1, t.getInputPin(0));
      g.associateOutput(0, a.getOutputPin(0));
    }
    g.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList(true))));
    Set<AritalSuggestion> sugs = AritalSuggestion.getSuggestions(g);
    System.out.println(sugs);
  }
}
