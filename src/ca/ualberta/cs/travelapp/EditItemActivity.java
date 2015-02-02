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
//calendar view code from http://androidopentutorials.com/android-datepickerdialog-on-edittext-click-event/ 2015/01/26
package ca.ualberta.cs.travelapp;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends Activity
{
	private EditText Date;
	int claimindex;
	int itemindex;
    
    private DatePickerDialog DatePickerDialog;
    
    private SimpleDateFormat df;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.edititem);
		ClaimListManager.initManager(this.getApplicationContext());
		EIManager.initManager(this.getApplicationContext());
		
		//initialize edit text variables
		EditText name = (EditText) findViewById(R.id.editsetItemName);
		EditText category = (EditText) findViewById(R.id.editsetItemCategory);
		EditText description = (EditText) findViewById(R.id.editsetItemDescription);
		Date = (EditText) findViewById(R.id.editsetItemDate);
		EditText amount = (EditText) findViewById(R.id.EntersetClaimStartDate);
		EditText currency = (EditText) findViewById(R.id.editsetItemCurrency);
		
		setDateTimeField();
		
		final int claimindex = getIntent().getIntExtra("claimposition", 0);
		final int itemindex = getIntent().getIntExtra("itemposition", 0);
		
		//gets list of expense items to compare index value
		ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
		final ArrayList<ExpenseItem> EI = new ArrayList<ExpenseItem>();
		String claim = ClaimListController.getClaimList().getClaims().get(claimindex).getClaimName();
		
		for (int i = 0; i < EIC.size(); i++){
			if(EIC.get(i).getClaimName().equals(claim)) {
				EI.add(EIC.get(i));
			}
		}
		
		ExpenseItem item = EI.get(itemindex);
		
		//sets all the text fields to their corresponding info
		name.setText(item.getItemName());
		category.setText(item.getCategory());
		description.setText(item.getDescription());
		currency.setText(item.getAmt_Cur().getCurrency());
		amount.setText(item.getAmt_Cur().getAmount().toString());
		String date = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getDate());
		Date.setText(date);
		
		//button to save any changes made
        Button savebutton = (Button) findViewById(R.id.buttonsavesetItem);
		savebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				try
				{
					editItemAction(v);
				} catch (ParseException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public void editItemAction(View v) throws ParseException {
		
		EditText name = (EditText) findViewById(R.id.editsetItemName);
		EditText category = (EditText) findViewById(R.id.editsetItemCategory);
		EditText description = (EditText) findViewById(R.id.editsetItemDescription);
		EditText amount = (EditText) findViewById(R.id.EntersetClaimStartDate);
		EditText currency = (EditText) findViewById(R.id.editsetItemCurrency);
		
		//gets list of expense items
		ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
		final ArrayList<ExpenseItem> EI = new ArrayList<ExpenseItem>();
		String claim = ClaimListController.getClaimList().getClaims().get(claimindex).getClaimName();
		
		for (int i = 0; i < EIC.size(); i++){
			if(EIC.get(i).getClaimName().equals(claim)) {
				EI.add(EIC.get(i));
			}
		}
		
		ExpenseItem item = EI.get(itemindex);
		
		Date start = df.parse(Date.getText().toString());
		String cname = ClaimListController.getClaimList().getClaims().get(claimindex).getClaimName();
		
		//checks that item name is still available
		String itemname = item.getItemName();
		if (!itemname.equals(name.getText().toString())) {
			if (EIController.getItemList().getItems().contains(name)) {
				Toast.makeText(this, "This Item Name is already being used", Toast.LENGTH_LONG).show();
			}
		}
		
		int index = 0;
		for (int i = 0; i < ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().size(); i++){
			if (ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(i).getItemName().equals(itemname)){
				index = i;
			}
		}
		if (!ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(0).getItemName().equals(itemname) && index == 0){
			throw new RuntimeException();
		}

		//parses amount into a string from a big decimal
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,###.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		BigDecimal amt = (BigDecimal) decimalFormat.parse(amount.getText().toString());
		
		//removes old expense item and adds new one
		EIController EC = new EIController();
		EIController.getItemList().getItems().remove(item);
		ExpenseItem Item = new ExpenseItem(cname, name.getText().toString(), category.getText().toString(), description.getText().toString(), start, amt,currency.getText().toString());
		EC.addItem(Item);
		EIController.saveItemList();
		
		Toast.makeText(this, "Claim Changed",Toast.LENGTH_SHORT).show();
		
		//gets new item information
		ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).setItemName(name.getText().toString());
		ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).setAmt(amt);
		ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).setCategory(category.getText().toString());
		ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).setCur(currency.getText().toString());
		ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).setDescription(description.getText().toString());
		ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).setDate(start);
		
		//shows uptodate info
		name.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).getItemName());
		category.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).getCategory());
		description.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).getDescription());
		currency.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).getAmt_Cur().getCurrency());
		amount.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).getAmt_Cur().getAmount().toString());
		String date = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(index).getDate());
		Date.setText(date);
	}
	
	//initializes calendar module
    private void setDateTimeField() {
        Date.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
			    DatePickerDialog.show();
			}
		});

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date.setText(df.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
