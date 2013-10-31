package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;


public class HistoryItem implements DisplayableItem{
	private long id;
	private int sum;
	private String date;
	private String time;
	private List<SoldItem> soldItems;
	
	public HistoryItem(String date,String time,int sum,List<SoldItem> soldItems){
		this.sum=sum;
		this.date=date;
		this.time=time;
		this.soldItems = soldItems;
		
	}
	public List<SoldItem> getSoldItems() {
		return soldItems;
	}
	public void setSoldItems(List<SoldItem> soldItems) {
		this.soldItems = soldItems;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
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
