package simugen.gui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import simugen.core.interfaces.Model;
import simugen.core.test.TestNewModel;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "TestGui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private Model model;

	/**
	 * The constructor
	 */
	public Activator() {
		// model = new TestNewModel(null);
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
	public static Activator getDefault() {
		return plugin;
	}

	public Model getModel() {
		return plugin.model;
	}
}
