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

public class AddClaimActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
		return true;
	}
	
	public void addClaimAction(View v) throws ParseException, ClaimAlreadyExistsException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		EditText claimname = (EditText) findViewById(R.id.EnterClaimName);
		EditText startDate = (EditText) findViewById(R.id.EnterStartDate);
		EditText endDate = (EditText) findViewById(R.id.EnterEndDate);
		EditText status = (EditText) findViewById(R.id.editEnterStatus);
		EditText description = (EditText) findViewById(R.id.EnterDescription);
		Date startdate = formatter.parse(startDate.getText().toString());
		Date enddate = formatter.parse(endDate.getText().toString()); 		
		ClaimList.addClaim(claimname.getText().toString(), startdate, enddate, status.getText().toString(),description.getText().toString());
	}

}
