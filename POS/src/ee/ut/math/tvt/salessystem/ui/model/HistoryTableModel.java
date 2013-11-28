	package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

/**
 * Stock item table model.
 */
public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(HistoryTableModel.class);
	public HistoryTableModel() {
		super(new String[] {"ID","Date", "Time", "Total Price"});
	}



	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getDate();
		case 2:
			return item.getTime();
		case 3:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final HistoryItem hi : rows) {
			buffer.append(hi.getId() +"\t");
			buffer.append(hi.getDate() + "\t");
			buffer.append(hi.getTime() + "\t");
			buffer.append(hi.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	  public void addItem(final HistoryItem item) {
	        
	        rows.add(item);

	        fireTableDataChanged();
	    }
	  
	  
	  public HistoryItem getItem(int index) {
		  return rows.get(index);
	  }

}
