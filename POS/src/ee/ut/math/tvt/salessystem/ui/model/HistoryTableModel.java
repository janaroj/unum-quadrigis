	package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;

/**
 * Stock item table model.
 */
public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public HistoryTableModel() {
		super(new String[] {"Date", "Time", "Total Price"});
	}



	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final HistoryItem HistoryItem : rows) {
			buffer.append(HistoryItem.getDate() + "\t");
			buffer.append(HistoryItem.getTime() + "\t");
			buffer.append(HistoryItem.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	  public void addItem(final HistoryItem item) {
	        
	        rows.add(item);
	        log.debug("Added " + item.getDate() + " " + item.getTime() + " Sum " + item.getSum());
	        fireTableDataChanged();
	    }

}
