package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class ConfirmPanel extends JPanel {

	private JTextField PaymentSum;
	private JLabel TotalSum;
	private JLabel ChangeAmount;
	private SalesSystemModel model;
	private JLabel EnterSum;
	private JLabel Empty;

	public ConfirmPanel(SalesSystemModel model) {
		this.model = model;

		setLayout(new GridLayout());

		add(drawDialogPane());
		setEnabled(false);
	}

	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		//setSize(300, 300);
		panel.setLayout(new GridLayout(3, 2));

		panel.setBorder(BorderFactory.createTitledBorder("Payment"));

		JTextField PaymentSum = new JTextField();
		JLabel TotalSum = new JLabel("Total sum"); // + mingi v2rk, kust v6tab
													// kogu summa.
		JLabel ChangeAmount = new JLabel("Sum: 500  Change: "); // Siia tuleb see, mis
														// makstakse tagasi.

		JButton ConfirmButton = new JButton("Confirm");
		JButton CancelButton = new JButton("Cancel");
		JLabel EnterSum = new JLabel("Payment sum: ");
		JLabel Empty = new JLabel("");

		// Fill the change amout field when payment amount changes.
		PaymentSum.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				// Arvuta tagasimakse raha.
			}

			public void removeUpdate(DocumentEvent e) {
				// Arvuta tagasimakse raha.
			}

			public void changedUpdate(DocumentEvent e) {
				// Arvuta tagasimakse raha.
			}

		});

		panel.add(EnterSum);
		panel.add(PaymentSum);
		panel.add(ChangeAmount);
		panel.add(Empty);
		panel.add(ConfirmButton);
		panel.add(CancelButton);

		return panel;
	}
}
