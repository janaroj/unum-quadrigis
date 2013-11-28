package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Purchase history details model.
 */
public class SecondHistoryTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;
	private final SalesDomainController domainController;

	private static final Logger log = Logger
			.getLogger(SecondHistoryTableModel.class);

	public SecondHistoryTableModel(SalesDomainController domainController) {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
		this.domainController=domainController;
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	public void addItem(final SoldItem item){
			rows.add(item);
			fireTableDataChanged();
	}
	
	public void removeItems() {
		rows.clear();
		fireTableDataChanged();
		
	}
	
	public void updateTable(Long id) {
		if (id>=0) { //There is a special case when id can be -1
			if (rows.size()!=0) removeItems();
			List<SoldItem> soldItems = domainController.loadBoughtItems(id);
			for (SoldItem si:soldItems) {addItem(si);}
			
			}
	}



}