
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	private StockTableModel stockModel;
	private StockItem stockItem,testItem;
	private SalesDomainController domainController = new SalesDomainControllerImpl();
	private long id;
	private double price=0.0;
	private int quantity = 10;
	
	@Before
	public void setUp() {
		stockModel = new StockTableModel(domainController);
		stockItem = new StockItem("Free meal","campaign",price,quantity);
		testItem = new StockItem("Test item","Test",5,6);
		
	}
	
	@Test  
	public void addOneNewItem() { //Covers validateNameUniqueness when name is unique
		int rows = stockModel.getRowCount();
		stockModel.addItem(stockItem);
		Assert.assertEquals(rows+1, stockModel.getRowCount()); //should add one row
	}
	
	@Test
	public void addMultipleNewItems() {
		int rows = stockModel.getRowCount();
		stockModel.addItem(stockItem);
		stockModel.addItem(testItem);
		Assert.assertEquals(rows+2, stockModel.getRowCount());
	}
	
	@Test
	public void addExistingItem(){  //Covers validateNameUniqueness when name is not unique
		int rows = stockModel.getRowCount();
		stockModel.addItem(stockItem);
		stockModel.addItem(stockItem);
		Assert.assertEquals(rows+1, stockModel.getRowCount()); //Added same item twice, but row count should only change by 1
		Assert.assertEquals(2*quantity, stockModel.getItemByName(stockItem.getName()).getQuantity()); //quantity should be twice the original
		
	}
	
	
	@Test
	public void testGetItemByIdWhenItemExists(){
			stockModel.addItem(stockItem);
			id = stockItem.getId();
			Assert.assertSame(stockModel.getItemById(id),stockItem);
	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException(){
		this.testGetItemByIdWhenThrowsException(99);
	}
	
	private void testGetItemByIdWhenThrowsException(int i) throws NoSuchElementException {
		stockModel.getItemById(i);
	}
	
	@Test 
	public void testHasEnoughInStock() {
		stockModel.addItem(stockItem);
		List<SoldItem> items = new ArrayList<SoldItem>();
		items.add(new SoldItem(stockItem,quantity-5));
		stockModel.removeFromStock(items);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testHasEnoughInStockWhenThrowsException(){
		stockModel.addItem(stockItem);
		List<SoldItem> items = new ArrayList<SoldItem>();
		items.add(new SoldItem(stockItem,quantity+5));
		stockModel.removeFromStock(items);
	}
	
	@After
	public void finishUp(){
		try {
			stockModel.removeItem(stockItem);
			stockModel.removeItem(testItem);
		} catch (VerificationFailedException e) {
		}
	}
	
	
}
