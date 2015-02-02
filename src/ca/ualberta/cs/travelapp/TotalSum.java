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
	
	public Amt_Cur get(int i){
		return sum.get(i);
	}
	
	public ArrayList<Amt_Cur> getTotalSum(ArrayList<ExpenseItem> EItems) {
		this.sum = new ArrayList<Amt_Cur>();
		ArrayList<Amt_Cur> extra = new ArrayList<Amt_Cur>();
		for (int i = 0; i < EItems.size(); i++){
			extra.add(EItems.get(i).getAmt_Cur());
		}	
		for (int i = 0; i < extra.size(); i++){
			String cur = extra.get(i).getCurrency();
			if (contains(cur)){
				for (int j = 0; j < size(); j++ ){
					if (sum.get(j).getCurrency().equals(cur)) {
						BigDecimal a  = sum.get(j).getAmount();
						BigDecimal b = extra.get(i).getAmount();
						BigDecimal c = a.add(b);
						Amt_Cur both = new Amt_Cur(c,cur);
						sum.remove(j);
						sum.add(both);
					}
				}
			} else {
				sum.add(extra.get(i));	
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
