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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Calendar;
import android.widget.DatePicker;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.Toast;

public class AddItemActivity extends Activity {
    private EditText Date;
    private DatePickerDialog DatePickerDialog;
    private SimpleDateFormat df;
	private int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_expense_item);
		ClaimListManager.initManager(this.getApplicationContext());
		EIManager.initManager(this.getApplicationContext());
		
		index = getIntent().getIntExtra("claimposition", 0);
		
	    df =new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	    
        Date = (EditText) findViewById(R.id.editEnterDate);    
        Date.setInputType(InputType.TYPE_NULL);
		
        setDateTimeField();
        
        //to save any added items
        Button savebutton = (Button) findViewById(R.id.buttonsaveItem);
		savebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				try
				{
					addItemAction(v);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
    }
		
	//set calendar module
    private void setDateTimeField() {
        Date.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
			    DatePickerDialog.show();
			}});
        
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date.setText(df.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));}
        
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}
	
	public void addItemAction(View v) throws ParseException {
		EditText itemname = (EditText) findViewById(R.id.editEnterItemName);
		EditText category = (EditText) findViewById(R.id.editEnterCategory);
		EditText description = (EditText) findViewById(R.id.editEnterDescription);
		EditText currency = (EditText) findViewById(R.id.editEnterCurr);
		EditText amount = (EditText) findViewById(R.id.editEnterAmount);
		
		// Create a String format for the amount from bigdeciaml
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,###.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		
		//checks that item name is still available
		if (EIController.getItemList().getItems().contains(itemname.getText().toString())) {
			Toast.makeText(this, "A claim with that name already exists", Toast.LENGTH_LONG).show();
		} else {
			//formats date and big decimal into string
			BigDecimal amt = (BigDecimal) decimalFormat.parse(amount.getText().toString());
			Date date = df.parse(Date.getText().toString());
			String claim = ClaimListController.getClaimList().getClaims().get(index).getClaimName();

			ExpenseItem item= new ExpenseItem(claim, itemname.getText().toString(), category.getText().toString(), description.getText().toString(), date, amt, currency.getText().toString());
			
			//add item to claims list expense item and claim list expense item
			EIController.getItemList().addItem(item);
			ClaimListController.getClaimList().getClaims().get(index).getEItems().add(item);

			Toast.makeText(this, "Item Added",Toast.LENGTH_SHORT).show();
			
			//reset text boxes
			itemname.setText("");
			category.setText("");
			description.setText("");
			Date.setText("");
			amount.setText("");
			currency.setText("");
			
			//add hints
			itemname.setHint("Enter Another Item");
			category.setHint("Enter Category");
			description.setHint("Enter Description");
			Date.setHint("Enter Date");
			amount.setHint("Enter Amount");
			currency.setHint("Enter Currency");
		}
	}

}
