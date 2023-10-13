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

import ca.uqac.lif.synthia.sequence.Playback;

public class RotateInteger extends Playback<Integer>
{
	public RotateInteger(int min, int max)
	{
		super(getRange(min, max));
		setLoop(true);
	}
	
	protected static List<Integer> getRange(int min, int max)
	{
		List<Integer> range = new ArrayList<Integer>(max - min + 1);
		for (int x = min; x <= max; x++)
		{
			range.add(x);
		}
		return range;
	}
}