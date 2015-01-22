package ca.ualberta.cs.travelapp;

import java.util.Date;

public class ExpenseItem {
	public String ItemName;
	public String Category;
	public String Description;
	public Date Date;
	public Integer Amount;
	public String Currency;
	
	public ExpenseItem() {
		//TODO check status
	}
	
	public String getItemName() {
		return ItemName;
	}
	
	public void setItemName(String itemName) {
		//TODO change in the claim list as well
		//TODO check status
		ItemName = itemName;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		//TODO check status
		Category = category;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		//TODO check status
		Description = description;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		//TODO check status
		Date = date;
	}
	public Integer getAmount() {
		
		return Amount;
	}
	public void setAmount(Integer amount) {
		//TODO check status
		//TODO change in total list as well
		Amount = amount;
	}
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		//TODO check status
		Currency = currency;
	}

	
}
