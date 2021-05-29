package simugen.gui.views;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import simugen.core.sql.SqlUtils;
import simugen.gui.SimActivator;
import simugen.gui.interfaces.RefreshableView;

public class OutputView extends ViewPart implements RefreshableView {
	public static final String ID = "TestGui.OutputView";

	@Inject
	IWorkbench workbench;

	private Combo comboDatabases;

	private String location;

	@Override
	public void dispose() {
		SimActivator.getDefault().removeRefreshableView(this);
		super.dispose();
	}

	@Override
	public void createPartControl(Composite parent) {
		SimActivator.getDefault().addRefreshableView(this);

		location = SimActivator.getDefault().getOutputLocation();

		Composite canvas = new Composite(parent, SWT.BORDER);

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

		Tree treeDatabase = new Tree(canvas, SWT.BORDER);

		gridData = new GridData();

		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalSpan = 2;
		gridData.widthHint = 100;

		treeDatabase.setLayoutData(gridData);

		final Menu menu = new Menu(treeDatabase);

		treeDatabase.setMenu(menu);

		treeDatabase.addMenuDetectListener(new MenuDetectListener() {

			@Override
			public void menuDetected(MenuDetectEvent e) {
				TreeItem item = treeDatabase.getSelection()[0];
				TreeItem root = treeDatabase.getItem(0);

				if (item.equals(root)) {
					e.doit = false;
				}
			}
		});

		Table table = new Table(canvas, SWT.BORDER);

		gridData = new GridData();

		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		table.setLayoutData(gridData);

		saveSettings.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				fillDatabase(treeDatabase, location + comboDatabases.getText(), table);
			}

		});

		menu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent e) {

				MenuItem[] items = menu.getItems();

				for (int i = 0; i < items.length; i++) {
					items[i].dispose();
				}

				MenuItem newItem = new MenuItem(menu, SWT.NONE);

				newItem.setText("SELECT * FROM  " + treeDatabase.getSelection()[0].getText() + ";");

				newItem.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						TreeItem sub = treeDatabase.getSelection()[0];

						String path = (String) sub.getData("path");

						String url = "jdbc:sqlite:" + path;

						String sql = (String) sub.getData("query");

						if (sql != null) {

							try {

								while (table.getColumnCount() > 0) {
									table.getColumns()[0].dispose();
								}

								Class.forName("org.sqlite.JDBC");

								Connection connection = DriverManager.getConnection(url);

								Statement stmt = connection.createStatement();

								stmt.execute(sql);

								ResultSet set = stmt.getResultSet();

								ResultSetMetaData rsmd = set.getMetaData();

								int columnCount = rsmd.getColumnCount();

								table.clearAll();

								for (TableItem tableItem : table.getItems()) {
									tableItem.dispose();
								}

								for (int i = 1; i <= columnCount; i++) {
									String name = rsmd.getColumnName(i);

									TableColumn col = new TableColumn(table, SWT.CENTER);

									col.setText(name);

									col.setWidth(100);

									table.setHeaderVisible(true);
								}

								while (set.next()) {
									String[] values = new String[columnCount];

									for (int i = 1; i <= columnCount; i++) {
										values[i - 1] = SqlUtils.getString(rsmd, set, i);
									}

									TableItem tableItem = new TableItem(table, SWT.NONE);

									tableItem.setText(values);
								}

								connection.close();
							} catch (SQLException | ClassNotFoundException exp) {
								System.out.println(exp.getMessage());
							}
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub

					}
				});
			}

		});
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
