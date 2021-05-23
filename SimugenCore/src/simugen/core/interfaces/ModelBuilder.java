package simugen.core.interfaces;

import java.lang.reflect.InvocationTargetException;

public interface ModelBuilder
{
	public Model buildModel()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
