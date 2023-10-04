package ca.uqac.lif.reversi.functions;

import java.util.HashSet;
import java.util.Set;

import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.Reversible;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

public class DistinctStreamSolver extends StreamSolver
{
  protected final Set<AritalSuggestion> m_suggestions;

  protected AritalSuggestion m_next = null;

  public DistinctStreamSolver(Reversible pipeline, Picker<MathList<Object>> output, long max_tries, int num_outputs)
  {
    super(pipeline, output, max_tries, num_outputs);
    m_suggestions = new HashSet<AritalSuggestion>();
  }

  public DistinctStreamSolver(Reversible pipeline, Picker<MathList<Object>> output)
  {
    this(pipeline, output, -1, 1);
  }

  @Override
  public boolean isDone()
  {
    if (m_next != null)
    {
      return false;
    }
    while (m_next == null)
    {
      while (m_bucket.hasNext())
      {
        AritalSuggestion s = m_bucket.next();
        if (!m_suggestions.contains(s))
        {
          m_next = s;
          m_suggestions.add(s);
          return false;
        }
      }
      fillBucket();
    }
    return true;
  }
  
  @Override
  public AritalSuggestion pick()
  {
    if (!isDone())
    {
      AritalSuggestion s = m_next;
      m_next = null;
      return s;
    }
    throw new NoMoreElementException("Exceeded number of attempts");
  }

}
