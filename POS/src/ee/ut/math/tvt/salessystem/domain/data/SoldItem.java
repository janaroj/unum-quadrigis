package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving
 * history.
 */
@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="sale_id")
	private long historyItemId;
	
	@Column(name="stockitem_id")
	private Long stockItemId;

	@Column(name = "name")
	private String name;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "item_price")
	private double price;
	
	@Column(name = "total_sum")
	private double sum;
	


	public SoldItem(StockItem stockItem, int quantity) {
		this.stockItemId = stockItem.getId();
		this.name = stockItem.getName();
		this.price = stockItem.getPrice();
		this.quantity = quantity;
		this.sum = this.price*this.quantity;
	}

	public SoldItem(StockItem stockItem, int quantity,HistoryItem historyItem) {
		this.stockItemId = stockItem.getId();
		this.name = stockItem.getName();
		this.price = stockItem.getPrice();
		this.quantity = quantity;
		this.sum = this.price*this.quantity;
		this.historyItemId=historyItem.getId();

	}
	
	public Long getId() {
		return stockItemId;
	}

	public void setId(Long id) {
		this.stockItemId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getSum() {
		return ((double) ((int) (Math
				.round((price * ((double) quantity)) * 100)))) / 100;
	}

	public long getHistoryItemId() {
		return historyItemId;
	}

	public void setHistoryItemId(long historyItemId) {
		this.historyItemId = historyItemId;
	}

	public Long getStockItemId() {
		return stockItemId;
	}

	public void setStockItemId(Long stockItemId) {
		this.stockItemId = stockItemId;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}


}
