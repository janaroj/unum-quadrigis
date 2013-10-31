package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;

public class ConfirmPanel extends JPanel {

	private JTextField PaymentSum;
	private JLabel TotalSum;
	private JLabel ChangeAmount;
	private SalesSystemModel model;
	private JLabel EnterSum;
	private JLabel Empty;
	private JButton ConfirmButton;
	private JPanel Display;

	public ConfirmPanel(SalesSystemModel model) {
		this.model = model;

		setLayout(new GridLayout());

		reDo();
		setEnabled(false);
	}

	private JComponent drawDialogPane() {
		// Create the panel
		Display = new JPanel(new GridLayout(3, 1));
		JLabel Pointless = new JLabel("");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		panel.setBorder(BorderFactory.createTitledBorder("Payment"));

		PaymentSum = new JTextField();
		JLabel TotalSum = new JLabel("Total sum");
		JLabel ChangeAmount = new JLabel("Sum: " + addItems() + "  Change: ");
		ConfirmButton = new JButton("Confirm");
		JButton CancelButton = new JButton("Cancel");
		JLabel EnterSum = new JLabel("Payment sum: ");
		Empty = new JLabel("");

		// Fill the change amount field when payment amount changes.
		PaymentSum.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				SetChangeText();
			}

			public void removeUpdate(DocumentEvent e) {
				SetChangeText();
			}

			public void changedUpdate(DocumentEvent e) {
				SetChangeText();
			}

		});
		// Returns to Purchase Tab after click.
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "Cancel") {
					PurchaseTab.submitConfirmCancelButtonClicked();

				}
			}
		});
		// Returns to Purchase Tab after click.
		ConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchaseTab.submitConfirmCancelButtonClicked();

			}
		});

		panel.add(EnterSum);
		panel.add(Display);
		Display.add(Pointless);
		Display.add(PaymentSum);
		panel.add(ChangeAmount);
		panel.add(Empty);
		panel.add(ConfirmButton);
		panel.add(CancelButton);

		return panel;
	}

	public double addItems() {
		int i = 0;
		double sum = 0.0;
		while (i < model.getCurrentPurchaseTableModel().getRowCount()) {
			sum += ((double) (model.getCurrentPurchaseTableModel().getValueAt(
					i, 2)) * (int) model.getCurrentPurchaseTableModel()
					.getValueAt(i, 3));
			i++;
		}
		return sum;

	}

	public void SetChangeText() {
		Empty.setText(PaymentSum.getText());
		if (Empty.getText() == null) {
			Empty.setText("0");
		}
		try {
			if (Empty.getText() == null) {
				Empty.setText("0");
			}
			if (Double.parseDouble(Empty.getText()) - addItems() < 0) {
				{
					Empty.setText(String.valueOf(Double.parseDouble(PaymentSum
							.getText()) - addItems()));
					Empty.setForeground(Color.RED);
					ConfirmButton.setEnabled(false);
				}
			} else {
				Empty.setText(String.valueOf(Double.parseDouble(PaymentSum
						.getText()) - addItems()));
				Empty.setForeground(Color.BLACK);
				ConfirmButton.setEnabled(true);

			}
		}

		catch (NumberFormatException e) {
			if (!PaymentSum.getText().equals("")) {
				JOptionPane.showMessageDialog(null,
						"Error, please enter numbers!", "Error",
						JOptionPane.ERROR_MESSAGE);
				Empty.setText("-");

			}

		}

	}

	public void reDo() {
		removeAll();
		add(drawDialogPane());
		revalidate();
	}

}
