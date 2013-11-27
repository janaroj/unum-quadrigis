package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public interface SalesDomainController {

	/**
	 * Load the current state of the warehouse.
	 * 
	 * @return List of ${link
	 *         ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
	 */
	public List<StockItem> loadWarehouseState();

	public List<HistoryItem> loadHistoryState();
	
	public List<SoldItem> loadBoughtItems(int id);
	
	public void removeStockItem(StockItem good) throws VerificationFailedException;
	
	public void saveHistory(HistoryItem item) throws VerificationFailedException;
	
	public void addNewStockItem(StockItem good) throws VerificationFailedException;
	 
	public void modifyStockItem(StockItem good) throws VerificationFailedException;
	
	public void removeEntities(List<? extends DisplayableItem> items) throws VerificationFailedException;
	
	public void updateEntities(List<? extends DisplayableItem> items) throws VerificationFailedException;
	 
	public void saveEntities(List<? extends DisplayableItem> items) throws VerificationFailedException;
	
	// business processes
	/**
	 * Initiate new business transaction - purchase of the goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public void startNewPurchase() throws VerificationFailedException;

	/**
	 * Rollback business transaction - purchase of goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public void cancelCurrentPurchase() throws VerificationFailedException;

	/**
	 * Commit business transaction - purchsae of goods.
	 * 
	 * @param goods
	 *            Goods that the buyer has chosen to buy.
	 * @throws VerificationFailedException
	 */

	public void endSession();
	
	public void submitPurchase() throws VerificationFailedException;

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException;

	//public void saveEntities(List<? extends DisplayableItem> items) throws VerificationFailedException;

}
