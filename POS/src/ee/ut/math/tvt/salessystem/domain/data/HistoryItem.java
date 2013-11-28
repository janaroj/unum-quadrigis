package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SALE")
public class HistoryItem implements DisplayableItem {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "total_price")
	private double sum;

	@Column(name = "date")
	private String date;

	@Column(name = "time")
	private String time;

	public HistoryItem(){}
	
	public HistoryItem(String date, String time, double sum) {
		this.sum = sum;
		this.date = date;
		this.time = time;

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

	public String getName() {
		return date + time;
	}

}
