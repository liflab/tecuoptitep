package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.reversi.util.AllPickersList;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.sequence.Playback;

public abstract class ApplyFunction extends ReversibleFunction
{
	public ApplyFunction(int in_arity)
	{
		super(in_arity);
	}
	
	public ApplyFunction(int in_arity, Picker<Boolean> coin)
	{
		super(in_arity, coin);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSuggestions()
	{
		for (int i = 0; i < getInputArity(); i++)
		{
			m_suggestedInputs.put(i, new ArrayList<Suggestion>());
		}
		int sug_cnt = 0;
		for (Suggestion sug : m_targetOutput)
		{
			MathList<?> sequence = (MathList<?>) sug.getValue();
			Bounded<?>[] pickers = new Bounded<?>[sequence.size()];
			for (int i = 0; i < pickers.length; i++)
			{
				List<MathList<Object>> values = getInputValuesFor(sequence.get(i));
				Playback<Object> pb = new Playback<Object>(values.toArray()).setLoop(false);
				pickers[i] = pb;
			}
			AllPickersList all = new AllPickersList(pickers);
			while (!all.isDone())
			{
				MathList<Object> list = (MathList<Object>) all.pick();
				if (!m_coin.pick())
        {
        	// Coin toss to select if suggestion is included
        	continue;
        }
				MathList<Object>[] in_sequences = new MathList[getInputArity()]; 
				for (int i = 0; i < in_sequences.length; i++)
				{
					in_sequences[i] = new MathList<Object>();
				}
				for (Object o_tuple : list)
				{
					MathList<Object> tuple = (MathList<Object>) o_tuple;
					for (int i = 0; i < in_sequences.length; i++)
					{
						in_sequences[i].add(tuple.get(i));
					}
				}
				for (int i = 0; i < in_sequences.length; i++)
				{
					Suggestion in_sug = new Suggestion(in_sequences[i]);
					in_sug.addLineage(sug.getLineage());
					in_sug.addLineage(new Association(this, sug_cnt));
					m_suggestedInputs.get(i).add(in_sug);
				}
				sug_cnt++;
			}
		}
	}

	protected abstract List<MathList<Object>> getInputValuesFor(Object o);

}
