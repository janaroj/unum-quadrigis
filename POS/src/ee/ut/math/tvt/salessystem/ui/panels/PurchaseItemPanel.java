package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// fields on the dialogPane
	private JComboBox<Object> barCodeField;
	private JTextField quantityField;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField sumField;

	private JButton addItemButton;

	// Warehouse model
	private SalesSystemModel model;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;

		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());

		setEnabled(false);
	}

	// shopping cart pane
	private JComponent drawBasketPane() {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Initialize the textfields
		barCodeField = new JComboBox<Object>();
		quantityField = new JTextField("1");
		nameField = new JTextField();
		priceField = new JTextField();
		sumField = new JTextField();

		// Fill the dialog fields if the bar code text field loses focus
		barCodeField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				fillDialogFields();
			}
		});

		// Fill the sum field when quantity changes
		quantityField.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				setSum();
			}

			public void removeUpdate(DocumentEvent e) {
				setSum();
			}

			public void changedUpdate(DocumentEvent e) {
				setSum();
			}

		});

		nameField.setEditable(false);
		priceField.setEditable(false);
		sumField.setEditable(false);

		// == Add components to the panel

		// - bar code
		panel.add(new JLabel("Item Name:"));
		panel.add(barCodeField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		// - name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// - sum
		panel.add(new JLabel("Sum:"));
		panel.add(sumField);

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchaseTab.submitPurchase.setEnabled(true);
				addItemEventHandler();
			}
		});

		panel.add(addItemButton);

		return panel;
	}

	// Fill dialog with data from the "database".
	public void fillDialogFields() {
		StockItem stockItem = getStockItemByBarcode();

		if (stockItem != null) {
			nameField.setText(stockItem.getName());
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
			setSum();

		} else {
			reset();
		}
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	private StockItem getStockItemByBarcode() {
		try {
			String item = String.valueOf(barCodeField.getSelectedItem());
			int i = 0;
			long code = 0;
			while (i < model.getWarehouseTableModel().getRowCount()) {
				if (item.equals(model.getWarehouseTableModel().getValueAt(i, 1))) {
					code = (long) model.getWarehouseTableModel().getValueAt(i,
							0);
					break;
				}
				i++;
			}
			return model.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = getStockItemByBarcode();
		if (stockItem != null) {
			int quantity;
			try {
				quantity = Integer.parseInt(quantityField.getText());
			} catch (NumberFormatException ex) {
				quantity = 1;
			}
			int i = 0;
			boolean found = false;
			while (i < model.getCurrentPurchaseTableModel().getRowCount()) {

				if (model.getCurrentPurchaseTableModel().getValueAt(i, 0) == stockItem
						.getId()) {
					found = true;
					break;
				}
				i++;

			}

			if (!found)
				model.getCurrentPurchaseTableModel().addItem(
						new SoldItem(stockItem, quantity));
			else {
				model.getCurrentPurchaseTableModel().changeItem(
						new SoldItem(stockItem, quantity), i);
			}
		}
		addItemButton.setText("Update cart");
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.addItemButton.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		barCodeField.removeAllItems();
		// Add items to barCodeField
		addItems();

		// Sets focus to barCodeField
		barCodeField.requestFocusInWindow();

		quantityField.setText("1");
		nameField.setText("");
		priceField.setText("");
		sumField.setText("");
		addItemButton.setText("Add to cart");
	}

	// adds items to barCodeField
	public void addItems() {
		int i = 0;
		while (i < model.getWarehouseTableModel().getRowCount()) {
			if ((Integer) (model.getWarehouseTableModel().getValueAt(i, 3)) > 0)
				barCodeField.addItem(model.getWarehouseTableModel().getValueAt(
						i, 1));
			i++;
		}

	}

	// V2ga sarnane addItemEventHandlerile, ilmselt saaks kokku panna
	public void setSum() {
		// add chosen item to the shopping cart.
		StockItem stockItem = getStockItemByBarcode();
		if (stockItem != null) {
			String quantity = quantityField.getText();
			// Checks if quantity is a number, if not sets it to 0
			int i = 0;
			try {
				while (i < model.getCurrentPurchaseTableModel().getRowCount()) {
					if (model.getCurrentPurchaseTableModel().getValueAt(i, 1) == barCodeField
							.getSelectedItem()) {
						addItemButton.setText("Update cart");
						break;
					} else {
						addItemButton.setText("Add to cart");
					}
					i++;
				}
				int amount = Integer.parseInt(quantity);
				if (amount<0) throw new NumberFormatException();
				if (amount > stockItem.getQuantity()) {
					quantityField.setBackground(Color.RED);
					JOptionPane.showMessageDialog(null,
							"Not enough products in stock",
							"Error", JOptionPane.ERROR_MESSAGE);
					addItemButton.setEnabled(false);
				} else {
					quantityField.setBackground(Color.WHITE);
					addItemButton.setEnabled(true);
				}
			} catch (NumberFormatException e) {
				quantity = "0";
				addItemButton.setEnabled(false);
			}

			sumField.setText(String.valueOf(((float) ((int) (Math
					.round((stockItem.getPrice() * Integer.parseInt(quantity) * 100))))) / 100));
		} else {
			reset();
		}
	}

	/*
	 * === Ideally, UI's layout and behavior should be kept as separated as
	 * possible. If you work on the behavior of the application, you don't want
	 * the layout details to get on your way all the time, and vice versa. This
	 * separation leads to cleaner, more readable and better maintainable code.
	 * 
	 * In a Swing application, the layout is also defined as Java code and this
	 * separation is more difficult to make. One thing that can still be done is
	 * moving the layout-defining code out into separate methods, leaving the
	 * more important methods unburdened of the messy layout code. This is done
	 * in the following methods.
	 */

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

}
