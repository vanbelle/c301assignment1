package ca.ualberta.cs.travelapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddItemActivity extends Activity
{
	int i;
	//TODO get i
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_expense_item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}
	
	public void addClaimAction(View v) throws ParseException, ClaimAlreadyExistsException, NumberFormatException, WrongStatusException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		EditText itemname = (EditText) findViewById(R.id.editEnterItemName);
		EditText Date = (EditText) findViewById(R.id.editEnterDate);
		EditText category = (EditText) findViewById(R.id.editEnterCategory);
		EditText amount = (EditText) findViewById(R.id.editEnterAmount);
		EditText currency = (EditText) findViewById(R.id.editEnterCurr);
		EditText description = (EditText) findViewById(R.id.editEnterDescription);
		Date date = formatter.parse(Date.getText().toString());		
		try
		{
			ClaimList.getClaims().get(i).addItem(itemname.getText().toString(), category.getText().toString(), description.getText().toString(), date, Integer.parseInt(amount.getText().toString()), currency.getText().toString());
		} catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
