package simugen.core.abstracts;

import simugen.core.interfaces.Element;

/**
 * Abstract implementation of {@link Element}.
 * 
 * @author Lorelei
 *
 */
public abstract class AbstractElement implements Element
{
	private String ID = null;

	@Override
	public void setLogID(String ID)
	{
		assert this.ID == null;

		this.ID = ID;
	}

	@Override
	public String getLogID()
	{
		return this.ID == null ? Element.super.getLogID() : ID;
	}

}
