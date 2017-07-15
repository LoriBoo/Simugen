package stock.gui.editors;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.PointStyle;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import simugen.core.defaults.DefaultSimEngine;
import simugen.core.defaults.EngineBatchFinishEvent;
import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;
import simugen.core.rng.EmpiricalGenerator;
import stock.gui.utilities.StockGuiUtils;
import stock.gui.utilities.StockNumberOfRuns;
import stock.model.StockModel;
import stock.model.components.StockEventListener;
import stock.model.data.StockData;
import stock.scraper.builder.StockAddDataEvent;
import stock.scraper.builder.StockCompany;
import stock.scraper.builder.StockCompanyListener;
import stock.scraper.utils.StockScraperUtils;

public class StockCompanyModelEditor extends EditorPart
		implements SimEventListener
{
	private StockCompany company;

	private Map<Long, BigDecimal> percentages;

	private EmpiricalGenerator generator;

	private XYGraph graph;

	private Trace actualsTrace;

	private Trace medTrace;

	private Trace minTrace;

	private Trace maxTrace;

	private Button runButton;

	private StockData stockData;

	private int pRuns;

	private int pDays;

	private void setup()
	{
		pRuns = 30;

		pDays = 50;

		percentages = StockScraperUtils
				.getPercentChange(company.getHistorical());

		generator = StockGuiUtils.createDistribution(percentages);

		this.addListenerObject(new StockCompanyListener()
		{
			@Override
			public void processStockEvent(StockAddDataEvent evt)
			{
				if (evt.isFor(company))
				{
					StockGuiUtils.addEntry(company, generator, percentages,
							evt.getSource());
				}

				generator.computeProbabilities();
			}
		});

		generator.computeProbabilities();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException
	{
		setSite(site);

		setInput(input);

		company = input.getAdapter(StockCompany.class);

		setPartName(company.getMarket().concat(":").concat(company.getToken()));
	}

	@Override
	public void createPartControl(Composite base)
	{
		setup();

		base.setLayout(new FillLayout());

		Composite parent = new Composite(base, SWT.NONE);

		parent.setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		parent.setLayout(new GridLayout(1, true));

		createModelControls(parent);

		final Canvas can = new Canvas(parent, SWT.DEFAULT);

		can.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		parent.layout();

		final LightweightSystem system = new LightweightSystem(can);

		graph = new XYGraph();

		graph.setTitle(company.getCompanyName());

		system.setContents(graph);

		graph.primaryXAxis.setTitle("Days");

		graph.primaryXAxis.setDateEnabled(true);

		graph.primaryXAxis.setTimeUnit(Calendar.DATE);

		graph.primaryYAxis.setTitle("Stock Value");

		CircularBufferDataProvider bufferData = new CircularBufferDataProvider(
				true);

		setup(bufferData);

		actualsTrace = new Trace("Actual", graph.primaryXAxis,
				graph.primaryYAxis, bufferData);

		actualsTrace.setPointStyle(PointStyle.XCROSS);

		graph.addTrace(actualsTrace);

		graph.performAutoScale();

		parent.layout();
	}

	private void createModelControls(Composite base)
	{
		Composite parent = new Composite(base, SWT.NONE);

		// Settable constants:
		// days
		// replications
		parent.setLayout(new GridLayout(3, true));

		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label label = new Label(parent, SWT.NONE);

		label.setText("Days to Simulate:");

		label.setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, true, false, 2, 1));

		final Text txtDays = new Text(parent, SWT.SINGLE | SWT.BORDER);

		txtDays.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		txtDays.setText(String.valueOf(this.pDays));

		label = new Label(parent, SWT.NONE);

		label.setText("Number of Runs:");

		label.setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, true, false, 2, 1));

		final Text txtRuns = new Text(parent, SWT.SINGLE | SWT.BORDER);

		txtRuns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		txtRuns.setText(String.valueOf(this.pRuns));

		final Button numberOfReps = new Button(parent, SWT.PUSH);

		numberOfReps.setText("Number of Runs Test");

		numberOfReps
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		numberOfReps.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				StockModel model = new StockModel(generator,
						company.getCompanyName());

				StockNumberOfRuns runs = new StockNumberOfRuns(model,
						company.getLatest());

				if (runs.open())
				{
					setDays(runs.days);

					setRuns(runs.finalRuns);

					txtDays.setText(String.valueOf(pDays));

					txtRuns.setText(String.valueOf(pRuns));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}
		});

		runButton = new Button(parent, SWT.PUSH);

		runButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		runButton.setText("Run model");

		runButton.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				runModel();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
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
					if (!value.isEmpty())
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
				finally
				{
					if (value.isEmpty())
					{
						runButton.setEnabled(!e.doit);
					}
					else
					{
						runButton.setEnabled(true);
					}

					if (e.doit && !value.isEmpty())
					{
						int val = Integer.valueOf(value);

						if (e.getSource().equals(txtRuns))
						{
							setRuns(val);
						}
						else
						{
							setDays(val);
						}
					}
				}
			}
		};

		txtDays.addVerifyListener(verifyInteger);

		txtRuns.addVerifyListener(verifyInteger);
	}

	protected void runModel()
	{
		runButton.setText("Running...");

		runButton.setEnabled(false);

		SimEngine engine = new DefaultSimEngine();

		stockData = new StockData(Calendar.getInstance().getTime(),
				company.getLatest());

		StockEventListener listener = new StockEventListener(stockData);

		StockModel model = new StockModel(generator, company.getCompanyName());

		model.setComputations(pDays);

		engine.setRuns(pRuns);

		engine.setModel(model);

		engine.addEventListener(listener);

		// engine.setRuns(getRuns());

		engine.setLoggingStyle(LoggingStyle.DATA);

		engine.addEventListener(StockCompanyModelEditor.this);

		engine.start();

		Display display = getSite().getShell().getDisplay();

		while (engine.isRunning())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}

	protected void setDays(int val)
	{
		pDays = val;
	}

	protected void setRuns(int val)
	{
		pRuns = val;
	}

	public void setup(CircularBufferDataProvider buffer)
	{
		buffer.setChronological(true);

		buffer.setXAxisDateEnabled(true);

		buffer.setConcatenate_data(true);

		int iter = company.getHistorical().keySet().size();

		for (Entry<Long, BigDecimal> e : company.getHistorical().entrySet())
		{
			iter--;

			if (iter <= 30)
			{
				buffer.setCurrentYData(e.getValue().doubleValue(), e.getKey());
			}
		}
	}

	@Override
	public boolean isDirty()
	{
		return false;
	}

	@Override
	public boolean isSaveAsAllowed()
	{
		return false;
	}

	@Override
	public void setFocus()
	{

	}

	@Override
	public void doSave(IProgressMonitor monitor)
	{

	}

	@Override
	public void doSaveAs()
	{

	}

	@Override
	public void processEvent(SimEvent event)
	{
		if (event instanceof EngineBatchFinishEvent)
		{
			runButton.setEnabled(true);

			runButton.setText("Run model");

			if (minTrace != null)
			{
				assert maxTrace != null;
				assert medTrace != null;

				graph.removeTrace(maxTrace);

				graph.removeTrace(medTrace);

				graph.removeTrace(minTrace);

			}

			minTrace = new Trace("Min", graph.primaryXAxis, graph.primaryYAxis,
					stockData.getMinBuffer());

			maxTrace = new Trace("Max", graph.primaryXAxis, graph.primaryYAxis,
					stockData.getMaximumBuffer());

			medTrace = new Trace("Median", graph.primaryXAxis,
					graph.primaryYAxis, stockData.getMedianBuffer());

			minTrace.setTraceColor(
					Display.getDefault().getSystemColor(SWT.COLOR_RED));

			maxTrace.setTraceColor(
					Display.getDefault().getSystemColor(SWT.COLOR_GREEN));

			medTrace.setTraceColor(
					Display.getDefault().getSystemColor(SWT.COLOR_YELLOW));

			graph.addTrace(minTrace);

			graph.addTrace(maxTrace);

			graph.addTrace(medTrace);

			graph.performAutoScale();
		}
	}

}
