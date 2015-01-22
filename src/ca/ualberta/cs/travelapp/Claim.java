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
	public ArrayList<Integer> TotalSum;
	
	public Claim(String ClaimName, Date StartDate, Date EndDate, String Status, String Description) {
		this.EItems = new ArrayList<ExpenseItem>(); 
		this.TotalSum = new ArrayList<Integer>();
		this.ClaimName = ClaimName;
		this.StartDate = StartDate;
		this.EndDate = EndDate;
		this.Description = Description;
		this.Status = Status;	
	}
	
	public void deleteClaim(String ClaimName) {
		//TODO delete item
	}
	
	public ArrayList<ExpenseItem> getEItems() {
		return EItems;
	}
		
	public ArrayList<Integer> getTotalSum() {
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
