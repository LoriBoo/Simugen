package simugen.gui.views;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import simugen.core.defaults.DefaultConsoleListener;
import simugen.gui.Activator;

public class ConsoleView extends ViewPart {
	public static final String ID = "TestGui.view";

	@Inject
	IWorkbench workbench;

	private Text console;

	@Override
	public void createPartControl(Composite parent) {
		console = new Text(parent, SWT.MULTI | SWT.V_SCROLL);

		console.setEditable(false);

		OutputStream out = new OutputStream() {
			public void write(int b) throws IOException {
				console.append(String.valueOf((char) b));
			}

			public void write(byte[] b, int off, int len) {
				console.append(new String(b, off, len));
			}
		};

		PrintStream ps = new PrintStream(out, true);

		// SimActivator.getDefault().getModelEngine().setStreamOut(ps);
		Activator.getDefault().getModelEngine().addListener(new DefaultConsoleListener(ps));
	}

	@Override
	public void setFocus() {

	}
}