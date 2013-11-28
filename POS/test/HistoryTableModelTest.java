import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;


public class HistoryTableModelTest {
	private HistoryTableModel historyModel;
	private HistoryItem historyItem;
	private String date="28.11.2013",time="1:48";
	private Double sum=10.0;
	private SalesDomainController domainController = new SalesDomainControllerImpl();
	
	@Before
	public void setUp() {
		historyModel = new HistoryTableModel();
		
		historyItem = new HistoryItem(date,time,sum);
	}
	
	@Test
	public void testAddHistoryItem(){
		historyModel.addItem(historyItem);
		Assert.assertFalse(historyModel.getTableRows().isEmpty());
	}
	
	@Test
	public void testGeneratingId(){
		try {
			historyModel.addItem(historyItem);
			Assert.assertNull(historyItem.getId()); //At first it isn't set
			domainController.saveHistory(historyItem,new ArrayList<SoldItem>()); //saves to database, DB automatically generates an id
			
			Assert.assertNotNull(historyItem.getId()); 
			
			//Removes from db
			List<HistoryItem> histList = new ArrayList<HistoryItem>();
			histList.add(historyItem);
			domainController.removeEntities(histList);
		} catch (VerificationFailedException e) {
		}
	}
	
	@Test
	public void getItemTest(){
		int rows = historyModel.getRowCount();  //this is the index of the next added item 
		//(for example if there are 2 rows - they are with indexes 0,1 so if you add another item it's id would be 2
		historyModel.addItem(historyItem);
		Assert.assertSame(historyItem, historyModel.getItem(rows));
	}
	
	
	
}
