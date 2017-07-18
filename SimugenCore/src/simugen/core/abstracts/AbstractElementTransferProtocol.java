package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.interfaces.ElementTransferProtocol;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimElement;
import simugen.core.interfaces.SimElementContainer;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimMessage;
import simugen.core.interfaces.SimMessageParticipant;
import simugen.core.messages.TransferMessage;
import simugen.core.messages.TransferNotReadyMessage;
import simugen.core.messages.TransferReadyMessage;
import simugen.core.messages.TransferRequestMessage;

public abstract class AbstractElementTransferProtocol extends Tickable
		implements ElementTransferProtocol, SimMessageParticipant,
		SimElementContainer, SimComponent
{
	protected SimComponent owner;

	protected SimElement element;

	private final List<SimMessage> messages = new ArrayList<>();

	public AbstractElementTransferProtocol()
	{
		this.owner = this;
	}

	@Override
	public List<SimMessage> getMessages(SimEngine engine, long tick)
	{
		final List<SimMessage> m = new ArrayList<>();

		if (this.canDoGetMessages(tick))
		{
			final List<SimMessage> sub = doGetMessages(engine, tick);

			if (sub != null)
			{
				m.addAll(sub);
			}
		}

		m.addAll(messages);

		messages.clear();

		return m.isEmpty() ? null : m;
	}

	abstract public List<SimMessage> doGetMessages(SimEngine engine, long tick);

	@Override
	public void receiveMessage(SimMessage message)
	{
		if (message instanceof TransferRequestMessage)
		{
			getTransferRequestMessage(
					TransferRequestMessage.class.cast(message));
		}
		else if (message instanceof TransferReadyMessage)
		{
			getTransferReadyMessage(TransferReadyMessage.class.cast(message));
		}
		else if (message instanceof TransferNotReadyMessage)
		{
			getTransferNotReadyMessage(
					TransferNotReadyMessage.class.cast(message));
		}
		else if (message instanceof TransferMessage)
		{
			getTransferMessage(TransferMessage.class.cast(message));
		}
		else
		{
			doRecieveMessage(message);
		}
	}

	abstract public void doRecieveMessage(SimMessage message);

	/**
	 * Actually send the transfer message. Remove the element from our
	 * container.
	 */
	@Override
	public void sendTransferMessage(long time, SimComponent component,
			SimElement element)
	{
		if (this.element != null && this.element == element)
		{
			messages.add(new TransferMessage(time, element, owner, component));

			this.doRemoveElement(this.element);

			this.element = null;
		}
	}

	/**
	 * Send a transfer ready message; Tell the upstream component that we are
	 * ready to receive the element.
	 */
	@Override
	public void sendTransferReadyMessage(long time, SimComponent component,
			SimElement element)
	{
		if (this.element != null && this.element == element)
		{
			messages.add(
					new TransferReadyMessage(time, element, owner, component));
		}
	}

	/**
	 * Send a transfer request message; We've got an element we want to
	 * transfer, ask the downstream component if it can recieve it
	 */
	@Override
	public void sendTransferRequestMessage(long time, SimComponent component,
			SimElement element)
	{
		this.element = element;

		messages.add(
				new TransferRequestMessage(time, element, owner, component));
	}

	/**
	 * Send a transfer ready message to the downstream component.
	 */
	@Override
	public void sendTransferNotReadyMessage(long time, SimComponent component,
			SimElement element)
	{
		if (this.element != null && this.element == element)
		{
			messages.add(new TransferNotReadyMessage(time, element, owner,
					component));

			element = null;
		}
	}

	/**
	 * If at this point we get a transfer message, the upstream component does
	 * not have the element anymore. If for some reason this component cannot
	 * receive it anymore, the element is no longer in the system.
	 */
	@Override
	public void getTransferMessage(TransferMessage message)
	{
		final SimElement element = message.getSimElement();

		this.doRecieveElement(element);
	}

	/**
	 * The downstream component has told us that it is ready to receive the
	 * element.
	 * 
	 * Send a transfer message with the element.
	 */
	@Override
	public void getTransferReadyMessage(TransferReadyMessage message)
	{
		assert message.getSimElement() == this.element;

		sendTransferMessage(message.getModelTime(),
				message.getSimComponentFrom(), this.element);
	}

	/**
	 * This component got a request from an upstream component.
	 * 
	 * Check if we can receive it; If we can, send a ready message. If we can't,
	 * send a not ready message.
	 */
	@Override
	public void getTransferRequestMessage(TransferRequestMessage message)
	{
		if (this.canRecieveElement())
		{
			this.element = message.getSimElement();

			sendTransferReadyMessage(message.getModelTime(),
					message.getSimComponentFrom(), element);
		}
		else
		{
			this.element = null;

			sendTransferNotReadyMessage(message.getModelTime(),
					message.getSimComponentFrom(), message.getSimElement());
		}
	}

	/**
	 * If the downstream component is not ready, receive it back into our own
	 * container, regardless of if we can.
	 */
	@Override
	public void getTransferNotReadyMessage(TransferNotReadyMessage message)
	{
		assert message.getSimElement() == this.element;

		this.doRecieveElement(this.element);

		this.element = null;
	}
}
