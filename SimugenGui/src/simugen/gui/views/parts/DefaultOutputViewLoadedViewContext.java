package simugen.gui.views.parts;

import org.eclipse.swt.widgets.Menu;

import simugen.gui.interfaces.LoadedViewContext;
import simugen.gui.views.OutputView;

public class DefaultOutputViewLoadedViewContext implements LoadedViewContext<OutputView> {

	@Override
	public void onLoad(OutputView view) {
		Menu menu = new Menu(view.treeDatabase);

		DefaultDatabaseContextMenu contextMenu = new DefaultDatabaseContextMenu(view);

		menu.addMenuListener(contextMenu);

		view.treeDatabase.setMenu(menu);
	}

	@Override
	public Class<OutputView> getViewClass() {
		return OutputView.class;
	}

}
