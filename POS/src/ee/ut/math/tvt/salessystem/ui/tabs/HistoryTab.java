package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {
    
	private SalesSystemModel model;
	private final SalesDomainController domainController;
	
    public HistoryTab(SalesSystemModel model,SalesDomainController dc) {
    	this.model=model;
    	this.domainController = dc;
    } 
    
    public Component draw() {
    	JPanel pane = new JPanel(new GridLayout(2,1));
    	pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel panel = new JPanel();
        
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        panel.setLayout(gb);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1.0d;
        gc.weighty = 0d;


        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        panel.add(drawHistoryMainPane(), gc);
        
        pane.add(panel);
        pane.add(drawPurchasedItems());
        return pane;
    }
    

    private Component drawPurchasedItems(){
    	JPanel panel = new JPanel();
    	panel.setBorder(BorderFactory.createTitledBorder("History"));
    	
    	JTable table = new JTable(model.getSecondHistoryTableModel());
    	
    	GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        
        panel.setLayout(gb);
        panel.add(scrollPane, gc);
    	return panel;
    }
    
    private Component drawHistoryMainPane(){
    	JPanel panel = new JPanel();

        JTable table = new JTable(model.getHistoryTableModel());
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        table.getSelectionModel().addListSelectionListener(new ListListener(table,model,domainController));
        JScrollPane scrollPane = new JScrollPane(table);

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        panel.setLayout(gb);
        panel.add(scrollPane, gc);

        panel.setBorder(BorderFactory.createTitledBorder("History"));
        return panel;
    }
}

class ListListener implements ListSelectionListener {
	private JTable table;
	private SalesSystemModel model;
	private final SalesDomainController domainController;
	ListListener(JTable table,SalesSystemModel model,SalesDomainController dc){
		this.table=table;
		this.model = model;
		this.domainController=dc;
	
	}
	public void valueChanged(ListSelectionEvent e) {
		int row = table.getSelectedRow()+1;
		
		if (table.getSelectedRow()>=0) {
		if (model.getSecondHistoryTableModel().getRowCount()!=0) model.getSecondHistoryTableModel().removeItems();
		
		List<SoldItem> soldItems = domainController.loadBoughtItems(row);
		
		for (SoldItem si:soldItems) {model.getSecondHistoryTableModel().addItem(si);}
		
		}
	}
}