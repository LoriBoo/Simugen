package simugen.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.widgets.MenuItem;

import simugen.core.defaults.DefaultEngine;
import simugen.core.interfaces.Engine;
import simugen.core.interfaces.Model;
import simugen.core.test.TestNewModel;
import simugen.gui.abstracts.AbstractSimActivator;
import simugen.gui.interfaces.LoadedViewContext;
import simugen.gui.interfaces.ModelRunner;
import simugen.gui.views.OutputView;

public class SimActivator extends AbstractSimActivator {

	public SimActivator() {
		addLoadedViewContext(new LoadedViewContext<OutputView>() {

			@Override
			public void onLoad(OutputView view) {
				view.treeDatabase.getMenu().addMenuListener(new MenuListener() {

					@Override
					public void menuShown(MenuEvent e) {
						MenuItem item = new MenuItem(view.treeDatabase.getMenu(), SWT.PUSH);
						item.setText("Testor!");
					}

					@Override
					public void menuHidden(MenuEvent e) {
						
					}
				});
			}

			@Override
			public Class<OutputView> getViewClass() {
				return OutputView.class;
			}
		});
	}

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
