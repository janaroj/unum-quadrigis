import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;


public class HistoryTableModelTest {
	private HistoryTableModel historyModel;
	private HistoryItem historyItem;
	
	@Before
	public void setUp() {
		historyModel = new HistoryTableModel();
		historyItem = new HistoryItem("28.11.2013","1:48",7);
		
	}
	
	@Test
	public void testAddHistoryItem(){
		historyModel.addItem(historyItem);
	}
	
	
}
