package simugen.core.defaults;

import java.util.Comparator;

import simugen.core.interfaces.Event;

public class EventSorter implements Comparator<Event>
{

	@Override
	public int compare(Event arg0, Event arg1)
	{
		if (arg0.getTime() < arg1.getTime())
		{
			return -1;
		}
		else if (arg0.getTime() > arg1.getTime())
		{
			return 1;
		}

		return 0;
	}

}
