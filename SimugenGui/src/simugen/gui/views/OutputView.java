package simugen.gui.views;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import simugen.gui.Activator;
import simugen.gui.interfaces.RefreshableView;
import simugen.gui.views.utils.SqlTableFiller;

public class OutputView extends ViewPart implements RefreshableView {
	public static final String ID = "TestGui.OutputView";

	@Inject
	IWorkbench workbench;

	public Combo comboDatabases;

	public String location;

	public Table table;

	public Tree treeDatabase;

	public Composite canvas;

	public SqlTableFiller sqlTableFiller;

	@Override
	public void dispose() {
		Activator.getDefault().removeRefreshableView(this);
		super.dispose();
	}

	@Override
	public void createPartControl(Composite parent) {
		Activator.getDefault().addRefreshableView(this);

		location = Activator.getDefault().getOutputLocation();

		canvas = new Composite(parent, SWT.BORDER);

		GridLayout layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		layout.marginTop = 10;
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 4;
		layout.verticalSpacing = 10;

		canvas.setLayout(layout);

		Label labelOutputLocation = new Label(canvas, SWT.SINGLE);

		labelOutputLocation.setText("Batch:");

		GridData gridData = new GridData();

		gridData.horizontalAlignment = SWT.RIGHT;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = false;

		labelOutputLocation.setLayoutData(gridData);

		// Label textOutputLocation = new Label(canvas, SWT.SINGLE | SWT.BORDER);

		comboDatabases = new Combo(canvas, SWT.DROP_DOWN | SWT.READ_ONLY);

		fillComboBox();
		// textOutputLocation.setText(location);

		gridData = new GridData();

		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		comboDatabases.setLayoutData(gridData);

		Button saveSettings = new Button(canvas, SWT.PUSH);

		saveSettings.setText("Set");

		gridData = new GridData();

		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = false;

		saveSettings.setLayoutData(gridData);

		treeDatabase = new Tree(canvas, SWT.BORDER);

		gridData = new GridData();

		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalSpan = 2;
		gridData.widthHint = 100;

		treeDatabase.setLayoutData(gridData);

		// final Menu menu = new Menu(treeDatabase);

		// treeDatabase.setMenu(menu);

		treeDatabase.addMenuDetectListener(new MenuDetectListener() {

			@Override
			public void menuDetected(MenuDetectEvent e) {
				e.doit = treeDatabase.getItemCount() > 0;
			}
		});

		table = new Table(canvas, SWT.BORDER);

		gridData = new GridData();

		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		table.setLayoutData(gridData);

		sqlTableFiller = new SqlTableFiller(table);

		saveSettings.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				fillDatabase(treeDatabase, location + comboDatabases.getText(), table);
			}

		});

		// DefaultDatabaseContextMenu contextMenu = new
		// DefaultDatabaseContextMenu(this);

		// menu.addMenuListener(contextMenu);

		Activator.getDefault().<OutputView>onViewLoaded(this);
	}

	private void fillComboBox() {
		comboDatabases.removeAll();

		File file = new File(location);

		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});

		for (String dir : directories) {
			comboDatabases.add(dir);
		}
	}

	private void fillDatabase(Tree tree, String location, Table table) {
		tree.clearAll(true);

		File file = new File(location);

		String[] files = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return name.endsWith(".db");
			}
		});

		for (String f : files) {
			TreeItem item = new TreeItem(tree, 0);
			item.setText(f);
			item.setData("path", location + "/" + f);

			fillTables(item, table);
		}
	}

	public void fillTables(TreeItem item, Table table) {
		String path = (String) item.getData("path");

		String url = "jdbc:sqlite:" + path;

		try {
			Class.forName("org.sqlite.JDBC");

			Connection connection = DriverManager.getConnection(url);

			DatabaseMetaData metaData = connection.getMetaData();

			String[] types = { "TABLE" };

			ResultSet tables = metaData.getTables(null, null, "%", types);
			while (tables.next()) {
				TreeItem sub = new TreeItem(item, SWT.NONE);

				sub.setText(tables.getString("TABLE_NAME"));

				sub.setData("path", path);

				String query = "SELECT * FROM " + tables.getString("TABLE_NAME") + ";";

				sub.setData("query", query);
			}
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void setFocus() {
		fillComboBox();
	}

	@Override
	public void refresh() {
		fillComboBox();
	}
}
