package ca.uqac.lif.reversi.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Ltl
{
	public static class And extends NaryConnective
	{
		@Override
		protected String getSymbol()
		{
			return "&";
		}
	}
	
	public static class Or extends NaryConnective
	{
		@Override
		protected String getSymbol()
		{
			return "|";
		}
	}
	
	public static class Not extends UnaryConnective
	{
		public Not(Ltl op)
		{
			super(op);
		}
		
		public Not()
		{
			super();
		}
		
		@Override
		protected String getSymbol()
		{
			return "!";
		}
	}
	
	public static class Globally extends UnaryConnective
	{
		public Globally(Ltl op)
		{
			super(op);
		}
		
		public Globally()
		{
			super();
		}
		
		@Override
		protected String getSymbol()
		{
			return "G";
		}
	}
	
	public static class Eventually extends UnaryConnective
	{
		public Eventually(Ltl op)
		{
			super(op);
		}
		
		public Eventually()
		{
			super();
		}
		
		@Override
		protected String getSymbol()
		{
			return "F";
		}
	}
	
	public static class Next extends UnaryConnective
	{
		public Next(Ltl op)
		{
			super(op);
		}
		
		public Next()
		{
			super();
		}
		
		@Override
		protected String getSymbol()
		{
			return "X";
		}
	}
	
	public static abstract class UnaryConnective extends Ltl
	{
		protected Ltl m_operand;
		
		public UnaryConnective()
		{
			super();
			m_operand = null;
		}
		
		public UnaryConnective(Ltl op)
		{
			super();
			m_operand = op;
		}
		
		public UnaryConnective add(Ltl op)
		{
			m_operand = op;
			return this;
		}
		
		@Override
		public void print(PrintStream ps)
		{
			ps.print("(");
			ps.print(getSymbol() + " ");
			m_operand.print(ps);
			ps.print(")");
		}
	}
	
	public static abstract class NaryConnective extends Ltl
	{
		protected final List<Ltl> m_operands;
		
		public NaryConnective()
		{
			super();
			m_operands = new ArrayList<Ltl>();
		}
		
		public NaryConnective add(Ltl op)
		{
			m_operands.add(op);
			return this;
		}

		@Override
		public void print(PrintStream ps)
		{
			if (m_operands.isEmpty())
			{
				return;
			}
			if (m_operands.size() == 1)
			{
				m_operands.get(0).print(ps);
			}
			ps.print("(");
			for (int i = 0; i < m_operands.size(); i++)
			{
				if (i > 0)
				{
					ps.print(" " + getSymbol() + " ");
				}
				m_operands.get(i).print(ps);
			}
			ps.print(")");
		}
	}
	
	protected abstract String getSymbol();
	
	public abstract void print(PrintStream ps);
}
