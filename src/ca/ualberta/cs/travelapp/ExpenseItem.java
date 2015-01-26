package ca.ualberta.cs.travelapp;

import java.util.Date;

public class ExpenseItem {
	public String ItemName;
	public String Category;
	public String Description;
	public Date Date;
	public Amt_Cur Amt_Cur;
	
	public ExpenseItem(String ItemName, String Category, String Description, Date Date, Integer Amount, String Currency) {
		this.ItemName = ItemName;
		this.Category = Category;
		this.Description = Description;
		this.Date = Date;
		this.Amt_Cur = new Amt_Cur(Amount, Currency);
	}
	
	public Amt_Cur getAmt_Cur() {
		return Amt_Cur;
	}	
	
	public void setAmt(Integer Amount) {
		this.Amt_Cur.setAmount(Amount);
	}	
	
	public void setCur(String Currency){
		this.Amt_Cur.setCurrency(Currency);
	}
	
	public String getItemName() {
		return ItemName;
	}
	
	public void setItemName(String itemName) {
		this.ItemName = itemName;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		this.Category = category;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		this.Description = description;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		this.Date = date;
	}	
}
