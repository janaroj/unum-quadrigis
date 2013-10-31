package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import org.apache.log4j.Logger;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class StockTab {

	private static final Logger Log = Logger.getLogger(StockTab.class);
	private SalesSystemModel model;
	// stockMenuPane fields
	private JButton addItem;

	// stockAddPane fields
	private JTextField idField;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField quantityField;
	private JTextField descriptionField;
	private JButton addItemButton;
	private JButton cancelButton;

	public StockTab(SalesSystemModel model) {
		this.model = model;
	}

	// warehouse stock tab - consists of a menu and a table
	public Component draw() {
		JPanel panel = new JPanel();

		// layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// set contraints for the StockMenuPane panel
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		// add the StockMenuPane panel to StockTab
		panel.add(drawStockMenuPane(), gc);

		// do with one GridBagConstraint object
		GridBagConstraints gc2 = new GridBagConstraints();
		gc2.anchor = GridBagConstraints.WEST;
		gc2.weightx = 0.2;
		gc2.weighty = 0d;
		gc2.gridwidth = GridBagConstraints.REMAINDER;
		gc2.fill = GridBagConstraints.NONE;
		panel.add(drawStockAddPane(), gc2);

		// set (add) constraints for the StockMainPane panel
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;

		// add the StockMainPane panel to StockTab
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		// initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		// set constraints (layout) for addItem button
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0;
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;

		// creates AddItem button and adds to panel
		addItem = new JButton("Add");
		addItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				addButtonClicked();

			}
		});
		panel.add(addItem, gc);

		return panel;
	}

	private Component drawStockAddPane() {
		// creating the panel
		JPanel panel = new JPanel();
		GridLayout addPanel = new GridLayout(6, 2);
		panel.setLayout(addPanel);

		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// initializing fields
		idField = new JTextField();
		idField.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				setStockItemName();
			}

			public void removeUpdate(DocumentEvent e) {
				setStockItemName();
			}

			public void changedUpdate(DocumentEvent e) {
				setStockItemName();
			}

		});
		nameField = new JTextField();
		priceField = new JTextField();
		quantityField = new JTextField();
		descriptionField = new JTextField();
		addItemButton = new JButton("Add to warehouse");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemToWarehouseButtonClicked();
			}
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButtonClicked();
			}
		});

		setStockAddPaneEnabled(false);

		// add fields with labels
		// id
		panel.add(new JLabel("Id"));
		panel.add(idField);

		// name
		panel.add(new JLabel("Name"));
		panel.add(nameField);

		// price
		panel.add(new JLabel("Price"));
		panel.add(priceField);

		// quantity
		panel.add(new JLabel("Quantity"));
		panel.add(quantityField);

		// description
		panel.add(new JLabel("Description"));
		panel.add(descriptionField);

		panel.add(addItemButton);
		panel.add(cancelButton);

		return panel;

	}

	// table of the wareshouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getWarehouseTableModel());
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

	public void addItemToWarehouseButtonClicked() {
		Log.info("Add to warehouse button clicked");

		// parsing exceptions?
		try {
			Long id = Long.parseLong(idField.getText());
			String name = nameField.getText();
			Double price = Double.parseDouble(priceField.getText());
			Integer quantity = Integer.parseInt(quantityField.getText());
			String description = descriptionField.getText();

			StockItem stockItem = new StockItem(id, name, description, price,
					quantity);

			model.getWarehouseTableModel().addItem(stockItem);

			resetNonUniqueFields();
			idField.setText("");

			setStockAddPaneEnabled(false);
		} catch (NumberFormatException ex) {
			Log.debug("Empty fields or incorrect input");
		}

	}

	protected void addButtonClicked() {
		Log.info("Add button clicked");

		setStockAddPaneEnabled(true);

	}

	protected void setStockAddPaneEnabled(boolean b) {
		idField.setEnabled(b);
		nameField.setEnabled(b);
		priceField.setEnabled(b);
		quantityField.setEnabled(b);
		descriptionField.setEnabled(b);
		addItemButton.setEnabled(b);
		cancelButton.setEnabled(b);

		addItem.setEnabled(!b);
	}

	private void setStockItemName() {
		try {
			// parsing exceptions
			Long itemId = Long.parseLong(String.valueOf(idField.getText()));
			String name = model.getWarehouseTableModel().getItemById(itemId)
					.getName();
			Double price = model.getWarehouseTableModel().getItemById(itemId)
					.getPrice();
			String desc = model.getWarehouseTableModel().getItemById(itemId)
					.getDescription();

			nameField.setText(name);
			priceField.setText(price.toString());
			descriptionField.setText(desc);

			setStockAddPaneEditable(false);
		} catch (NumberFormatException ex) {

			setStockAddPaneEditable(true);
			resetNonUniqueFields();

		} catch (NoSuchElementException ex) {

			setStockAddPaneEditable(true);
			resetNonUniqueFields();
		}
	}

	protected void resetNonUniqueFields() {
		nameField.setText("");
		priceField.setText("");
		descriptionField.setText("");
		quantityField.setText("");

	}

	protected void cancelButtonClicked() {
		resetNonUniqueFields();
		idField.setText("");
		setStockAddPaneEnabled(false);
	}

	protected void setStockAddPaneEditable(boolean b) {
		nameField.setEditable(b);
		priceField.setEditable(b);
		descriptionField.setEditable(b);
	}

}
