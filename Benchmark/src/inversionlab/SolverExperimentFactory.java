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

import static inversionlab.InputOutputFactory.METHOD;
import static inversionlab.StreamExperiment.SIZE_LIMIT;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.experiment.ExperimentFactory;
import ca.uqac.lif.labpal.region.Point;

public class SolverExperimentFactory extends ExperimentFactory<SolverExperiment>
{
	protected Map<String,SolverFactory<?>> m_factories;
	
	protected int m_traceLimit = 10;
	
	public SolverExperimentFactory(Laboratory lab)
	{
		super(lab);
		m_factories = new HashMap<String,SolverFactory<?>>();
	}
	
	public SolverExperimentFactory add(String name, SolverFactory<?> f)
  {
    m_factories.put(name, f);
    return this;
  }
	
	public SolverExperimentFactory setTraceLimit(int trace_limit)
	{
		m_traceLimit = trace_limit;
		return this;
	}
	
	@Override
  public SolverExperiment createExperiment(Point pt)
  {
    SolverExperiment e = new SolverExperiment();
    if (!set(pt, e))
    {
      return null;
    }
    return e;
  }
	
	public boolean set(Point pt, SolverExperiment e)
  {
    String name = pt.getString(METHOD);
    e.describe(METHOD, "The method used to generate input streams");
    e.writeInput(METHOD, name);
    SolverFactory<?> factory = m_factories.get(name);
    if (!factory.setSolver(pt, e))
    {
      return false;
    }
    e.writeInput(SIZE_LIMIT, m_traceLimit);
    return true;
  }

	@Override
	protected Class<? extends SolverExperiment> getClass(Point arg0)
	{
		// Not needed
		return null;
	}

	@Override
	protected Constructor<? extends SolverExperiment> getEmptyConstructor(Point arg0)
	{
		// Not needed
		return null;
	}

	@Override
	protected Constructor<? extends SolverExperiment> getPointConstructor(Point arg0)
	{
		// Not needed
		return null;
	}
}
