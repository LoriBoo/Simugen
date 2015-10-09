package stock.model;

import java.util.ArrayList;
import java.util.List;

import simugen.core.abstracts.AbstractSimModel;
import simugen.core.defaults.ModelFinishedEvent;
import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;
import simugen.core.interfaces.SimModel;
import simugen.core.rng.EmpiricalGenerator;
import stock.model.components.StockEvent;
import stock.model.components.StockProcess;
import stock.model.components.StockProcessor;
import stock.model.data.StockData;

public class StockModel extends AbstractSimModel implements SimEventListener
{
	EmpiricalGenerator historical;

	String company;

	int comp;

	double value;

	List<Double> listValues = new ArrayList<>();

	private final StockData stockData;

	public StockModel(EmpiricalGenerator data, String company,
			StockData stockData)
	{
		this.company = company;

		this.historical = data;

		this.stockData = stockData;

		addListener(this);

		assert (data != null && company != null && stockData != null);
	}

	public void setComputations(int computations)
	{
		this.comp = computations;
	}

	public void setInitialValue(double value)
	{
		this.value = value;
	}

	@Override
	public void startUp()
	{
		stockData.reset();

		final StockProcessor processor = new StockProcessor(comp);

		final StockProcess proc = new StockProcess(historical, value);

		processor.setProcess(proc);

		addComponent(processor);
	}

	@Override
	public void onShutdown()
	{
	}

	@Override
	public void processEvent(SimEvent event)
	{
		synchronized (stockData)
		{
			if (event instanceof StockEvent)
			{
				stockData.addValue(((StockEvent) event).getValue());
			}
		}
	}

	@Override
	public SimModel getCopy()
	{
		StockModel copy = new StockModel(historical, company, stockData);

		copy.setComputations(comp);

		copy.setInitialValue(value);

		return copy;
	}

}
