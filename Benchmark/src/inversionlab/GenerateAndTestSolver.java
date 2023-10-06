package inversionlab;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.reversi.AritalSuggestion;
import ca.uqac.lif.reversi.util.MathList;
import ca.uqac.lif.synthia.Picker;

public class GenerateAndTestSolver extends GenerateAndTest implements Solver
{
	/**
	 * The maximum number of times the solver will attempt to find a solution for
	 * a given output.
	 */
	protected int m_maxTries = 1000;
	
	public GenerateAndTestSolver(Processor p, int min_length, int max_length,
			Picker<MathList<Object>> list_picker)
	{
		super(p, min_length, max_length, list_picker);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<AritalSuggestion> solve(AritalSuggestion outputs)
	{
		Set<AritalSuggestion> sugs = new HashSet<AritalSuggestion>();
		List<Object> target = (List<Object>) outputs.get(0);
		for (int i = 0; i < m_maxTries; i++)
		{
			MathList<Object>[] in_sug = new MathList[m_processor.getInputArity()];
			for (int j = 0; j < in_sug.length; j++)
			{
				in_sug[j] = m_listPicker.pick();
			}
			List<Object> out = runThrough(in_sug);
			if (sameOutput(target, out))
			{
				AritalSuggestion s = new AritalSuggestion(in_sug.length);
				for (int j = 0; j < in_sug.length; j++)
				{
					s.set(j, in_sug[j]);
				}
				sugs.add(s);
			}
		}
		return sugs;
	}
	
	protected static boolean sameOutput(List<Object> target, List<Object> candidate)
	{
		if (target.size() != candidate.size())
		{
			return false;
		}
		for (int i = 0; i < target.size(); i++)
		{
			if (!target.get(i).equals(candidate.get(i)))
			{
				return false;
			}
		}
		return true;
	}
}
