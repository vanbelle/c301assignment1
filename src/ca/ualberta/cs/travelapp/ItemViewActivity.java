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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemViewActivity extends Activity {
	int index;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_expenseitems);
		EIManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());

		index = getIntent().getIntExtra("position", 0); //gets claim index position

		//adds new clickable expense item button
		Button itembutton = (Button) findViewById(R.id.AddExpenseItemButton);
		itembutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String status = ClaimListController.getClaimList().getClaims().get(index).getStatus();
				if (status.equals("In Progress") || status.equals("Returned")) {
					Intent intent = new Intent(ItemViewActivity.this, AddItemActivity.class);
					intent.putExtra("claimposition", index);
					startActivity(intent);
				} else {
					Toast.makeText(ItemViewActivity.this, "Status does not allow for adding Items", Toast.LENGTH_LONG).show();
				}
			}
		});

		//clickable edit button takes user to edit Claim page
		Button editbutton = (Button) findViewById(R.id.buttonedit);
		editbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemViewActivity.this, EditClaimActivity.class);
				intent.putExtra("claimposition", index);
				startActivity(intent);
			}
		});

		//clickable email button, takes user to email page
		Button emailbutton = (Button) findViewById(R.id.buttonemail);
		emailbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemViewActivity.this, EmailActivity.class);
				intent.putExtra("claimposition", index);
				startActivity(intent);
			}
		});

		//initializes Delete Button
		Button deletebutton = (Button) findViewById(R.id.buttondelete);
		deletebutton.setOnClickListener(new View.OnClickListener() {

			@Override // checks that the user does indeed want to delete their claim
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(ItemViewActivity.this);
				adb.setMessage("Delete this Item?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String name = ClaimListController.getClaimList().getClaims().get(index).getClaimName();
						ClaimListController.getClaimList().removeClaim(name);
						ClaimListController.saveClaimList();
						//deletes all expense items associated with this claim
						for (int i = 0; i < EIController.getItemList().size(); i++) {
							if (EIController.getItemList().getItems().get(i).getClaimName().equals(name)) {
								EIController.getItemList().getItems().remove(i);
								EIController.saveItemList();
							}
						}
						Toast.makeText(ItemViewActivity.this,"Claim Deleted \n Return to Previous Page", Toast.LENGTH_LONG).show();
					}
				});
				adb.setNegativeButton("Cancel", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				adb.show();
			}
		});

	}

	public void onResume() {
		super.onResume();
		
		//lists all the claim info
		String string = ClaimListController.getClaimList().getClaims().get(index).getClaimName()+" - "+ClaimListController.getClaimList().getClaims().get(index).getStatus();
		TextView displayName = (TextView) findViewById(R.id.textClaimName);
		displayName.setText(string);

		TextView displaydescription = (TextView) findViewById(R.id.textClaimDescription);
		String s = ClaimListController.getClaimList().getClaims().get(index).getDescription();
		displaydescription.setText(s);

		TextView displayDate = (TextView) findViewById(R.id.textStarttoEndDate);
		String startdate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getStartDate());
		String endDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getEndDate());
		displayDate.setText(startdate+" - "+endDate);

		//finds the list of expense items pertaining to this claim
		ListView EIlistview = (ListView) findViewById(R.id.listExpenseItems);
		ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
		ArrayList<ExpenseItem> EI = new ArrayList<ExpenseItem>();
		final ArrayList<String> EIA = new ArrayList<String>();
		String claim = ClaimListController.getClaimList().getClaims().get(index).getClaimName();

		for (int i = 0; i < EIC.size(); i++){
			if(EIC.get(i).getClaimName().equals(claim)) {
				EI.add(EIC.get(i));
				String date = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(EIC.get(i).getDate());
				EIA.add(date+"  "+EIC.get(i).getItemName());
			}
		}
		final ArrayList<ExpenseItem> ei = EI;
		final ArrayAdapter<String> ItemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EIA);
		EIlistview.setAdapter(ItemAdapter);
		
		//adds a listener to the item list
		EIController.getItemList().addListener(new Listener() {
			@Override
			public void update() {
				ei.clear();

				ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
				final ArrayList<String> EIA = new ArrayList<String>();
				String claim = ClaimListController.getClaimList().getClaims().get(index).getClaimName();

				for (int i = 0; i < EIC.size(); i++){
					if(EIC.get(i).getClaimName().equals(claim)) {
						String date = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(EIC.get(i).getDate());
						EIA.add(date+"  "+EIC.get(i).getItemName());
					}
				}

				ItemAdapter.notifyDataSetChanged();
			}
		});
		
		//when item is clicked it goes to the corresponding item page
		EIlistview.setOnItemClickListener(new OnItemClickListener()
		{ 
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				Intent intent = new Intent(ItemViewActivity.this, ExpenseItemActivity.class);
				intent.putExtra("claimposition", index);
				intent.putExtra("itemposition", arg2);
				startActivity(intent);
			}
		});

		//computes the total sum corresponding to the expense item list
		TotalSum amounts = new TotalSum();
		amounts.getTotalSum(ei);
		ListView TSlistview = (ListView) findViewById(R.id.listTotalSum);
		final ArrayList<Amt_Cur> list = new ArrayList<Amt_Cur>();
		for (int i = 0; i < amounts.size(); i++) {
			list.add(amounts.get(i));
		}

		final ArrayAdapter<Amt_Cur> TSAdapter = new ArrayAdapter<Amt_Cur>(this, android.R.layout.simple_list_item_1, list);
		TSlistview.setAdapter(TSAdapter);

		//add a listener to the total list
		amounts.addListener(new Listener()
		{

			@Override
			public void update()
			{
				list.clear();

				ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
				ArrayList<ExpenseItem> EI = new ArrayList<ExpenseItem>();
				String claim = ClaimListController.getClaimList().getClaims().get(index).getClaimName();

				for (int i = 0; i < EIC.size(); i++){
					if(EIC.get(i).getClaimName().equals(claim)) {
						EI.add(EIC.get(i));
					}
				}
				
				TotalSum amounts = new TotalSum();
				amounts.getTotalSum(ei);
				final ArrayList<Amt_Cur> list = new ArrayList<Amt_Cur>();
				for (int i = 0; i < amounts.size(); i++) {
					list.add(amounts.get(i));
				}

				TSAdapter.notifyDataSetChanged();
			}
		});		

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_view, menu);
		return true;
	}

}
