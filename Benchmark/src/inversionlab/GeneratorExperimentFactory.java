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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import static inversionlab.GeneratorFactory.METHOD;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentFactory;
import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.units.Time;
import ca.uqac.lif.units.si.Second;

/**
 * An object that provides instances of a precondition based on a name
 * contained in a {@link Point}.
 */
public class GeneratorExperimentFactory extends ExperimentFactory<GeneratorExperiment>
{ 
  protected Map<String,GeneratorFactory<?>> m_factories;
  
  protected int m_sizeLimit = 10;
  
  protected Time m_timeout = new Second(0);

  public GeneratorExperimentFactory(Laboratory lab)
  {
    super(lab);
    m_factories = new HashMap<String,GeneratorFactory<?>>();
  }

  public GeneratorExperimentFactory add(String name, GeneratorFactory<?> f)
  {
    m_factories.put(name, f);
    return this;
  }
  
  public GeneratorExperimentFactory setTimeout(Time timeout)
  {
    m_timeout = timeout;
    return this;
  }
  
  public GeneratorExperimentFactory setSizeLimit(int size)
  {
    m_sizeLimit = size;
    return this;
  }
  
  @Override
  public GeneratorExperiment createExperiment(Point pt)
  {
    GeneratorExperiment e = new GeneratorExperiment();
    if (!set(pt, e))
    {
      return null;
    }
    return e;
  }

  public boolean set(Point pt, GeneratorExperiment e)
  {
    String name = pt.getString(METHOD);
    e.setTimeout(m_timeout);
    e.describe(METHOD, "The method used to generate input streams");
    e.writeInput(METHOD, name);
    GeneratorFactory<?> factory = m_factories.get(name);
    if (!factory.setGenerator(pt, e))
    {
      return false;
    }
    e.setSizeLimit(m_sizeLimit);
    return true;
  }

  @Override
  protected Class<? extends GeneratorExperiment> getClass(Point arg0)
  {
    // Not needed
    return null;
  }

  @Override
  protected Constructor<? extends GeneratorExperiment> getEmptyConstructor(Point arg0)
  {
    // Not needed
    return null;
  }

  @Override
  protected Constructor<? extends GeneratorExperiment> getPointConstructor(Point arg0)
  {
    // Not needed
    return null;
  }
}
