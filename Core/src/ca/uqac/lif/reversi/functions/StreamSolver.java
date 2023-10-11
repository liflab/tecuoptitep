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
package ca.uqac.lif.reversi.functions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.dag.Node;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.Reversible;
import ca.uqac.lif.reversi.Suggestion;
import ca.uqac.lif.reversi.util.Bucket;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

public class StreamSolver implements Bounded<AritalSuggestion>
{
	protected final Reversible m_pipeline;

	protected final Picker<MathList<Object>> m_output;

	protected final Bucket<AritalSuggestion> m_bucket;

	protected final long m_maxTries;

	/**
	 * The number of outputs that are considered simultaneously when inverting
	 * a pipeline.
	 */
	protected int m_numOutputs = 1;

	public StreamSolver(Reversible pipeline, Picker<MathList<Object>> output, long max_tries, int num_outputs)
	{
		super();
		m_pipeline = pipeline;
		m_output = output;
		m_bucket = new Bucket<>();
		m_maxTries = max_tries;
	}

	public StreamSolver(Reversible pipeline, Picker<MathList<Object>> output)
	{
		this(pipeline, output, -1, 1);
	}

	@Override
	public void reset()
	{
		m_pipeline.reset();
		m_output.reset();
	}

	@Override
	public boolean isDone()
	{
		if (m_bucket.hasNext())
		{
			return false;
		}
		fillBucket();
		return !m_bucket.hasNext();
	}

	@Override
	public AritalSuggestion pick()
	{
		if (!m_bucket.hasNext())
		{
			fillBucket();
		}
		if (!m_bucket.hasNext())
		{
			throw new NoMoreElementException("Exceeded maximum number of attempts");
		}
		return m_bucket.next();
	}

	@Override
	public Picker<AritalSuggestion> duplicate(boolean with_state)
	{
		throw new UnsupportedOperationException("This picker cannot be duplicated (yet)");
	}

	protected void fillBucket()
	{
		long i;
		//System.out.println("Called");
		for (i = 0; (i < m_maxTries || m_maxTries < 0) && !m_bucket.hasNext(); i++)
		{
			List<Suggestion> out_sugs = new ArrayList<Suggestion>(m_numOutputs);
			for (int j = 0; j < m_numOutputs; j++)
			{
				List<Object> output = m_output.pick();
				out_sugs.add(new Suggestion(output));
				//System.out.print(output.size() + " ");
			}
			for (int k = 0; (k < m_maxTries || m_maxTries < 0); k++)
			{
				m_pipeline.reset();
				m_pipeline.setTargetOutputs(0, out_sugs);
				//System.out.println(out_sugs + "?");
				Set<AritalSuggestion> sugs = AritalSuggestion.getSuggestions((Node) m_pipeline);
				Set<AritalSuggestion> new_sugs = new HashSet<AritalSuggestion>();
				for (AritalSuggestion s : sugs)
				{
					AritalSuggestion out_sug = new AritalSuggestion(m_numOutputs + 1);
					for (int j = 0; j < m_numOutputs; j++)
					{
						out_sug.set(j, s.get(j));
					}
					out_sug.set(m_numOutputs, out_sugs);
					new_sugs.add(out_sug);
				}
				m_bucket.fill(new_sugs);
			}
		}
		//System.out.println("End");
	}
}
