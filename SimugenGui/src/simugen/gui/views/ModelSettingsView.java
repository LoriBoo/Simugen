package simugen.gui.views;

import javax.inject.Inject;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import simugen.gui.Activator;

public class ModelSettingsView extends ViewPart {
	public static final String ID = "TestGui.ModelSettingsView";

	@Inject
	IWorkbench workbench;

	@Override
	public void createPartControl(Composite parent) {
		String location = Activator.getDefault().getOutputLocation();

		Composite canvas = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		layout.marginTop = 10;
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 3;
		layout.verticalSpacing = 10;

		canvas.setLayout(layout);

		Label labelOutputLocation = new Label(canvas, SWT.SINGLE);

		labelOutputLocation.setText("Output Location:");

		GridData gridData = new GridData();

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.TOP;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = false;

		labelOutputLocation.setLayoutData(gridData);

		Label textOutputLocation = new Label(canvas, SWT.SINGLE | SWT.BORDER);

		textOutputLocation.setText(location);

		gridData = new GridData();

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.TOP;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = true;

		textOutputLocation.setLayoutData(gridData);

		Button saveSettings = new Button(canvas, SWT.PUSH);

		saveSettings.setText("Change");

		gridData = new GridData();

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.TOP;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = false;

		saveSettings.setLayoutData(gridData);

		saveSettings.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				DirectoryDialog dialog = new DirectoryDialog(parent.getShell());

				dialog.setFilterPath(textOutputLocation.getText());

				String output = dialog.open();

				if (output != null) {
					output = output.replace("\\", "/") + "/";

					System.out.println(output);

					textOutputLocation.setText(output);
					Activator.getDefault().getModelEngine().setOutputLocation(output);

					IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

					preferences.setValue("OutputLocation", output);
				}
			}
		});
	}

	@Override
	public void setFocus() {

	}
}