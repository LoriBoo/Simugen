package simugen.gui.interfaces;

import java.util.List;

import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.widgets.Control;

public interface MenuListenerViewPart {
	public void addMenuListener(Control control, MenuListener listener);
	
	public List<MenuListener> getListeners(Control control);
}
