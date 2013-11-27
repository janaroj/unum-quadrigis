
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	private StockTableModel stockModel;
	private StockItem stockItem;
	private SalesDomainController domainController = new SalesDomainControllerImpl();
	private long id=99;
	
	@Before
	public void setUp() {
		stockModel = new StockTableModel(domainController);
		stockItem = new StockItem(id,"Free meal","campaign",0);
		stockModel.addItem(stockItem);
	}
	
	@Test(expected = NoSuchElementException.class)  //TODO vale exception
	public void testValidateNameUniqueness() {
		
		this.testValidateNameUniqueness(stockItem);
	}
	
	private void testValidateNameUniqueness(StockItem si) throws NoSuchElementException {
		stockModel.addItem(si);
	}
	
	@Test
	public void testHasEnoughInStock(){
		
	}
	
	@Test
	public void testGetItemByIdWhenItemExists(){
			Assert.assertSame(stockModel.getItemById(id),stockItem);
	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException(){
		this.testGetItemByIdWhenThrowsException(2);
	}
	
	private void testGetItemByIdWhenThrowsException(int i) throws NoSuchElementException {
		stockModel.getItemById(i);
	}
	
	@After
	public void finishUp(){
		try {
			stockModel.removeItem(stockItem);
		} catch (VerificationFailedException e) {
		}
	}
	
	
}
