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

import java.util.List;

import ca.uqac.lif.cep.Processor;
import inversionlab.GenerateAndTest.OutputCondition;

/**
 * An object containing a BeepBeep processor pipeline, along with a condition
 * to evaluate on the output of this pipeline.
 */
public class PipelineCondition implements OutputCondition
{
	protected final Processor m_pipeline;
	
	public PipelineCondition(Processor p)
	{
		super();
		m_pipeline = p;
	}
	
	public Processor getPipeline()
	{
		return m_pipeline;
	}
	
	@Override
	public boolean isValid(List<? extends Object> output)
	{
		return false;
	}
}
