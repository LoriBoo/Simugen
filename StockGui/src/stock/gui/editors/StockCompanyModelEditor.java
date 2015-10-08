package stock.gui.editors;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.PointStyle;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import simugen.core.rng.EmpiricalGenerator;
import stock.gui.utilities.StockGuiUtils;
import stock.scraper.builder.StockAddDataEvent;
import stock.scraper.builder.StockCompany;
import stock.scraper.builder.StockCompanyListener;
import stock.scraper.utils.StockScraperUtils;

public class StockCompanyModelEditor extends EditorPart
{
	private StockCompany company;
	
	private Map<Date, BigDecimal> percentages;
	
	private EmpiricalGenerator generator;

	private XYGraph graph;

	private Trace actualsTrace;
	
	public void setup()
	{
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
			}
		});
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
	public void createPartControl(Composite parent)
	{		
		parent.setLayout(new FillLayout());
		
		final Canvas can = new Canvas(parent, SWT.DEFAULT);

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
	
	public void setup(CircularBufferDataProvider buffer)
	{
		buffer.setChronological(true);

		buffer.setXAxisDateEnabled(true);

		buffer.setConcatenate_data(true);

		int iter = company.getHistorical().keySet().size();

		for (Entry<Date, BigDecimal> e : company.getHistorical().entrySet())
		{
			iter--;

			if (iter <= 30)
			{
				buffer.setCurrentYData(e.getValue().doubleValue(),
						e.getKey().getTime());
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

}
