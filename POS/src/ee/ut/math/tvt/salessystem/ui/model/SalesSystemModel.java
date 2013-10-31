package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {
    
    private static final Logger log = Logger.getLogger(SalesSystemModel.class);
    
    // History model
    private HistoryTableModel historyTableModel;
    
    // Warehouse model
    private StockTableModel warehouseTableModel;
    
    // Current shopping cart model
    private PurchaseInfoTableModel currentPurchaseTableModel;
    
    // More precise history model
    private SecondHistoryTableModel secHistoryTableModel;

    private final SalesDomainController domainController;

    /**
     * Construct application model.
     * @param domainController Sales domain controller.
     */
    public SalesSystemModel(SalesDomainController domainController) {
        this.domainController = domainController;
        warehouseTableModel = new StockTableModel();
        historyTableModel = new HistoryTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel();
        secHistoryTableModel = new SecondHistoryTableModel();

        // populate stock model with data from the warehouse
        warehouseTableModel.populateWithData(domainController.loadWarehouseState());

    }

    public StockTableModel getWarehouseTableModel() {
        return warehouseTableModel;
    }
    
    public HistoryTableModel getHistoryTableModel() {
    	return historyTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }
    
    public SecondHistoryTableModel getSecondHistoryTableModel(){
    	return secHistoryTableModel;
    }
    
    
    
    
}
