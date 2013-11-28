package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;
	private final SalesDomainController domainController;
	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public StockTableModel(SalesDomainController dc) {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
		domainController = dc;
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	
	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param stockItem
	 */
	public void addItem(final StockItem stockItem) {
		try {
			StockItem item = getItemByName(stockItem.getName());
			item.setQuantity(item.getQuantity() + stockItem.getQuantity());
			domainController.modifyStockItem(item);
			log.debug("Found existing item " + stockItem.getName()
					+ " increased quantity by " + stockItem.getQuantity());
		}
		catch (NoSuchElementException e) {
			try {
			rows.add(stockItem);
			log.debug("Added " + stockItem.getName()
					+ " quantity of " + stockItem.getQuantity());
			
				domainController.addNewStockItem(stockItem);
			} catch (VerificationFailedException e1) {
				log.debug("Error adding to database");
			}
		}
		catch (VerificationFailedException ve) {
			log.debug("Error adding to database");
		}
		fireTableDataChanged();
	}
	
	public void removeFromStock(final List<SoldItem> items) throws IllegalArgumentException {
		int i = 0;
		while (i < items.size()) {
			int qnt = items.get(i).getQuantity();
			long id = items.get(i).getId();
			StockItem stockItem = getItemById(id);
			if (stockItem.getQuantity()<qnt) throw new IllegalArgumentException();
			stockItem.setQuantity(stockItem.getQuantity() - qnt);
			i++;
		}
	}
	
	public void removeItem(final StockItem stockItem) throws VerificationFailedException {
		domainController.removeStockItem(stockItem);
		fireTableDataChanged();
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

}
