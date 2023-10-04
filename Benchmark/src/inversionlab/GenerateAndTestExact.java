package inversionlab;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class GenerateAndTestExact extends GenerateAndTest
{

	public GenerateAndTestExact(Processor p, int min_length, int max_length,
			Picker<MathList<Object>> list_picker)
	{
		super(p, min_length, max_length, list_picker);
	}
	
	@Override
	public boolean isValidSuggestion(MathList<Object>[] inputs)
	{
		// TODO
		return false;
	}

}
