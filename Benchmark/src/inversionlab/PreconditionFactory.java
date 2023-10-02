package inversionlab;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.labpal.region.Point;
import ca.uqac.lif.synthia.Picker;

/**
 * An object that provides instances of a precondition based on a name
 * contained in a {@link Point}.
 */
public abstract class PreconditionFactory
{
	public static final String CONDITION = "Condition";
	
	public static final String ALPHABET_SIZE = "Alphabet size";
	
	public static final String TWO_EQUAL_DECIMATE = "Two equal decimate";
	
	public static final String TWO_EQUAL_TRIM = "Two equal trim";
	
	protected int m_seed = 0;
	
	protected Picker<Integer> m_length;
	
	public PreconditionFactory(Picker<Integer> length)
	{
		super();
		m_length = length;
	}
	
	public PreconditionFactory setSeed(int seed)
	{
		m_seed = seed;
		return this;
	}
	
	public boolean set(Point pt, StreamGenerationExperiment e)
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
	
	protected abstract boolean setTwoEqualDecimate(Point pt, int alphabet_size, StreamGenerationExperiment e);
	
	protected abstract boolean setTwoEqualTrim(Point pt, int alphabet_size, StreamGenerationExperiment e);
	
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
