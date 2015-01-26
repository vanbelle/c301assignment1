package ca.ualberta.cs.travelapp;

public class Amt_Cur
{
	public Integer Amount;
	public String Currency;
	
	public Amt_Cur(Integer Amount, String Currency) {
		this.Amount = Amount;
		this.Currency = Currency;	
	}
	public Integer getAmount() {
		return Amount;
	}
	
	public void setAmount(Integer amount) {
		this.Amount = amount;
	}
	
	public String getCurrency() {
		return Currency;
	}
	
	public void setCurrency(String currency){
		this.Currency = currency;
	}
}
