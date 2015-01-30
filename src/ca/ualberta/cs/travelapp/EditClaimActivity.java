package ca.ualberta.cs.travelapp;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class EditClaimActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.editclaim);
		
		int index = getIntent().getIntExtra("claimposition", 0);
		
		EditText claimname = (EditText) findViewById(R.id.EntersetClaimName);
		EditText status = (EditText) findViewById(R.id.editsetClaimStatus);
		EditText description = (EditText) findViewById(R.id.EntersetClaimDescription);
		EditText startDate = (EditText) findViewById(R.id.EntersetClaimEndDate);
		EditText endDate = (EditText) findViewById(R.id.EntersetClaimStartDate);
		
		claimname.setText(ClaimListController.getClaimList().getClaims().get(index).getClaimName());
		status.setText(ClaimListController.getClaimList().getClaims().get(index).getStatus());
		description.setText(ClaimListController.getClaimList().getClaims().get(index).getDescription());
		String start = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getStartDate());
		String end = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getEndDate());
		startDate.setText(start);
		endDate.setText(end);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_claim, menu);
		return true;
	}

}
