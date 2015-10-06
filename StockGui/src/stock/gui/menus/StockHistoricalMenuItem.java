package stock.gui.menus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import stock.scraper.builder.StockCompany;
import stock.scraper.builder.YahooUrlBuilder;
import stock.scraper.builder.factory.StockBuilderFactory;
import stock.scraper.utils.StockScraperUtils;

public class StockHistoricalMenuItem
{
	private final MenuItem item;
	
	private final StockCompany selected;

	public StockHistoricalMenuItem(Menu parent, int style, StockCompany company)
	{
		item = new MenuItem(parent, style);

		selected = company;

		item.setText("Build historical data for " + selected.getToken());

		item.addSelectionListener(listener);
	}
	
	public MenuItem getItem()
	{
		return item;
	}

	private final SelectionListener listener = new SelectionListener()
	{
		private final IInputValidator validator = new IInputValidator()
		{
			final String[] patterns =
			{ "[\\d]{4}-[\\d]{2}-[\\d]{2}", "[\\d]{4}-[\\d]{1}-[\\d]{2}",
					"[\\d]{4}-[\\d]{2}-[\\d]{1}",
					"[\\d]{4}-[\\d]{1}-[\\d]{1}" };

			final Pattern[] pat =
			{ Pattern.compile(patterns[0]), Pattern.compile(patterns[1]),
					Pattern.compile(patterns[2]),
					Pattern.compile(patterns[3]) };

			@Override
			public String isValid(String newText)
			{
				final Matcher[] matcher =
				{ pat[0].matcher(newText), pat[1].matcher(newText),
						pat[2].matcher(newText), pat[3].matcher(newText) };

				for (Matcher match : matcher)
				{
					if (match.matches())
					{
						try
						{
							new SimpleDateFormat("yyyy-MM-dd").parse(newText);
						}
						catch (ParseException e)
						{
							return "invalid";
						}
						return null;
					}
				}

				return "Enter a valid date.";
			}
		};

		@Override
		public void widgetSelected(SelectionEvent e)
		{
			Shell shell = Display.getDefault().getActiveShell();

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			Date start = null;

			Date end = null;

			InputDialog dialog = new InputDialog(shell, "Start Date",
					"Enter start date in 'yyyy-MM-dd' format", "", validator);

			int rtn = dialog.open();

			if (rtn == InputDialog.OK)
			{
				try
				{
					start = format.parse(dialog.getValue());
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				return;
			}

			String today = format.format(Calendar.getInstance().getTime());

			dialog = new InputDialog(shell, "End Date",
					"Enter end date in 'yyyy-MM-dd' format", today, validator);

			rtn = dialog.open();

			if (rtn == InputDialog.OK)
			{
				try
				{
					end = format.parse(dialog.getValue());
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				return;
			}

			if (!start.before(end) && !start.equals(end))
			{
				throw new IllegalStateException(
						"Start date must be before or at end date.");
			}

			YahooUrlBuilder builder = StockBuilderFactory.INSTANCE
					.createBuilder(selected, start, end);

			IRunnableWithProgress run = new IRunnableWithProgress()
			{
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException
				{
					Map<Date, BigDecimal> hist = null;
					try
					{
						hist = StockScraperUtils.getHistorical(builder);
					}
					catch (IOException | ParseException e)
					{
						e.printStackTrace();
					}

					selected.clearHistorical();

					for (Entry<Date, BigDecimal> entry : hist.entrySet())
					{
						selected.addHistorical(entry.getKey(),
								entry.getValue());
					}
				}
			};

			NullProgressMonitor npm = new NullProgressMonitor();

			try
			{
				run.run(npm);
			}
			catch (InvocationTargetException e1)
			{
				e1.printStackTrace();
			}
			catch (InterruptedException e1)
			{
				selected.clearHistorical();
				e1.printStackTrace();
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e)
		{
		}
	};

}
