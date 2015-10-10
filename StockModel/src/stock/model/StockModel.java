package stock.model;

import java.util.ArrayList;
import java.util.List;

import simugen.core.abstracts.AbstractSimModel;
import simugen.core.interfaces.SimModel;
import simugen.core.rng.EmpiricalGenerator;
import stock.model.components.StockProcess;
import stock.model.components.StockProcessor;

public class StockModel extends AbstractSimModel
{
	EmpiricalGenerator historical;

	String company;

	int comp;

	double value;

	List<Double> listValues = new ArrayList<>();

	public StockModel(EmpiricalGenerator data, String company)
	{
		this.company = company;

		this.historical = data;

		assert (data != null && company != null);
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
	public SimModel getCopy()
	{
		StockModel copy = new StockModel(historical, company);

		copy.setComputations(comp);

		copy.setInitialValue(value);

		return copy;
	}

}
