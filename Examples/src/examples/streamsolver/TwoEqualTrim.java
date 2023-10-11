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
package examples.streamsolver;

import java.util.Arrays;
import java.util.List;

import ca.uqac.lif.dag.NodeConnector;
import ca.uqac.lif.reversi.Equals;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;
import ca.uqac.lif.reversi.util.EndsInPicker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomInteger;

public class TwoEqualTrim
{

  public static void main(String[] args)
  {
    List<Object> alphabet = Arrays.asList("a", "b", "c", "d");
    RandomBoolean coin = new RandomBoolean(0.1);
    coin.setSeed(0);
    DistinctStreamSolver solver = new DistinctStreamSolver(new Group(1, 1) {{
      Fork f = new Fork();
      associateInput(0, f.getInputPin(0));
      Trim t = new Trim(1, alphabet, coin);
      NodeConnector.connect(f, 0, t, 0);
      Equals eq = new Equals(alphabet, coin);
      NodeConnector.connect(t, 0, eq, 0);
      NodeConnector.connect(f, 1, eq, 1);
      associateOutput(0, eq.getOutputPin(0));
      addNodes(f, t, eq);
    }}, new EndsInPicker(new RandomInteger(4, 20)), 100, 20);
    while (!solver.isDone())
    {
      System.out.println(solver.pick());
    }
  }
  
  protected static class Coin extends RandomBoolean
  {
  	public Coin(double p)
  	{
  		super(p);
  	}
  	
  	public Boolean pick()
  	{
  		boolean b = super.pick();
  		System.out.print(b);
  		return b;
  	}
  }

}
