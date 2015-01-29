package ca.ualberta.cs.travelapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Claim implements Serializable{
	private static final long serialVersionUID = 5792713063252972173L;
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
	
	public boolean equals(Object compareClaim) {
		if (compareClaim != null &&
				compareClaim.getClass()==this.getClass()) {
			return this.equals((Claim)compareClaim);
		} else {
			return false;
		} 
	} 
	
	public String toString() {
		return getClaimName();
	}
	
	public boolean equals(Claim compareClaim) {
		if(compareClaim==null) {	
			return false;
		}
		return getClaimName().equals(compareClaim.getClaimName());
	}
	
	public int hashCode() {
		return ("Claim:"+getClaimName()).hashCode();
	}
	
	public void addItem(String ItemName, String Category, String Description, Date Date, Integer Amount, String Currency) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw  new WrongStatusException();
		}
		ExpenseItem Item = new ExpenseItem(ItemName, Category,Description, Date, Amount,Currency);
		EItems.add(Item);
		this.getTotalSum();
	}
		
	public void editItemName(String oldname, String newname) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setItemName(newname);
			}
		}	
	}
	
	public void editDescription(String oldname, String description) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setDescription(description);
			}
		}	
	}
	
	public void editCategory(String oldname, String category) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setCategory(category);
			}
		}	
	}
	
	public void editDate(String oldname, Date date) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setDate(date);
			}
		}	
	}
	
	public void editAmount(String oldname, Integer Amount) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setAmt(Amount);
			}
		}	
		this.getTotalSum();
	}
	
	public void editCurrency(String oldname, String currency) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setCur(currency);
			}
		}	
		this.getTotalSum();
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
	public void setClaimName(String claimName) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		this.ClaimName = claimName;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		this.Status = status;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		this.Description = description;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		this.StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) throws WrongStatusException {
		if (this.Status != "In Progress" || this.Status != "Returned") {
			throw new WrongStatusException();
		}
		this.EndDate = endDate;
	}
}