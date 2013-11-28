import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SecondHistoryTableModel;


public class SecondHistoryTableModelTest {
	private HistoryTableModel historyModel;
	private SecondHistoryTableModel secondHistoryModel;
	private HistoryItem historyItem1,historyItem2;
	private String[] dates = {"26.11.2013","28.11.2013"},times = {"1:48","18:18"};
	private SalesDomainController domainController = new SalesDomainControllerImpl();
	private SoldItem item1,item2,item3;
	private List<SoldItem> soldItems1,soldItems2;
	
	@Before
	public void setUp(){
		//This table only has items if a row is selected from HistoryTable
		historyModel = new HistoryTableModel();
		secondHistoryModel = new SecondHistoryTableModel(domainController);
		
		item1 = new SoldItem(new StockItem("Juust","Cheesy",1.0,10),5);
		item2 = new SoldItem(new StockItem("Vorst","Kodumaine",2.0,5),2);
		item3 = new SoldItem(new StockItem("Vein","Magus",1.0,3),1);
		soldItems1 = new ArrayList<SoldItem>(Arrays.asList(item1,item2));
		historyItem1 = new HistoryItem(dates[0],times[0],14);
		historyModel.addItem(historyItem1);
		soldItems2 = new ArrayList<SoldItem>(Arrays.asList(item3));
		historyItem2 = new HistoryItem(dates[1],times[1],1);
		historyModel.addItem(historyItem2);
	}
	
	@Test
	public void addAndShowItemTestWithOneItem(){
		//saves to database, DB automatically generates an id
		try {
		
			domainController.saveHistory(historyItem2,soldItems2);
			secondHistoryModel.updateTable(historyItem2.getId());
			Assert.assertEquals(soldItems2.size(), secondHistoryModel.getRowCount());
		} catch (VerificationFailedException e) {
		} 
	}
	
	@Test
	public void addAndShowItemTestWithMultipleItems(){
		//saves to database, DB automatically generates an id
		try {
			domainController.saveHistory(historyItem1,soldItems1);
			secondHistoryModel.updateTable(historyItem1.getId());
			Assert.assertEquals(soldItems1.size(), secondHistoryModel.getRowCount());
		} catch (VerificationFailedException e) {
		} 
	}
	
	
	@After
	public void finish(){
		try {
			List<HistoryItem> histList = new ArrayList<HistoryItem>();
			histList.add(historyItem1);
			histList.add(historyItem2);
			domainController.removeEntities(histList);
			domainController.removeEntities(soldItems1);
			domainController.removeEntities(soldItems2);
		} catch (VerificationFailedException e) {
		}
	}
	
}
