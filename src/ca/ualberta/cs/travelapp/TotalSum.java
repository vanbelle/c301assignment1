package ca.ualberta.cs.travelapp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;


public class TotalSum implements Serializable
{
	private static final long serialVersionUID = -1127997892741776638L;
	public ArrayList<Amt_Cur> sum;
	protected transient ArrayList<Listener> listeners = null;
	
	public TotalSum() {
		super();		
		this.listeners = new ArrayList<Listener>();
		this.sum = new ArrayList<Amt_Cur>();
	}
	
	public ArrayList<Amt_Cur> getTotalSum(ArrayList<ExpenseItem> List) {
		this.sum = new ArrayList<Amt_Cur>();
		for (int i = 0; i < List.size(); i++){
			ExpenseItem Item = List.get(i);
			Amt_Cur amtcur = Item.getAmt_Cur();
			String cur = amtcur.getCurrency();
			for (int j = 0; j < sum.size(); j++) {
				if (sum.get(j).getCurrency().equals(cur)) {
					BigDecimal a = sum.get(j).getAmount();
					BigDecimal b = a.add(amtcur.getAmount());
					sum.remove(j);
					Amt_Cur combined = new Amt_Cur(b, cur);
					sum.add(combined);				
				}
				else {
					sum.add(amtcur);
				}
			}
			
		}
		notifyListeners();
		return sum;
	}
	
	public int size() {
		return sum.size();
	}
	
	public boolean contains(String currency) {
		for (int i = 0; i < size(); i++) {
			if (sum.get(i).getCurrency().equals(currency)){
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Listener> getListeners() {
		if (listeners == null ) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	

	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}
	
	public void addListener (Listener L) {
		getListeners().add(L);
	}
	

	public void removeListener(Listener l) {
		getListeners().remove(l);
	}
	
}
