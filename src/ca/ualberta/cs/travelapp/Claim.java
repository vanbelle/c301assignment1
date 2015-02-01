package ca.ualberta.cs.travelapp;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
		String startdate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(getStartDate());
		String endDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(getEndDate());
		return ClaimName+"\n"+"---------------\n"+Status+"\n"+Description+"\n"+startdate+" - "+endDate+"\n\n";
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

	public ArrayList<ExpenseItem> getEItems() {
		return EItems;
	}

	public String getClaimName() {
		return ClaimName;
	}
	
	public void setClaimName(String claimName) {
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
	
	public void setDescription(String description) {
		this.Description = description;
	}
	
	public Date getStartDate() {
		return StartDate;
	}
	
	public void setStartDate(Date startDate) {
		this.StartDate = startDate;
	}
	
	public Date getEndDate() {
		return EndDate;
	}
	
	public void setEndDate(Date endDate) {
		this.EndDate = endDate;
	}
	
	public void addItem(ExpenseItem item)
	{
		EItems.add(item);
	}
}
	
	
	
	
	
	
