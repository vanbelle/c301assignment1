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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

//saves all the Expense Items
public class EIManager
{
	static final String prefFile = "ItemList";
	static final String clKey = "itemList";
	Context context;

	static private EIManager eiManager = null;

	public static void initManager(Context context) {
		if (eiManager == null) {
			if (context==null) {
				throw new RuntimeException("Missing context for EIManager");
			}
			eiManager = new EIManager(context);
		}
	}

	public static EIManager getManager()
	{
		if (eiManager==null) {
			throw new RuntimeException("Did not initialize Manager");
		}
		return eiManager;
	}

	public EIManager(Context context) {
		this.context = context;
	}

	public ExpenseItemList loadItemList() throws ClassNotFoundException, IOException {
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		String itemListData = settings.getString(clKey, "");	
		if (itemListData.equals("")) {
			return new ExpenseItemList();
		} else {	
			return itemListFromString(itemListData);
		}
	}

	static public ExpenseItemList itemListFromString(String itemListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(itemListData, Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (ExpenseItemList)oi.readObject();
	}

	static public String itemListToString(ExpenseItemList cl) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo;
		oo = new ObjectOutputStream(bo);
		oo.writeObject(cl);
		oo.close();
		byte bytes[] = bo.toByteArray();
		return Base64.encodeToString(bytes,Base64.DEFAULT);
	}

	public void saveItemList(ExpenseItemList cl) throws IOException {
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(clKey, itemListToString(cl));
		editor.commit();	
	}

}

