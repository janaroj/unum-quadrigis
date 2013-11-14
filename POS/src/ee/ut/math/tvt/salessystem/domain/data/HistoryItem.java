package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

public class HistoryItem implements DisplayableItem {

	@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private Long id;

	@Column(name = "sum")
	private double sum;

	@Column(name = "date")
	private String date;

	@Column(name = "time")
	private String time;

	@OneToMany(mappedBy = "acceptedOrder")
	private List<SoldItem> soldItems;

	public HistoryItem(String date, String time, double sum,
			List<SoldItem> soldItems) {
		this.sum = sum;
		this.date = date;
		this.time = time;
		this.soldItems = soldItems;

	}

	public List<SoldItem> getSoldItems() {
		return soldItems;
	}

	public void setSoldItems(List<SoldItem> soldItems) {
		this.soldItems = soldItems;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
