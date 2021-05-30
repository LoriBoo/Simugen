package simugen.gui.interfaces;

import org.eclipse.ui.part.ViewPart;

public interface LoadedViewContext<T extends ViewPart> {
	public void onLoad(T view);

	public Class<T> getViewClass();
}
