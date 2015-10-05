package stock.gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import stock.gui.views.StockListView;
import stock.scraper.builder.StockCompany;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{

	private final static String COMPANY_DATA = "company.data";

	private final Map<String, File> mapKeyToDataFile = new HashMap<>();

	private final List<StockCompany> listCompanies = new ArrayList<>();

	// The plug-in ID
	public static final String PLUGIN_ID = "StockGui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;

		mapKeyToDataFile.put(COMPANY_DATA, makeFile(COMPANY_DATA));

		File data = mapKeyToDataFile.get(COMPANY_DATA);

		if (data.exists())
		{
			listCompanies.addAll(loadData(COMPANY_DATA, StockCompany.class));
		}
	}

	private File makeFile(String filename)
	{
		File location = new File(Platform.getLocation().toFile(), "\\data\\");

		if (!location.exists())
		{
			location.mkdir();
		}

		return new File(location, filename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		
		saveData(COMPANY_DATA, listCompanies.toArray(new Serializable[0]));
		
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault()
	{
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path)
	{
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public List<StockCompany> getCompanies()
	{
		return listCompanies;
	}

	public void addCompany(StockCompany company)
	{
		listCompanies.add(company);

		for (IViewReference reference : this.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences())
		{
			final Object view = reference.getView(false);

			if (view != null && view instanceof StockListView)
			{
				StockListView listView = (StockListView) view;

				listView.addStockCompany(company);
			}
		}
	}

	private <T> List<T> loadData(String key, Class<T> t)
	{
		File file = mapKeyToDataFile.get(key);

		assert file.exists();

		FileReader fileReader = null;

		ByteArrayInputStream byt = null;

		ObjectInputStream reader = null;

		List<T> list = new ArrayList<>();

		try
		{
			fileReader = new FileReader(file);
			
			CharArrayWriter bufArray = new CharArrayWriter();
			
			while(fileReader.ready())
			{
				int c = fileReader.read();
				
				if(c != -1)
				{
					bufArray.append((char) c);
				}
			}
			
			char[] buffer = bufArray.toCharArray();
			
			byte[] array = Hex.decodeHex(buffer);

			byt = new ByteArrayInputStream(array);

			reader = new ObjectInputStream(byt);

			Object obj = reader.readObject();
			
			while (!(obj instanceof EOF))
			{
				T data = t.cast(obj);

				list.add(data);
				
				obj = reader.readObject();
			}
		}
		catch (IOException | DecoderException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (fileReader != null)
				{
					fileReader.close();
				}
				if (byt != null)
				{
					byt.close();
				}
				if (reader != null)
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return list;
	}

	private void saveData(String key, Serializable[] dataArray)
	{
		File file = mapKeyToDataFile.get(key);

		assert file.exists();

		FileWriter fileWriter = null;

		ByteArrayOutputStream byt = null;

		ObjectOutputStream writer = null;

		try
		{
			byt = new ByteArrayOutputStream();

			writer = new ObjectOutputStream(byt);

			for (Serializable data : dataArray)
			{
				writer.writeObject(data);
			}
			
			EOF eof = new EOF();
			
			writer.writeObject(eof);

			byte[] array = byt.toByteArray();

			String hexString = Hex.encodeHexString(array);

			fileWriter = new FileWriter(file);

			fileWriter.write(hexString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (fileWriter != null)
				{
					fileWriter.close();
				}
				if (byt != null)
				{
					byt.close();
				}
				if (writer != null)
				{
					writer.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
