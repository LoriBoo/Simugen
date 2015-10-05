package stock.gui.utilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import stock.scraper.builder.StockCompany;
import stock.scraper.builder.factory.StockBuilderFactory;

public class StockCompanyDialogBox extends Dialog
{
	private Shell dialog;

	private StockCompany company = null;

	public StockCompanyDialogBox(Shell parent)
	{
		super(parent);

		dialog = new Shell(parent, SWT.DIALOG_TRIM);
	}

	public StockCompany open()
	{
		dialog.setText("Add Company by Stock Token");

		dialog.setSize(300, 300);

		dialog.setLayout(new FillLayout());

		Composite parent = new Composite(dialog, SWT.NONE);

		parent.setLayout(new GridLayout(2, false));

		GridData data = new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1);

		Label label = new Label(parent, SWT.SINGLE);

		label.setText("Company Token");

		label.setLayoutData(data);

		final Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);

		data = new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1);

		data.minimumHeight = 25;

		text.setLayoutData(data);

		Button okay = new Button(parent, SWT.PUSH);

		okay.setText("OK");

		data = new GridData(SWT.FILL, SWT.DEFAULT, true, false);

		data.minimumWidth = 100;

		okay.setLayoutData(data);

		okay.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				company = StockBuilderFactory.INSTANCE
						.createCompany(text.getText());

				dialog.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}
		});

		dialog.layout();

		dialog.setSize(dialog.computeSize(300, SWT.DEFAULT));

		dialog.open();

		while (!dialog.isDisposed())
		{
			if (!dialog.getDisplay().readAndDispatch())
			{
				dialog.getDisplay().sleep();
			}
		}

		return company;
	}

}
