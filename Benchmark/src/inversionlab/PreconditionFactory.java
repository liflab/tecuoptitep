package inversionlab;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.labpal.experiment.Experiment;
import ca.uqac.lif.labpal.region.Point;

public abstract class PreconditionFactory<T>
{
  public static final String CONDITION = "Condition";

  public static final String ALPHABET_SIZE = "Alphabet size";

  public static final String TWO_EQUAL_DECIMATE = "Two equal decimate";

  public static final String TWO_EQUAL_TRIM = "Two equal trim";
  
  protected int m_seed;
  
  public T setCondition(Point pt, Experiment e)
  {
    String name = pt.getString(CONDITION);
    e.writeInput(CONDITION, name);
    int alphabet_size = -1;
    {
      Object o = pt.get(ALPHABET_SIZE);
      if (o != null)
      {
        alphabet_size = ((Number) o).intValue();
      }
    }
    e.describe(ALPHABET_SIZE, "The number of possible distinct events found in a stream");
    e.writeInput(ALPHABET_SIZE, alphabet_size);
    switch (name)
    {
    case TWO_EQUAL_DECIMATE:
      return getTwoEqualDecimate(pt, alphabet_size, e);
    case TWO_EQUAL_TRIM:
      return getTwoEqualTrim(pt, alphabet_size, e);
    }
    return null;
  }
  
  protected abstract T getTwoEqualDecimate(Point pt, int alphabet_size, Experiment e);

  protected abstract T getTwoEqualTrim(Point pt, int alphabet_size, Experiment e);
  
  /**
   * Gets an instance of an alphabet of input events made of single characters.
   * @param size The number of characters in the alphabet
   * @return The alphabet
   */
  protected static List<Object> getStringAlphabet(int size)
  {
    List<Object> list = new ArrayList<Object>(size);
    for (int i = 0; i < size; i++)
    {
      list.add(Character.toString((char) (i + 65)));
    }
    return list;
  }
}
