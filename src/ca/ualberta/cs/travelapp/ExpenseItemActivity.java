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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseItemActivity extends Activity {
	public int claimindex;
	public int itemindex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_item);
		EIManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
		//gets indexes for claim and expense item lists
		final int claimindex = getIntent().getIntExtra("claimposition", 0);
		final int itemindex = getIntent().getIntExtra("itemposition", 0);
		
		//gets the list of expense items corresponding to the particular claim
		ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
		final ArrayList<ExpenseItem> EI = new ArrayList<ExpenseItem>();
		String claim = ClaimListController.getClaimList().getClaims().get(claimindex).getClaimName();
		
		for (int i = 0; i < EIC.size(); i++){
			if(EIC.get(i).getClaimName().equals(claim)) {
				EI.add(EIC.get(i));
			}
		}
		//gets item at itemindex
		ExpenseItem item = EI.get(itemindex);
		
		//displays info pertaining to that item 
		TextView displayName = (TextView) findViewById(R.id.textItemName);
		displayName.setText(item.getItemName());
		
		TextView displaydescription= (TextView) findViewById(R.id.textItemDescription);
		displaydescription.setText(item.getDescription());
		
		TextView displayDate = (TextView) findViewById(R.id.textDate);
		String Date = new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(item.getDate());
		displayDate.setText(Date);
		
		TextView displayCat = (TextView) findViewById(R.id.textCategory);
		displayCat.setText(item.getCategory());
		
		TextView displayAmt = (TextView) findViewById(R.id.textItemAmount);
		displayAmt.setText(item.getAmt_Cur().getAmount().toString());
		
		TextView displayCur = (TextView) findViewById(R.id.textItemCurrency);
		displayCur.setText(item.getAmt_Cur().getCurrency());
		
		//delete button with cancel options
		Button deletebutton = (Button) findViewById(R.id.buttonitemdelete);
		deletebutton.setOnClickListener(new View.OnClickListener() {
			
			//checks that deleting is what you want
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(ExpenseItemActivity.this);
				adb.setMessage("Delete this Item?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EIController.getItemList().getItems().remove(itemindex);
						Toast.makeText(ExpenseItemActivity.this,"Item Deleted \n Return to Previous Page", Toast.LENGTH_LONG).show();
						EIController.saveItemList();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
