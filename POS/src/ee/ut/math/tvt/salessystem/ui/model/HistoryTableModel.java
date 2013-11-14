	package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Stock item table model.
 */
public class HistoryTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(HistoryTableModel.class);
	public HistoryTableModel() {
		super(new String[] {"Date", "Time", "Total Price"});
	}



	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		return null;
	//TODO
	}
	
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem HistoryItem : rows) {
		//	buffer.append(HistoryItem.getDate() + "\t");
		//	buffer.append(HistoryItem.getTime() + "\t");
			buffer.append(HistoryItem.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	  public void addItem(final SoldItem item) {
	        
	        rows.add(item);

	        fireTableDataChanged();
	    }
	  
	  public SoldItem getItem(int index) {
		  return rows.get(index);
	  }

}
