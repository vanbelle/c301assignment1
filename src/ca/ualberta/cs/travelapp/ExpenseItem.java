/*
Travel App: Keeps tracks of expenses and claims for various trips.

Copyright [2015] Sarah Van Belleghem vanbelle@ualberta.ca
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package ca.ualberta.cs.travelapp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpenseItem implements Serializable{
	
	private static final long serialVersionUID = 3799830749456137439L;
	public String ClaimName;
	public String ItemName;
	public String Category;
	public String Description;
	public Date Date;
	public Amt_Cur Amt_Cur;
	
	public ExpenseItem(String ClaimName, String ItemName, String Category, String Description, Date Date, BigDecimal Amount, String Currency) {
		this.ItemName = ItemName;
		this.ClaimName = ClaimName;
		this.Category = Category;
		this.Description = Description;
		this.Date = Date;
		this.Amt_Cur = new Amt_Cur(Amount, Currency);
	}
	
	public String toString() {
		return ItemName;

	}
	
	public void setClaimName(String Name) {
		this.ClaimName = Name;
	}
	
	//puts in an email-able format 
	public String toEmailString() {
		String date = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(Date);
		return ItemName+"\n"+Category+"\n"+Description+"\n"+date+"\n"+Amt_Cur.toString()+"\n\n";

	}
	
	public String getClaimName(){
		return ClaimName;
	}
	
	public boolean equals(Object compareItem) {
		if (compareItem != null &&
				compareItem.getClass()==this.getClass()) {
			return this.equals((ExpenseItem)compareItem);
		} else {
			return false;
		}
	}

	public boolean equals(ExpenseItem item) {
		if(item==null) {
			return false;
		}
		return getItemName().equals(item.getItemName());
	}
	
	public int hashCode() {
		return ("Item:"+getItemName()).hashCode();
	}

	
	public Amt_Cur getAmt_Cur() {
		return Amt_Cur;
	}	
	
	public void setAmt(BigDecimal Amount) {
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
