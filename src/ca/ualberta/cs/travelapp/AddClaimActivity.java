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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddClaimActivity extends Activity
{
    private EditText startDate;
    private EditText endDate;
    
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    
    private SimpleDateFormat df;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);
		ClaimListManager.initManager(this.getApplicationContext());
		EIManager.initManager(this.getApplicationContext());
		
	    df =new SimpleDateFormat("dd-MM-yyyy", Locale.US);
	    
	    //intialize empty text spaces
        startDate = (EditText) findViewById(R.id.EnterStartDate);    
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.requestFocus();
        
        endDate = (EditText) findViewById(R.id.EnterEndDate);
        endDate.setInputType(InputType.TYPE_NULL);
        
	    setDateTimeField();    
	    
        Button savebutton = (Button) findViewById(R.id.buttonsaveClaim);
		savebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				try
				{
					addClaimAction(v);
				} catch (ParseException e)
				{
					e.printStackTrace();
				} 
			}
		});
    }

	//initialize calendar view
    private void setDateTimeField() {
        startDate.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
			    fromDatePickerDialog.show();
			}
		});
        endDate.setOnClickListener(new View.OnClickListener() {
        
        	@Override
        	public void onClick(View view) {
        		toDatePickerDialog.show();   
        	}
        });
        
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(df.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        
        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(df.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
		return true;
	}
	
	
	public void addClaimAction(View v) throws ParseException{
		EditText claimname = (EditText) findViewById(R.id.EnterClaimName);
		EditText status = (EditText) findViewById(R.id.editEnterStatus);
		EditText description = (EditText) findViewById(R.id.EnterDescription);
		
		//checks that claimname is ok
		if (ClaimListController.getClaimList().getClaims().contains(claimname.getText().toString())) {
			Toast.makeText(this, "A claim with that name already exists", Toast.LENGTH_LONG).show();
		} else {
			//puts date in string format
			Date start = df.parse(startDate.getText().toString());
			Date end = df.parse(endDate.getText().toString());	

			//adds new claim, saves it, sorts it
			ClaimListController ct = new ClaimListController();
			Claim claim = new Claim(claimname.getText().toString(), start, end, status.getText().toString(), description.getText().toString());
			ct.addClaim(claim);
			ct.sort();
			ClaimListController.saveClaimList();

			Toast.makeText(this, "Claim Added",Toast.LENGTH_SHORT).show();

			//make text spaces blank for another claim if the user wishes
			claimname.setText("");
			status.setText("");
			description.setText("");
			startDate.setText("");
			endDate.setText("");

			//sets hints
			claimname.setHint("Enter Another Claim");
			status.setHint("Enter Status");
			description.setHint("Enter Description");
			startDate.setHint("Enter Start Date");
			endDate.setHint("Enter End Date");
		}
	}

}
