package inversionlab;

import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class RandomListPicker implements Picker<MathList<Object>>
{
	protected final Picker<Integer> m_length;

	protected final Picker<Object> m_symbol;

	public RandomListPicker(Picker<Integer> len, Picker<Object> symbol)
	{
		super();
		m_length = len;
		m_symbol = symbol;
	}

	@Override
	public Picker<MathList<Object>> duplicate(boolean arg0)
	{
		throw new UnsupportedOperationException("Cannot duplicate this picker");
	}

	@Override
	public MathList<Object> pick()
	{
		int len = m_length.pick();
		MathList<Object> out = new MathList<Object>();
		for (int i = 0; i < len; i++)
		{
			out.add(m_symbol.pick());
		}
		return out;
	}

	@Override
	public void reset()
	{
		m_length.reset();
		m_symbol.reset();
	}
}