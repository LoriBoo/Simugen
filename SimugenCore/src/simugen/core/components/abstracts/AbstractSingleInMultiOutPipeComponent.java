package simugen.core.components.abstracts;

import java.util.HashMap;
import java.util.Map;

import simugen.core.components.interfaces.Component;
import simugen.core.interfaces.DataContext;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.TransferOutputPipe;
import simugen.core.transfer.interfaces.MultiTransferOutputPipe;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;

public abstract class AbstractSingleInMultiOutPipeComponent extends AbstractComponent
		implements MultiTransferOutputPipe, SingleTransferInputPipe {

	protected Map<Component, TransferOutputPipe> mapPipes = new HashMap<>();

	private final TransferInputPipe inputPipe = new TransferInputPipe(this);

	private final DataContext<Component, ElementTransferData> dataContext = new DataContext<Component, ElementTransferData>() {
		@Override
		public void processData(ElementTransferData data) {
			AbstractSingleInMultiOutPipeComponent.this.receiveElement(data);
		}
	};

	public AbstractSingleInMultiOutPipeComponent() {
		addProcessDataContext(ElementTransferData.class, dataContext);
	}

	@Override
	public TransferInputPipe getTransferInputPipe() {
		return inputPipe;
	}

	@Override
	public void addTransferOutputPipe(Component connected, TransferInputPipe pipe) {
		mapPipes.put(connected, new TransferOutputPipe());
		
		mapPipes.get(connected).union(pipe);
	}

	@Override
	public TransferOutputPipe getTransferOutputPipe(Component connected) {
		return mapPipes.get(connected);
	}

}
