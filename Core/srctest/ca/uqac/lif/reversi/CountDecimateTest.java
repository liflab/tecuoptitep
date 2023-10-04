package ca.uqac.lif.reversi;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ca.uqac.lif.reversi.util.MathList;

public class CountDecimateTest
{
	@Test
	public void test1()
	{
		List<Object> alphabet = Arrays.asList("a", "b", "c", "d");
		CountDecimate cd = new CountDecimate(2, alphabet);
		cd.setTargetOutputs(0, Arrays.asList(new Suggestion(MathList.toList("a"))));
		List<Suggestion> sugs = cd.getSuggestions(0);
		assertEquals(5, sugs.size());
	}
}
