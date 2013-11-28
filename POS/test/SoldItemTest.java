import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
	private SoldItem item1;
	private SoldItem item2;
	private List<SoldItem> SoldItemList = new ArrayList<SoldItem>();

	@Before
	public void SetUp() {
		item1 = new SoldItem(new StockItem("Peet", "Punane", 5.0,1), 0);
		item2 = new SoldItem(new StockItem("Porgand", "Oranz", 3.0,1),
				0);

		SoldItemList.add(item1);
		SoldItemList.add(item2);
	}

	@Test
	public void testGetSum() {
		Assert.assertEquals(0.0, item1.getSum());
	}

	@Test
	public void testGetSumWithZeroQuantity() {
		for (SoldItem si : SoldItemList) {
			testGetSumWithZeroQuantity(si);
		}
	}

	public void testGetSumWithZeroQuantity(SoldItem si) {
		Assert.assertEquals(0.0, si.getSum());
	}
}
