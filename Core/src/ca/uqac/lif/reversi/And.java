package ca.uqac.lif.reversi;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.reversi.util.MathList;

public class And extends ApplyFunction
{
	protected static final List<MathList<Object>> s_listTrue;
	
	protected static final List<MathList<Object>> s_listFalse;
	
	static
	{
		s_listTrue = new ArrayList<MathList<Object>>(1);
		s_listTrue.add(MathList.toList(true, true));
		s_listFalse = new ArrayList<MathList<Object>>(1);
		s_listFalse.add(MathList.toList(false, true));
		s_listFalse.add(MathList.toList(false, false));
		s_listFalse.add(MathList.toList(true, false));
	}
	
  public And()
  {
    super(2);
  }

	@Override
	protected List<MathList<Object>> getInputValuesFor(Object o)
	{
		return Boolean.TRUE.equals(o) ? s_listTrue : s_listFalse;
	}
}
