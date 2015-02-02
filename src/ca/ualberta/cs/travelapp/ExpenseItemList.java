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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ExpenseItemList implements Serializable
{
	private static final long serialVersionUID = -95227261104436492L;
	public ArrayList<ExpenseItem> EItems = null;
	protected transient ArrayList<Listener> listeners = null;
	
	public ExpenseItemList()
	{
		this.EItems= new ArrayList<ExpenseItem>();
		this.listeners = new ArrayList<Listener>();
	}
	
	public ArrayList<ExpenseItem> getItems()
	{
		return EItems;
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
	
	public int size() {
		return EItems.size();
	}
	
	public boolean contains(ExpenseItem item){
		for (int i = 0; i < size(); i++) {
			if (item.getItemName().equals(EItems.get(i).getItemName())){
				return true;
			}
		}
		return false;
	}

	public void addItem(ExpenseItem item)
	{
		EItems.add(item);
		notifyListeners();
	}
	
	public boolean contains (String Name) {
		for (int i = 0; i < size(); i++) {
			if (EItems.get(i).getItemName().equals(Name)){
				return false;
			}
		}
		return true;
	}
	
	public void removeItem(String ItemName) {
		for (int i = 0; i < EItems.size(); i++) {
			if (EItems.get(i).getItemName().equals(ItemName)) {
				EItems.remove(i);
			}
		}
		notifyListeners();
	}
  
	//sorts expense item list by the date of each item 
	public void sort()
	{
		Collections.sort(EItems, new Comparator<ExpenseItem>() {
			@Override
			public int compare(ExpenseItem lhs, ExpenseItem rhs){
				return lhs.getDate().compareTo(rhs.getDate());
			}
		});
	}
	

}
