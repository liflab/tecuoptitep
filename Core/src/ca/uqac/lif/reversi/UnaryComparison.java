package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.enumerative.AllPickers;

public abstract class UnaryComparison<T> extends AlphabetFunction
{
	protected final T m_constant;

	public UnaryComparison(T constant, List<Object> alphabet, Picker<Boolean> coin)
	{
		super(1, alphabet, coin);
		m_constant = constant;
	}

	public UnaryComparison(T constant, List<Object> alphabet)
	{
		super(1, alphabet);
		m_constant = constant;
	}

	@Override
	protected void getSuggestions()
	{
		int sug_cnt = 0;
		List<Suggestion> in_sugs = new ArrayList<Suggestion>(m_targetOutput.size());
		for (Suggestion out_sug : m_targetOutput)
		{
			List<?> out_stream = (List<?>) out_sug.getValue();
			Bounded<?>[] pickers = new Bounded[out_stream.size()];
			for (int i = 0; i < pickers.length; i++)
			{
				Object e = out_stream.get(i);
				pickers[i] = getPickerForInput((Boolean) e);
			}
			AllPickers all = new AllPickers(pickers);
			while (!all.isDone())
			{
				MathList<Object> in_list = new MathList<Object>();
				Object[] elems = all.pick();
				for (Object e : elems)
				{
					in_list.add(e);
				}
				Suggestion in_sug = new Suggestion(in_list);
				in_sug.addLineage(out_sug.getLineage());
				in_sug.addLineage(new Association(this, sug_cnt));
				in_sugs.add(in_sug);
				sug_cnt++;
			}
		}
		m_suggestedInputs.put(0, in_sugs);
	}

	protected abstract Bounded<?> getPickerForInput(boolean o);
}
