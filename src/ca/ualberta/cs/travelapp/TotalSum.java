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
	
	public ArrayList<Amt_Cur> getTotalSum(ArrayList<ExpenseItem> EItems) {
		this.sum = new ArrayList<Amt_Cur>();
		ArrayList<Amt_Cur> extra = new ArrayList<Amt_Cur>();
		for (int i = 0; i < EItems.size(); i++){
			extra.add(EItems.get(i).getAmt_Cur());
		}	
		for (int i = 0; i < extra.size(); i++){
			String cur = extra.get(i).getCurrency();
			for (int j = 0; j < Extra.size(); j++) {
				if (i != j){
					if (extra.get(j).getCurrency().equals(cur)){
						Amt_Cur ac = new Amt_Cur(cur)
						sum.add(object)
					}
					if (sum.get(j).getCurrency().equals(cur)) {
						BigDecimal a = sum.get(j).getAmount();
						BigDecimal sum2 = a.add(amtcur.getAmount());
						sum.remove(j);
						Amt_Cur combined = new Amt_Cur(sum2, cur);
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
	
	public ArrayList<Amt_Cur> getTS(){
		return sum;
	}
	
	public void add(Amt_Cur ac){
		sum.add(ac);
		notifyListeners();
	}
	
	public void remove(int i) {
		sum.remove(i);
	}
	
	public void remove(Amt_Cur ac){
		sum.remove(ac);
	}
	
	public void clear(){
		int s = size();
		for (int i = 0; i < s; i++) {
			sum.remove(0);
		}
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
