package coffeeshopmodel;

import coffeeshopmodel.src.CoffeeShopModel;
import coffeeshopmodel.src.CoffeeShopModelRunner;
import simugen.core.defaults.DefaultEngine;
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
		return CoffeeShopModel.class;
	}

	@Override
	protected ModelRunner setModelRunner() {
		return new CoffeeShopModelRunner();
	}

	@Override
	protected Engine setModelEngine() {
		return new DefaultEngine();
	}

}
