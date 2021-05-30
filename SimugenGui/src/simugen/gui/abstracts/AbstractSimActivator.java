package simugen.gui.abstracts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import simugen.core.interfaces.Engine;
import simugen.core.interfaces.Model;
import simugen.gui.SimActivator;
import simugen.gui.interfaces.LoadedViewContext;
import simugen.gui.interfaces.ModelRunner;
import simugen.gui.interfaces.RefreshableView;
import simugen.gui.views.parts.DefaultOutputViewLoadedViewContext;

public abstract class AbstractSimActivator extends AbstractUIPlugin {
	// The shared instance
	private static AbstractSimActivator plugin;

	private Class<? extends Model> modelClass;

	private ModelRunner modelRunner;

	private Engine modelEngine;

	private List<RefreshableView> refreshableViews = new ArrayList<>();

	private List<LoadedViewContext<?>> listLoadedViewContexts = new ArrayList<>();

	/**
	 * The constructor
	 */
	public AbstractSimActivator() {
		modelClass = setModelClass();
		modelRunner = setModelRunner();
		modelEngine = setModelEngine();
		addLoadedViewContext(new DefaultOutputViewLoadedViewContext());
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

	public String getOutputLocation() {
		IPreferenceStore preferences = SimActivator.getDefault().getPreferenceStore();

		String prefLoc = preferences.getString("OutputLocation");

		String location = "";

		if (prefLoc == "") {
			location = plugin.getModelEngine().getOutputLocation();
			preferences.setValue("OutputLocation", location);
		} else {
			location = prefLoc;
			SimActivator.getDefault().getModelEngine().setOutputLocation(location);
		}

		return location;
	}

	public void addRefreshableView(RefreshableView view) {
		this.refreshableViews.add(view);
	}

	public void removeRefreshableView(RefreshableView view) {
		this.refreshableViews.remove(view);
	}

	public List<RefreshableView> getRefreshableViews() {
		return refreshableViews;
	}

	protected abstract Class<? extends Model> setModelClass();

	protected abstract ModelRunner setModelRunner();

	protected abstract Engine setModelEngine();

	public void addLoadedViewContext(LoadedViewContext<?> loadedViewContext) {
		listLoadedViewContexts.add(loadedViewContext);
	}

	public <T extends ViewPart> void onViewLoaded(T view) {
		for(LoadedViewContext<?> lvc : listLoadedViewContexts) {
			if(lvc.getViewClass().equals(view.getClass())) {
				@SuppressWarnings("unchecked")
				LoadedViewContext<T> tlvc = (LoadedViewContext<T>) lvc;
				
				tlvc.onLoad(view);
			}
		}
	}
}
