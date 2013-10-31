package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class StockTab {
	private JButton addItem;
	
	private JTextField idField;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField quantityField;

	private JButton addItemButton;


	private SalesSystemModel model;

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
		
		// find proper constraints plz
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
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;

		// creates AddItem button and adds to panel
		addItem = new JButton("Add");
		panel.add(addItem, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}

	private Component drawStockAddPane() {
		// creating the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// initializing the textfields
		// id field default value should be generated automatically
		// based on the nr of items in the warehouse
		idField = new JTextField("");
		nameField = new JTextField();
		priceField = new JTextField();
		quantityField = new JTextField();

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

		// create and add the add button
		addItemButton = new JButton("Add to warehouse");
		addItemButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();

			}
		});
		panel.add(addItemButton);

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

	public void addItemEventHandler() {
		// add item to the list of warehouse items

	}

}
