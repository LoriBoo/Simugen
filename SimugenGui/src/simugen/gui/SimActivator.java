package simugen.gui;

import simugen.core.defaults.DefaultEngine;
import simugen.core.interfaces.Engine;
import simugen.core.interfaces.Model;
import simugen.core.test.TestNewModel;
import simugen.gui.abstracts.AbstractSimActivator;
import simugen.gui.interfaces.ModelRunner;

public class SimActivator extends AbstractSimActivator{

	@Override
	protected Class<? extends Model> setModelClass() {
		return TestNewModel.class;
	}

	@Override
	protected ModelRunner setModelRunner() {
		return new SimModelRunner();
	}

	@Override
	protected Engine setModelEngine() {
		return new DefaultEngine();
	}

}
