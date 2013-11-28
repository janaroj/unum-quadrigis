import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest {
	private SoldItem item1;
	private SoldItem item2;
	private SoldItem item3;
	private long id = 0;
	private String name = "Kartul", desc = "Kollane";
	private double[] prices = { 2.0, 20.0, 12.0 };
	private int[] quantities = { 2, 4, 3 };
	private PurchaseInfoTableModel tabel;

	@Before
	public void SetUp() {
		item1 = new SoldItem(new StockItem(name, desc, prices[0], 7),
				quantities[0]);
		item2 = new SoldItem(new StockItem("Juust", "Kollane",
				prices[1], 5), quantities[1]);
		item3 = new SoldItem(new StockItem("Kurk", "Luunja",
				prices[2], 3), quantities[2]);

		tabel = new PurchaseInfoTableModel();

	}

	@Test
	public void testAddSoldItem() {
		tabel.addItem(item1);
		Assert.assertFalse(tabel.getTableRows().isEmpty()); //If it isn't empty then works
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetSumWithNoItems() {
		testGetSumWithNoItems(0.0);
	}

	private void testGetSumWithNoItems(double expected)
			throws IndexOutOfBoundsException {
		Assert.assertEquals(expected, tabel.getValueAt(0, 4));
	}

	@Test
	public void testGetSumWithOneItem() {
		tabel.addItem(item1);
		for (int i = 0; i < tabel.getTableRows().size(); i++) {
			Assert.assertEquals(quantities[i] * prices[i], tabel.getTableRows()
					.get(i).getSum());
		}

	}

	@Test
	public void testGetSumWithMultipleItems() {
		tabel.addItem(item1);
		tabel.addItem(item2);
		tabel.addItem(item3);
		for (int i = 0; i < tabel.getTableRows().size(); i++) {
			Assert.assertEquals(quantities[i] * prices[i], tabel.getTableRows()
					.get(i).getSum());
		}

	}
}
