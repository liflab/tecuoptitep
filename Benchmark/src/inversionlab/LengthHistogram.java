package inversionlab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static inversionlab.GeneratorExperiment.LENGTH_DISTRIBUTION;
import static inversionlab.GeneratorExperiment.METHOD;

import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.json.JsonNumber;
import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.table.Table;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.AtomicFunction;
import ca.uqac.lif.petitpoucet.function.RelationNodeFactory;
import ca.uqac.lif.spreadsheet.Spreadsheet;

public class LengthHistogram extends Table
{
  protected final Map<String,Experiment> m_categories;
  
  protected final List<String> m_names;
  
  public LengthHistogram()
  {
    super();
    m_categories = new HashMap<String,Experiment>();
    m_names = new ArrayList<String>();
  }
  
  protected LengthHistogram add(Collection<? extends Experiment> exps)
  {
    for (Experiment e : exps)
    {
      String method = e.readString(METHOD);
      m_categories.put(method, e);
      m_names.add(method);
    }
    Collections.sort(m_names);
    return this;
  }
  
  @Override
  public Status getStatus()
  {
    return Status.DONE;
  }

  @Override
  public Collection<Experiment> dependsOn()
  {
    return m_categories.values();
  }

  @Override
  protected Spreadsheet calculateSpreadsheet()
  {
    int min_len = Integer.MAX_VALUE, max_len = Integer.MIN_VALUE;
    for (Experiment e : m_categories.values())
    {
      JsonMap distro = (JsonMap) e.read(LENGTH_DISTRIBUTION);
      for (String s : distro.keySet())
      {
        int len = Integer.parseInt(s);
        min_len = Math.min(min_len, len);
        max_len = Math.max(max_len, len);
      }
    } 
    Spreadsheet sp = new Spreadsheet(m_categories.size() + 1, max_len - min_len + 2);
    // First line
    sp.set(0, 0, "Length");
    for (int i = 0; i < m_names.size(); i++)
    {
      sp.set(i + 1, 0, m_names.get(i));
    }
    for (int i = 0; i <= max_len - min_len; i++)
    {
      sp.set(0, i + 1, i + min_len);
    }
    for (int i = 0; i < m_names.size(); i++)
    {
      Experiment e = m_categories.get(m_names.get(i));
      JsonMap distro = (JsonMap) e.read(LENGTH_DISTRIBUTION);
      for (Map.Entry<String,JsonElement> entry : distro.entrySet())
      {
        int len = Integer.parseInt(entry.getKey());
        int value = ((JsonNumber) entry.getValue()).numberValue().intValue();
        sp.set(i + 1, len - min_len + 1, value);
      }
    }
    return sp;
  }
  
  @Override
  public float getProgression()
  {
    return 0;
  }

  @Override
  public Table dependsOn(int arg0, int arg1)
  {
    return null;
  }

  @Override
  protected PartNode explain(Part p, RelationNodeFactory factory)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AtomicFunction duplicate(boolean with_state)
  {
    // TODO Auto-generated method stub
    return null;
  }

}
