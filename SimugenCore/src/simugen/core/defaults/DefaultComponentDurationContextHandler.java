package simugen.core.defaults;

import simugen.core.abstracts.AbstractDurationContextHandler;
import simugen.core.components.interfaces.Component;
import simugen.core.enums.SimTimeUnit;

public class DefaultComponentDurationContextHandler extends AbstractDurationContextHandler {

	public DefaultComponentDurationContextHandler(Component component, String pathToDB, SimTimeUnit unit,
			String tableName, String elementColumn, String componentColumn, int run, long epoch) {

		super(component, pathToDB, unit, tableName, elementColumn, componentColumn, run, epoch);
	}

	@Override
	protected void doHandleEvent(ElementTransferEvent event) {

	}
}
