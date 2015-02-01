package ca.ualberta.cs.travelapp;

import java.io.IOException;
import java.util.ArrayList;


public class EIController
{
	private static ExpenseItemList itemlist = null;

	static public ExpenseItemList getItemList() {
		if (itemlist == null) {
			try {
				itemlist = EIManager.getManager().loadItemList();
				itemlist.addListener(new Listener() {
					
					@Override
					public void update() {
						saveItemList();
					}
				});
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize ItemList from ItemListManager");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize ItemList from ItemListManager");
			}
		}
		return itemlist;
	}
	
	static public void saveItemList() {
		try {
			EIManager.getManager().saveItemList(getItemList());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not deserialize ItemList from ItemListManager");
		}
	}
	
	public void sort() {
		getItemList().sort();
	}

	public void addItem(ExpenseItem item){
		getItemList().addItem(item);
	}
	
	public void addAll(ArrayList<ExpenseItem> list){
		for (int i = 0; i < list.size(); i++) {
			getItemList().addItem(list.get(i));
		}
	}
}
