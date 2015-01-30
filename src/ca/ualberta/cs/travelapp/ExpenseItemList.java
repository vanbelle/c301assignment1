package ca.ualberta.cs.travelapp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


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
			if (item.getItemName() == EItems.get(i).getItemName()){
				return true;
			}
		}
		return false;
	}
	
	public void editItemName(String oldname, String newname) throws WrongStatusException {
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setItemName(newname);
			}
		}	
	}
	
	public void editDescription(String oldname, String description) throws WrongStatusException {
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setDescription(description);
			}
		}	
	}
	
	public void editCategory(String oldname, String category) throws WrongStatusException {
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setCategory(category);
			}
		}	
	}
	
	public void editDate(String oldname, Date date) throws WrongStatusException {
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setDate(date);
			}
		}	
	}
	
	public void editAmount(String oldname, BigDecimal Amount) throws WrongStatusException {
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setAmt(Amount);
			}
		}	
	}
	
	public void editCurrency(String oldname, String currency) throws WrongStatusException {
		for (int i = 0; i < EItems.size(); i++){
			if (EItems.get(i).getItemName() == oldname) {
				EItems.get(i).setCur(currency);
			}
		}	
	}

	public void addItem(ExpenseItem item)
	{
		EItems.add(item);
		notifyListeners();
	}
	
	public boolean contains (String Name) {
		for (int i = 0; i < size(); i++) {
			if (EItems.get(i).getItemName() == Name){
				return false;
			}
		}
		return true;
	}
	
	public void removeItem(String ItemName) {
		for (int i = 0; i < EItems.size(); i++) {
			if (EItems.get(i).getItemName() == ItemName) {
				EItems.remove(i);
			}
		}
		notifyListeners();
	}

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
