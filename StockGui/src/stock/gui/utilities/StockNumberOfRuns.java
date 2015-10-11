package stock.gui.utilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import stock.model.StockModel;

public class StockNumberOfRuns
{
	private final StockModel model;

	private double confidence = 0.998;

	private int samples = 10;

	public int days = 30;

	private int maxRuns = 100;

	private final double initialValue;

	private boolean okay;

	public int finalRuns;

	public StockNumberOfRuns(StockModel model, double initialValue)
	{
		this.model = model;

		this.initialValue = initialValue;
	}

	public boolean open()
	{
		okay = false;

		Shell shell = new Shell(Display.getDefault(),
				SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);

		shell.setLayout(new FillLayout());

		shell.setText("Number of Runs testing");

		Composite parent = new Composite(shell, SWT.NONE);

		parent.setLayout(new GridLayout(3, true));

		Label label = new Label(parent, SWT.NONE);

		label.setText("Confidence interval: ");

		label.setLayoutData(makeGridData(2));

		Text txtConfidence = new Text(parent, SWT.SINGLE | SWT.BORDER);

		txtConfidence.setText(String.valueOf(this.confidence));

		txtConfidence.setLayoutData(makeGridData(1));

		txtConfidence.addVerifyListener(new VerifyListener()
		{
			@Override
			public void verifyText(VerifyEvent e)
			{
				Text text = Text.class.cast(e.widget);

				String value = e.start == 0 ? e.text
						: text.getText().concat(e.text);

				try
				{
					if (!value.isEmpty())
					{
						if (Double.valueOf(value) >= 1d)
						{
							e.doit = false;
						}
					}
				}
				catch (NumberFormatException f)
				{
					// *GULP* eat it.
					e.doit = false;
				}
			}
		});

		final VerifyListener verifyInteger = new VerifyListener()
		{
			@Override
			public void verifyText(VerifyEvent e)
			{
				Text text = Text.class.cast(e.widget);

				String value = e.start == 0 ? e.text
						: text.getText().concat(e.text);

				try
				{
					if (!text.getText().isEmpty())
					{
						if (Integer.valueOf(value) < 0)
						{
							e.doit = false;
						}
					}
				}
				catch (NumberFormatException f)
				{
					// Yummy
					e.doit = false;
				}
			}
		};

		label = new Label(parent, SWT.NONE);

		label.setText("Sample Size");

		label.setLayoutData(makeGridData(2));

		Text txtSamples = new Text(parent, SWT.SINGLE | SWT.BORDER);

		txtSamples.addVerifyListener(verifyInteger);

		txtSamples.setText(String.valueOf(this.samples));

		txtSamples.setLayoutData(makeGridData(1));

		label = new Label(parent, SWT.NONE);

		label.setText("Days Simulated");

		label.setLayoutData(makeGridData(2));

		Text txtDays = new Text(parent, SWT.SINGLE | SWT.BORDER);

		txtDays.addVerifyListener(verifyInteger);

		txtDays.setText(String.valueOf(this.days));

		txtDays.setLayoutData(makeGridData(1));

		label = new Label(parent, SWT.NONE);

		label.setText("Maximum runs");

		label.setLayoutData(makeGridData(2));

		Text txtMaxRuns = new Text(parent, SWT.SINGLE | SWT.BORDER);

		txtMaxRuns.addVerifyListener(verifyInteger);

		txtMaxRuns.setText(String.valueOf(this.maxRuns));

		txtMaxRuns.setLayoutData(makeGridData(1));

		label = new Label(parent, SWT.NONE);

		label.setLayoutData(makeGridData(1));

		final Button ok = new Button(parent, SWT.PUSH);

		final Button cancel = new Button(parent, SWT.PUSH);

		ok.setText("Run test");

		ok.setLayoutData(makeGridData(1));

		ok.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				try
				{
					maxRuns = Integer.valueOf(txtMaxRuns.getText());
					days = Integer.valueOf(txtDays.getText());
					samples = Integer.valueOf(txtSamples.getText());
					confidence = Double.valueOf(txtConfidence.getText());

					finalRuns = StockGuiUtils.numberOfReps(model, initialValue,
							samples, days, maxRuns, confidence);

					okay = finalRuns < maxRuns;

					MessageBox box = new MessageBox(shell,
							SWT.APPLICATION_MODAL);

					box.setText("Results");

					String message = maxRuns == finalRuns
							? "Run limit was hit. Consider changing parameters."
							: "Runs for " + (confidence * 100d) + "% CI: "
									+ finalRuns;

					box.setMessage(message);

					box.open();
				}
				catch (Exception x)
				{

				}
				finally
				{
					shell.dispose();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}
		});

		cancel.setText("Cancel");

		cancel.setLayoutData(makeGridData(1));

		cancel.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				okay = false;
				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}
		});

		shell.setSize(shell.computeSize(300, SWT.DEFAULT));

		shell.open();

		while (!shell.isDisposed())
		{
			Display d = shell.getDisplay();

			if (!d.readAndDispatch())
			{
				d.sleep();
			}
		}

		return okay;
	}

	private GridData makeGridData(int span)
	{
		return new GridData(SWT.FILL, SWT.DEFAULT, true, false, span, 1);
	}
}
