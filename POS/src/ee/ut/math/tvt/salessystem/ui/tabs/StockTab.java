package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	
	//Listener
	private DocListener docListener;

	public StockTab(SalesSystemModel model) {
		this.model = model;
		docListener = new DocListener();
	}

	// warehouse stock tab - consists of a menu, an add pane and a table
	public Component draw() {
		JPanel panel = new JPanel();

		// layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// add the panels to stocktab
		panel.add(drawStockMenuPane(), getStockMenuPaneConstraints());
		panel.add(drawStockAddPane(), getStockAddPaneConstraints());
		panel.add(drawStockMainPane(), getStockMainPaneConstraints());
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		// initialize menu layout
		panel.setLayout(new GridBagLayout());

		// creates AddItem button and adds to panel
		addItem = new JButton("Add");
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonClicked();
			}
		});
		panel.add(addItem, getAddButtonConstraints());

		return panel;
	}

	// panel for stocking the warehouse
	private Component drawStockAddPane() {
		// creating the panel
		JPanel panel = new JPanel();
		GridLayout addPanel = new GridLayout(6, 2);
		panel.setLayout(addPanel);

		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// initializing fields
		idField = new JTextField();
		nameField = new JTextField();
		priceField = new JTextField();
		quantityField = new JTextField();
		descriptionField = new JTextField();
		idField.getDocument().addDocumentListener(docListener);
		idField.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				setStockItemValues	();			
			}
			
			public void insertUpdate(DocumentEvent e) {
				setStockItemValues();
				
			}
			
			public void changedUpdate(DocumentEvent e) {
				setStockItemValues();
				
			}
		});
		priceField.getDocument().addDocumentListener(docListener);
		quantityField.getDocument().addDocumentListener(docListener);
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

		// adding fields with labels
		panel.add(new JLabel("Id"));
		panel.add(idField);

		panel.add(new JLabel("Name"));
		panel.add(nameField);

		panel.add(new JLabel("Price"));
		panel.add(priceField);

		panel.add(new JLabel("Quantity"));
		panel.add(quantityField);

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
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);
		panel.add(scrollPane, getScrollPaneConstraints());

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

	// buttons clicked
	public void addItemToWarehouseButtonClicked() {
		// parsing exceptions?
		try {
			Long id = Long.parseLong(idField.getText());
			String name = nameField.getText();
			Double price = Double.parseDouble(priceField.getText());
			Integer quantity = Integer.parseInt(quantityField.getText());
			String description = descriptionField.getText();

			if (id < 0 || price < 0 || quantity < 0) {
				throw new NumberFormatException();
			}

			StockItem stockItem = new StockItem(id, name, description, price,
					quantity);

			model.getWarehouseTableModel().addItem(stockItem);

			resetNonUniqueFields();
			idField.setText("");

			setStockAddPaneEnabled(false);
		} catch (NumberFormatException ex) {
			Log.debug("Add to warehouse number format ex");
			JOptionPane.showMessageDialog(null,
					"Empty fields or otherwise incorrect input", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	protected void addButtonClicked() {
		setStockAddPaneEnabled(true);

	}

	protected void cancelButtonClicked() {
		resetNonUniqueFields();
		idField.setText("");
		setStockAddPaneEnabled(false);
	}


	// enabling/resetting fields
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

	protected void resetNonUniqueFields() {
		nameField.setText("");
		priceField.setText("");
		descriptionField.setText("");
		quantityField.setText("");

	}

	protected void setStockAddPaneEditable(boolean b) {
		nameField.setEditable(b);
		priceField.setEditable(b);
		descriptionField.setEditable(b);
	}
	// checks if input is valid
	private void checkInput() {
		try {
			int qnt =Integer.parseInt(quantityField.getText());
			if (qnt<=0) throw new NumberFormatException();
			quantityField.setForeground(Color.black);
		}
		catch (NumberFormatException e) {
			quantityField.setForeground(Color.red);
		}
		try {
			int id = Integer.parseInt(idField.getText());
			if (id<=0) throw new NumberFormatException();
			idField.setForeground(Color.black);
		}
		catch (NumberFormatException e) {
			idField.setForeground(Color.red);
		}
		try {
			double price = Double.parseDouble(priceField.getText());
			if (price<0) throw new NumberFormatException();
			priceField.setForeground(Color.black);
		}
		catch (NumberFormatException e) {
			priceField.setForeground(Color.red);
		}
	}
	
	// sets the correct values if an item with the same id already exists
	private void setStockItemValues() {
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
	
	private class DocListener implements DocumentListener{

		public void insertUpdate(DocumentEvent e) {
			checkInput();
			
		}

		public void removeUpdate(DocumentEvent e) {
			checkInput();
			
		}

		public void changedUpdate(DocumentEvent e) {
			checkInput();
			
		}
		
	}

	// constraints
	private GridBagConstraints getStockMainPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getStockMenuPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}

	private GridBagConstraints getStockAddPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	private GridBagConstraints getAddButtonConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0;
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		return gc;
	}

	private GridBagConstraints getScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		return gc;
	}
}


