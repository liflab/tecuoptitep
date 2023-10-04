package inversionlab;

import static inversionlab.PreconditionFactory.ALPHABET_SIZE;
import static inversionlab.PreconditionFactory.CONDITION;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.labpal.region.Point;

/**
 * An object that provides instances of a precondition based on a name
 * contained in a {@link Point}.
 */
public abstract class PreconditionFactory
{
  public static final String MIN_LENGTH = "Min length";
  
  public static final String MAX_LENGTH = "Max length";
  
  public static final String CONDITION = "Condition";

  public static final String ALPHABET_SIZE = "Alphabet size";

  public static final String TWO_EQUAL_DECIMATE = "Two equal decimate";

  public static final String TWO_EQUAL_TRIM = "Two equal trim";

  protected final int m_minLength;

  protected final int m_maxLength;

  protected int m_seed = 0;


  public PreconditionFactory(int min_length, int max_length)
  {
    super();
    m_minLength = min_length;
    m_maxLength = max_length;
  }

  public PreconditionFactory setSeed(int seed)
  {
    m_seed = seed;
    return this;
  }

  public boolean set(Point pt, GenerationExperiment e)
  {
    String name = pt.getString(CONDITION);
    e.describe(CONDITION, "The precondition to generate input sequences for");
    e.writeInput(CONDITION, name);
    e.describe(MIN_LENGTH, "The minimum length of the streams to generate");
    e.writeInput(MIN_LENGTH, m_minLength);
    e.describe(MAX_LENGTH, "The maximum length of the streams to generate");
    e.writeInput(MAX_LENGTH, m_maxLength);
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
      return setTwoEqualDecimate(pt, alphabet_size, e);
    case TWO_EQUAL_TRIM:
      return setTwoEqualTrim(pt, alphabet_size, e);
    }
    return false;
  }

  protected int getSeed()
  {
    return m_seed;
  }

  protected abstract boolean setTwoEqualDecimate(Point pt, int alphabet_size, GenerationExperiment e);

  protected abstract boolean setTwoEqualTrim(Point pt, int alphabet_size, GenerationExperiment e);

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
