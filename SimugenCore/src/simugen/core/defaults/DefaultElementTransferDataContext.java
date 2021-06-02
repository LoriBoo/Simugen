package simugen.core.defaults;

import simugen.core.abstracts.AbstractDataContext;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;

/**
 * Default context class for data type {@link ElementTransferData}. Generic type
 * T must extend {@link SingleTransferInputPipe}, which allows the owning object
 * to receive elements.
 * 
 * @author Lorelei
 *
 * @param <T>
 *            subclass of {@link SingleTransferInputPipe}
 */
final public class DefaultElementTransferDataContext<T extends SingleTransferInputPipe>
		extends AbstractDataContext<T, ElementTransferData>
{

	public DefaultElementTransferDataContext(T componentContext)
	{
		super(componentContext);
	}

	/**
	 * The process owner receives element data.
	 */
	@Override
	protected void doProcessData(T componentContext, ElementTransferData data)
	{
		componentContext.receiveElementTransferData(data);
	}

}
