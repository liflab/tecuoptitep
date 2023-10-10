package ca.uqac.lif.reversi;

import ca.uqac.lif.synthia.Picker;

public interface MonteCarloReversible extends Reversible
{
	public void setCoin(Picker<Boolean> coin);
}
