/*
    Inversion of BeepBeep processor chains
    Copyright (C) 2023-2024 Laboratoire d'informatique formelle
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
import ca.uqac.lif.reversi.CountDecimate;
import ca.uqac.lif.reversi.Equals;
import ca.uqac.lif.reversi.Fork;
import ca.uqac.lif.reversi.Group;
import ca.uqac.lif.reversi.Trim;
import ca.uqac.lif.reversi.functions.DistinctStreamSolver;
import ca.uqac.lif.reversi.util.EndsInPicker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomInteger;

public class TwoEqualDecimateWildcard
{

	public static void main(String[] args)
	{
		List<Object> alphabet = Arrays.asList("a", "b", "c", "d");
    RandomBoolean coin = new RandomBoolean(1);
    coin.setSeed(10);
    DistinctStreamSolver solver = new DistinctStreamSolver(new Group(1, 1) {{
      Fork f = new Fork();
      associateInput(0, f.getInputPin(0));
      CountDecimate t = new CountDecimate(2, alphabet, coin, true);
      NodeConnector.connect(f, 0, t, 0);
      Equals eq = new Equals(alphabet, coin, false);
      NodeConnector.connect(t, 0, eq, 0);
      NodeConnector.connect(f, 1, eq, 1);
      Trim trim = new Trim(1, Arrays.asList(false, true), coin, true);
			NodeConnector.connect(eq, 0, trim, 0);
      associateOutput(0, trim.getOutputPin(0));
      addNodes(f, t, eq, trim);
    }}, new EndsInPicker(new RandomInteger(1, 20), true));
    while (!solver.isDone())
    {
      System.out.println(solver.pick());
    }
	}

}
