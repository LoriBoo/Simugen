package stock.gui.utilities;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ModalSelector<T>
{
	private final Map<String, T> $map;

	private final Shell $shell;

	private Combo $combo;

	private T $return = null;

	private enum Selected
	{
		OKAY, CANCEL
	}

	private SelectionListener comboListener = new SelectionListener()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			$okay.setEnabled(!$combo.getText().equals($message));
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e)
		{
		}
	};

	private SelectionListener buttonListener = new SelectionListener()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			if (e.widget.getData().equals(Selected.OKAY))
			{
				$return = $map.get($combo.getText());

				assert $return != null;
			}
			else if (e.widget.getData().equals(Selected.CANCEL))
			{
				$return = null;
			}
			else
			{
				throw new IllegalStateException(
						"Something went wrong in ModalSelector");
			}

			$shell.dispose();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e)
		{
		}
	};

	private Composite $parent;

	private String $message = "Please select an item from the list.";

	private Button $okay;

	public ModalSelector(Map<String, T> map)
	{
		$map = map;

		$shell = new Shell(Display.getDefault(),
				SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		createControls();
	}

	public void setText(String text)
	{
		$shell.setText(text);
	}

	public void setMessage(String message)
	{
		$combo.remove(0);
		
		$message = message;
		
		$combo.add($message, 0);
		
		$combo.setText($message);
	}

	private void createControls()
	{
		$shell.setLayout(new FillLayout());

		$parent = new Composite($shell, SWT.NONE);

		$parent.setLayout(new GridLayout(3, true));

		$combo = new Combo($parent, SWT.READ_ONLY);

		$combo.setLayoutData(makeGridData(3, 1));

		$combo.setItems(
				$map.keySet().toArray(new String[$map.keySet().size()]));

		$combo.add($message, 0);
		
		$combo.setText($message);
		
		makeSpace(1, 1);

		$okay = new Button($parent, SWT.PUSH);

		$okay.setLayoutData(makeGridData(1, 1));

		Button cancel = new Button($parent, SWT.PUSH);

		cancel.setLayoutData(makeGridData(1, 1));

		$okay.setEnabled(false);

		$okay.addSelectionListener(buttonListener);

		$okay.setText("Okay");

		$okay.setData(Selected.OKAY);

		cancel.addSelectionListener(buttonListener);

		cancel.setText("Cancel");

		cancel.setData(Selected.CANCEL);

		$shell.setSize($shell.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		$combo.addSelectionListener(comboListener);
	}

	public T open()
	{
		$shell.open();

		while (!$shell.isDisposed())
		{
			if (!$shell.getDisplay().readAndDispatch())
			{
				$shell.getDisplay().sleep();
			}
		}

		return $return;
	}

	private GridData makeGridData(int hSpan, int vSpan)
	{
		return new GridData(SWT.FILL, SWT.DEFAULT, true, false, hSpan, vSpan);
	}

	private void makeSpace(int hSpan, int vSpan)
	{
		Label label = new Label($parent, SWT.NONE);

		label.setLayoutData(makeGridData(hSpan, vSpan));
	}

}
