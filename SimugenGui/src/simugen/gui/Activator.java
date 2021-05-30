package simugen.gui;

import simugen.core.interfaces.Engine;
import simugen.core.interfaces.Model;
import simugen.gui.abstracts.AbstractSimActivator;
import simugen.gui.interfaces.ModelRunner;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractSimActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "CoffeeShopModel"; //$NON-NLS-1$

	@Override
	protected Class<? extends Model> setModelClass() {
		return null;
	}

	@Override
	protected ModelRunner setModelRunner() {
		return null;
	}

	@Override
	protected Engine setModelEngine() {
		return null;
	}

}
