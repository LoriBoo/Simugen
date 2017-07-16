package simugen.core.abstracts;

import simugen.core.interfaces.SimEvent;

public abstract class AbstractProcessResponseEvent implements SimEvent
{
	private boolean processed = false;
	
	private boolean response = false;
	
	public void setProcessedResponse(boolean processed, boolean response)
	{
		this.processed = processed;
		
		this.response = response;
	}
	
	@Override
	public boolean processed()
	{
		return processed;
	}
	
	@Override
	public boolean response()
	{
		return response;
	}
}
