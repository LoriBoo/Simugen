package simugen.gui.abstracts;

import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.ui.part.ViewPart;

public abstract class AbstractContextMenu<T extends ViewPart> implements MenuListener {

	private T view;

	public AbstractContextMenu(T view) {
		this.view = view;
	}

	protected abstract void doMenuShown(MenuEvent e, T view);
	
	protected abstract void doMenuHidden(MenuEvent e, T view);

	@Override
	public void menuHidden(MenuEvent e) {
		doMenuHidden(e, view);
	}

	@Override
	public void menuShown(MenuEvent e) {
		doMenuShown(e, view);
	}

}
