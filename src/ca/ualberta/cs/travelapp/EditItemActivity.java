package ca.ualberta.cs.travelapp;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class EditItemActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.edititem);
		
		int claimindex = getIntent().getIntExtra("claimposition", 0);
		int itemindex = getIntent().getIntExtra("itemposition", 0);
		
		EditText name = (EditText) findViewById(R.id.editsetItemName);
		EditText category = (EditText) findViewById(R.id.editsetItemCategory);
		EditText description = (EditText) findViewById(R.id.editsetItemDescription);
		EditText Date = (EditText) findViewById(R.id.editsetItemDate);
		EditText amount = (EditText) findViewById(R.id.EntersetClaimStartDate);
		EditText currency = (EditText) findViewById(R.id.editsetItemCurrency);
		
		name.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getItemName());
		category.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getCategory());
		description.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getDescription());
		currency.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getAmt_Cur().getCurrency());
		amount.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getAmt_Cur().getAmount().toString());
		String date = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getDate());
		Date.setText(date);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
