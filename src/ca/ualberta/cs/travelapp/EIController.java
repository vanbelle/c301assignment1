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


//provides static access to the managed and therefore the stored info
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
