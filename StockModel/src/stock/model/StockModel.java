package stock.model;

import java.util.ArrayList;
import java.util.List;

import simugen.core.abstracts.AbstractSimModel;
import simugen.core.rng.EmpiricalGenerator;
import stock.model.components.StockEventListener;
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
		
		//TODO this shouldn't be in here. But it's fine for now.
		addListener(new StockEventListener());
	}

	@Override
	public void onShutdown()
	{
		// TODO Auto-generated method stub

	}

}
