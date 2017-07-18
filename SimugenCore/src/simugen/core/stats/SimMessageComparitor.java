package simugen.core.stats;

import java.util.Comparator;

import simugen.core.interfaces.SimMessage;

public class SimMessageComparitor implements Comparator<SimMessage>
{

	@Override
	public int compare(SimMessage o1, SimMessage o2)
	{
		if (o1.getModelTime() < o2.getModelTime())
			return -1;
		if (o1.getModelTime() > o2.getModelTime())
			return 1;
		return 0;
	}

}
