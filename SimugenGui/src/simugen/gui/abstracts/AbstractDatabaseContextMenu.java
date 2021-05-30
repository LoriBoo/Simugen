package simugen.gui.abstracts;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;

import simugen.gui.views.OutputView;

public abstract class AbstractDatabaseContextMenu extends AbstractContextMenu<OutputView> {

	public AbstractDatabaseContextMenu(OutputView view) {
		super(view);
	}

	protected abstract String getQuery(OutputView view);

	protected abstract String getLabel(OutputView view);

	@Override
	protected void doMenuShown(MenuEvent e, OutputView view) {
		Menu menu = (Menu) e.getSource();

		for (MenuItem dispose : menu.getItems()) {
			dispose.dispose();
		}

		MenuItem newItem = new MenuItem(menu, SWT.NONE);

		// newItem.setText("SELECT * FROM " +
		// view.treeDatabase.getSelection()[0].getText() + ";");

		newItem.setText(getLabel(view));

		newItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem sub = view.treeDatabase.getSelection()[0];

				String path = (String) sub.getData("path");

				String url = "jdbc:sqlite:" + path;

				String sql = getQuery(view);

				if (sql != null) {

					try {
						view.sqlTableFiller.fill(url, sql);
					} catch (SQLException | ClassNotFoundException exp) {
						System.out.println(exp.getMessage());
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
	}

	@Override
	protected void doMenuHidden(MenuEvent e, OutputView View) {

	}

}
