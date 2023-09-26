package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.reversi.util.MathList;

public class Not extends ApplyFunction
{
	protected static final List<MathList<Object>> s_listTrue;
	
	protected static final List<MathList<Object>> s_listFalse;
	
	static
	{
		s_listTrue = new ArrayList<MathList<Object>>(1);
		s_listTrue.add(MathList.toList(false));
		s_listFalse = new ArrayList<MathList<Object>>(1);
		s_listFalse.add(MathList.toList(true));
	}
	
  public Not()
  {
    super(1);
  }

	@Override
	protected List<MathList<Object>> getInputValuesFor(Object o)
	{
		// The outputs are reversed in the list themselves
		return Boolean.TRUE.equals(o) ? s_listTrue : s_listFalse;
	}
}
