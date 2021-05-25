package simugen.gui.abstracts;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import simugen.core.interfaces.Engine;
import simugen.core.interfaces.Model;
import simugen.gui.interfaces.ModelRunner;

public abstract class AbstractSimActivator extends AbstractUIPlugin {
	// The shared instance
	private static AbstractSimActivator plugin;
	
	private Class<? extends Model> modelClass;
	
	private ModelRunner modelRunner;
	
	private Engine modelEngine;

	/**
	 * The constructor
	 */
	public AbstractSimActivator() {
		modelClass = setModelClass();
		modelRunner = setModelRunner();
		modelEngine = setModelEngine();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AbstractSimActivator getDefault() {
		return plugin;
	}

	public Class<? extends Model> getModelClass() {
		return plugin.modelClass;
	}
	
	public ModelRunner getModelRunner() {
		return plugin.modelRunner;
	}
	
	public Engine getModelEngine() {
		return plugin.modelEngine;
	}
	
	protected abstract Class<? extends Model> setModelClass();
	
	protected abstract ModelRunner setModelRunner();

	protected abstract Engine setModelEngine();
}
