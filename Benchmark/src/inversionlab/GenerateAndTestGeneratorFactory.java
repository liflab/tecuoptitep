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

import static inversionlab.PreconditionFactory.ALPHABET_SIZE;

import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.Choice;

public class GenerateAndTestGeneratorFactory extends GeneratorFactory<GroupProcessor>
{

  public GenerateAndTestGeneratorFactory(PreconditionFactory<GroupProcessor> factory, int min_length, int max_length)
  {
    super(factory, min_length, max_length);
  }

  @Override
  protected boolean instantiateGenerator(Point pt, GroupProcessor precondition, GeneratorExperiment e)
  {
    int alphabet_size = -1;
    {
      Object o = pt.get(ALPHABET_SIZE);
      if (o == null)
      {
        return false;
      }
      alphabet_size = ((Number) o).intValue();
    }
    GroupProcessor g = m_factory.setCondition(pt, e);
    GenerateAndTestGenerator gen = new GenerateAndTestGenerator(g, m_minLength, m_maxLength, getListPicker(alphabet_size));
    e.setGenerator(gen);
    return true;
  }
  
  protected RandomListPicker getListPicker(int alphabet_size)
  {
    Choice<Object> symbol = new Choice<>(new RandomFloat().setSeed(getSeed()));
    float prob = 1f / alphabet_size;
    for (Object o : PreconditionFactory.getStringAlphabet(alphabet_size))
    {
      symbol.add(o, prob);
    }
    RandomInteger rint = new RandomInteger(0, 1000).setSeed(getSeed() + 10);
    RandomListPicker rpl = new RandomListPicker(rint, symbol);
    return rpl;
  }

}
