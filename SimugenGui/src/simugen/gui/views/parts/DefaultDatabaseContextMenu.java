package simugen.gui.views.parts;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.TreeItem;

import simugen.gui.abstracts.AbstractDatabaseContextMenu;
import simugen.gui.views.OutputView;

public class DefaultDatabaseContextMenu extends AbstractDatabaseContextMenu {

	public DefaultDatabaseContextMenu(OutputView view) {
		super(view);
	}

	@Override
	protected String getQuery(OutputView view) {
		TreeItem item = view.treeDatabase.getSelection()[0];
		TreeItem root = view.treeDatabase.getItem(0);

		if (root.equals(item)) {
			InputDialog dialog = new InputDialog(view.canvas.getShell(), "Input Query", "Please input SQL Query", "",
					null);

			int result = dialog.open();

			String query = dialog.getValue();

			if (query != "" && result == InputDialog.OK) {

				return query;
			} else {
				return null;
			}
		} else {
			TreeItem sub = view.treeDatabase.getSelection()[0];

			return (String) sub.getData("query");
		}
	}

	@Override
	protected String getLabel(OutputView view) {
		TreeItem item = view.treeDatabase.getSelection()[0];
		TreeItem root = view.treeDatabase.getItem(0);

		if (root.equals(item)) {
			return "Run query...";
		} else {
			return getQuery(view);
		}
	}
}
