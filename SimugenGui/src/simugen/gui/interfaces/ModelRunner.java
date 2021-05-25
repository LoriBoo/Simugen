package simugen.gui.interfaces;

import java.lang.reflect.InvocationTargetException;

public interface ModelRunner {
	public void execute() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
