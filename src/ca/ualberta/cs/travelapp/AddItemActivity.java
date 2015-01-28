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
//TODO take from ADD claim pop up date thing
	}

}
