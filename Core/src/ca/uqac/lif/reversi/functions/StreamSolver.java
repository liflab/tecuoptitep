package ca.uqac.lif.reversi.functions;

import java.util.Arrays;
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

  public StreamSolver(Reversible pipeline, Picker<MathList<Object>> output, long max_tries)
  {
    super();
    m_pipeline = pipeline;
    m_output = output;
    m_bucket = new Bucket<>();
    m_maxTries = max_tries;
  }
  
  public StreamSolver(Reversible pipeline, Picker<MathList<Object>> output)
  {
    this(pipeline, output, -1);
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
    for (i = 0; (i < m_maxTries || m_maxTries < 0) && !m_bucket.hasNext(); i++)
    {
      m_pipeline.reset();
      MathList<Object> output = m_output.pick();
      m_pipeline.setTargetOutputs(0, Arrays.asList(new Suggestion(output)));
      //System.out.println(output + "?");
      Set<AritalSuggestion> sugs = AritalSuggestion.getSuggestions((Node) m_pipeline);
      m_bucket.fill(sugs);
    }
  }
}