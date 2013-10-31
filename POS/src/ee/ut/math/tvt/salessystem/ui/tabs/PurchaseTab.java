package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

	private static final Logger log = Logger.getLogger(PurchaseTab.class);

	private final SalesDomainController domainController;

	private JButton newPurchase;

	static public JButton submitPurchase;

	private JButton cancelPurchase;

	private PurchaseItemPanel purchasePane;

	private  JPanel confPanel;

	private SalesSystemModel model;

	private JPanel cards;

	private CardLayout cl;
	
	private JButton ConfirmButton,CancelButton;
	
	private JLabel Empty;
	
	private JTextField PaymentSum;


	public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
		this.domainController = controller;
		this.model = model;
		cards = new JPanel(new CardLayout());
		cl = (CardLayout) cards.getLayout();
	}

	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog
	 * and shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(model);
		panel.add(purchasePane, getConstraintsForPurchasePanel());


		cards.add(panel, "PurchasePanel");
		
		return cards;

	}

	// The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined
		// above
		panel.add(newPurchase, gc);
		panel.add(submitPurchase, gc);
		panel.add(cancelPurchase, gc);

		return panel;
	}

	// Creates the button "New purchase"
	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});

		return b;
	}

	// Creates the "Confirm" button
	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	// Creates the "Cancel" button
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}
	
	//Draws the additional screen where you can confirm the order
	
	private JComponent drawDialogPane() {
		// Create the panel
		JPanel Display = new JPanel(new GridLayout(3, 1));
		JLabel Pointless = new JLabel("");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		panel.setBorder(BorderFactory.createTitledBorder("Payment"));

		PaymentSum = new JTextField();
		JLabel ChangeAmount = new JLabel("Sum: " + sumItems() + "  Change: ");
		ConfirmButton = new JButton("Confirm");
		ConfirmButton.setEnabled(false);
		CancelButton = new JButton("Cancel");
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
				cl.show(cards, "PurchasePanel");

			}
		});
		ConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmedClicked();

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


	public void SetChangeText() {
		Empty.setText(PaymentSum.getText());
		if (Empty.getText() == null) {
			Empty.setText("0");
		}
		try {
			if (Empty.getText() == null) {
				Empty.setText("0");
			}
			if (Double.parseDouble(Empty.getText()) - sumItems() < 0) {
				{
					Empty.setText(String.valueOf(Double.parseDouble(PaymentSum
							.getText()) - sumItems()));
					Empty.setForeground(Color.RED);
					ConfirmButton.setEnabled(false);
				}
			} else {
				Empty.setText(String.valueOf(Double.parseDouble(PaymentSum
						.getText()) - sumItems()));
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
	
	
	public double sumItems() {
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
	/*
	 * === Event handlers for the menu buttons (get executed when the buttons
	 * are clicked)
	 */

	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		try {
			domainController.startNewPurchase();
			startNewSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		try {
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>submit purchase</code> event. */
	public void submitPurchaseButtonClicked() {
		confPanel = (JPanel) drawDialogPane();
		cards.add(confPanel, "ConfirmPanel");
		cl.show(cards, "ConfirmPanel");
	}
	
	public void addToHistory() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		int i=0,sum=0;
		String dateTime = dateFormat.format(date);
		String[] dateArray = dateTime.split((" - "));
		String dateString = dateArray[0],timeString = dateArray[1];
		while (i<model
				.getCurrentPurchaseTableModel().getTableRows().size()) {
			model
			.getCurrentPurchaseTableModel().getTableRows().get(i).getPrice();
			model
			.getCurrentPurchaseTableModel().getTableRows().get(i).getName();
			sum += model
					.getCurrentPurchaseTableModel().getTableRows().get(i).getSum();
			i++;
		}
		model.getHistoryTableModel().addItem(
				new HistoryItem(dateString,timeString,sum));
	}

	public void removeFromStock(){
		int i=0;
		while (i<model
				.getCurrentPurchaseTableModel().getTableRows().size()) {
			int qnt= model.getCurrentPurchaseTableModel().getTableRows().get(i).getQuantity();
			long id = model.getCurrentPurchaseTableModel().getTableRows().get(i).getId();
			StockItem stockItem = model.getWarehouseTableModel().getItemById(id);
			stockItem.setQuantity(stockItem.getQuantity()-qnt);
			i++;
		}
	}
	
	protected void confirmedClicked() {
		log.info("Sale complete");
		try {
			domainController.submitPurchase();
			log.debug("Contents of the current basket:\n"
					+ model.getCurrentPurchaseTableModel());
			domainController.submitCurrentPurchase(model
					.getCurrentPurchaseTableModel().getTableRows());
			
			removeFromStock();
			addToHistory();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/*
	 * === Helper methods that bring the whole purchase-tab to a certain state
	 * when called.
	 */

	// switch UI to the state that allows to proceed with the purchase



	private void startNewSale() {
		purchasePane.reset();
		cl.show(cards, "PurchasePanel");
		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(false);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}

	// switch UI to the state that allows to initiate new purchase
	private void endSale() {
		purchasePane.reset();
		cl.show(cards, "PurchasePanel");
		cancelPurchase.setEnabled(false);
		submitPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}

	/*
	 * === Next methods just create the layout constraints objects that control
	 * the the layout of different elements in the purchase tab. These
	 * definitions are brought out here to separate contents from layout, and
	 * keep the methods that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}

	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}

	// The constraints that control the layout of the buttons in the purchase
	// menu
	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;

		return gc;
	}

}
