package simugen.core.interfaces;

import simugen.core.messages.TransferMessage;
import simugen.core.messages.TransferNotReadyMessage;
import simugen.core.messages.TransferReadyMessage;
import simugen.core.messages.TransferRequestMessage;

public interface ElementTransferProtocol
{
	public void sendTransferMessage(long time, SimComponent component, SimElement element);

	public void sendTransferReadyMessage(long time, SimComponent component,
			SimElement element);

	public void sendTransferRequestMessage(long time, SimComponent component,
			SimElement element);

	public void sendTransferNotReadyMessage(long time, SimComponent component,
			SimElement element);

	public void getTransferMessage(TransferMessage message);

	public void getTransferReadyMessage(TransferReadyMessage message);

	public void getTransferRequestMessage(TransferRequestMessage message);

	public void getTransferNotReadyMessage(TransferNotReadyMessage message);
}
