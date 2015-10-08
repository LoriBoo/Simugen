package stock.gui.views;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.PointStyle;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import simugen.core.rng.EmpiricalGenerator;
import stock.gui.Activator;
import stock.gui.utilities.StockGuiUtils;
import stock.scraper.builder.StockAddDataEvent;
import stock.scraper.builder.StockCompany;
import stock.scraper.builder.StockCompanyListener;
import stock.scraper.utils.StockScraperUtils;

public class StockModelView extends ViewPart
{
	private StockCompany company;

	private EmpiricalGenerator generator;

	private Map<Date, BigDecimal> percentages;

	private XYGraph graph;

	public void setCompany(StockCompany company)
	{
		this.company = company;

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

	protected EmpiricalGenerator getGenerator()
	{
		if (!generator.isReady())
		{
			generator.computeProbabilities();
		}
		return generator;
	}

	@Override
	public void createPartControl(Composite parent)
	{
		StockCompany company = Activator.getDefault().getCompanies().get(0);

		setCompany(company);

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

		Trace trace = new Trace("Actual", graph.primaryXAxis,
				graph.primaryYAxis, bufferData);

		trace.setPointStyle(PointStyle.XCROSS);

		graph.addTrace(trace);

		graph.performAutoScale();
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
	public void setFocus()
	{

	}

	public StockCompany getCompany()
	{
		return company;
	}

}