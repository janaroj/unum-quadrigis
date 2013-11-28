import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemTest {
	private StockItem item1;
	private long id = 0;
	private String name = "Kapsas", desc = "Roheline";
	private double price = 15.0;
	private int quantity = 5;

	@Before
	public void SetUp() {
		item1 = new StockItem(id, name, desc, price, quantity);
	}

	@Test
	public void testClone() {
		Assert.assertNotSame(item1, item1.clone());
	}

	@Test
	public void testGetColumn() {
		Assert.assertEquals(id, item1.getColumn(0));
		Assert.assertEquals(name, item1.getColumn(1));
		Assert.assertEquals(desc, item1.getColumn(4));
		Assert.assertEquals(price, item1.getColumn(2));
		Assert.assertEquals(quantity, item1.getColumn(3));
	}

	@Test(expected = RuntimeException.class)
	public void testGetInvalidColumn() {
		testGetInvalidColumn(5);
	}

	private void testGetInvalidColumn(int id) throws RuntimeException {
		item1.getColumn(id);// invalid column!
	}

}
