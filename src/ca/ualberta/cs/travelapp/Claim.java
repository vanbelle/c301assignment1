package ca.ualberta.cs.travelapp;

import java.util.ArrayList;
import java.util.Date;

public class Claim {
	public String ClaimName;
	public String Status;
	public String Description;
	public Date StartDate;
	public Date EndDate;
	public ArrayList<ExpenseItem> EItems;
	public ArrayList<Amt_Cur> TotalSum;
	
	public Claim(String ClaimName, Date StartDate, Date EndDate, String Status, String Description) {
		this.EItems = new ArrayList<ExpenseItem>(); 
		this.ClaimName = ClaimName;
		this.StartDate = StartDate;
		this.EndDate = EndDate;
		this.Description = Description;
		this.Status = Status;	
	}
	
	public void addItem(String ItemName, String Category, String Description, Date Date, Integer Amount, String Currency) {
		ExpenseItem Item = new ExpenseItem(ItemName, Category,Description, Date, Amount,Currency);
	}
		
	public ArrayList<ExpenseItem> getEItems() {
		return EItems;
	}
		
	public ArrayList<Amt_Cur> getTotalSum() {
		this.TotalSum = new ArrayList<Amt_Cur>();
		for (int i = 0; i < EItems.size(); i++){
			ExpenseItem Item = EItems.get(i);
			Amt_Cur amtcur = Item.getAmt_Cur();
			String cur = amtcur.getCurrency();
			for (int j = 0; j < TotalSum.size(); j++) {
				if (TotalSum.get(j).getCurrency() == cur) {
					Integer a = TotalSum.get(j).getAmount();
					a = a + amtcur.getAmount();
					TotalSum.remove(j);
					Amt_Cur combined = new Amt_Cur(a, cur);
					TotalSum.add(combined);				
				}
				else {
					TotalSum.add(amtcur);
				}
			}
			
		}
		return TotalSum;
	}
	
	public String getClaimName() {
		return ClaimName;
	}
	public void setClaimName(String claimName) {
		//TODO check status
		this.ClaimName = claimName;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		//TODO check status
		this.Status = status;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		//TODO check status
		this.Description = description;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		//TODO check status
		this.StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		//TODO check status
		this.EndDate = endDate;
	}
	
	

}
