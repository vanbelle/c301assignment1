package ca.ualberta.cs.travelapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ExpenseItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_item);
		
		int claimindex = getIntent().getIntExtra("claimposition", 0);
		int itemindex = getIntent().getIntExtra("itemposition", 0);
		
		TextView displayName = (TextView) findViewById(R.id.textItemName);
		displayName.setText(ClaimList.getClaims().get(claimindex).getEItems().get(itemindex).getItemName());
		
		TextView displaydescription= (TextView) findViewById(R.id.textItemDescription);
		displaydescription.setText(ClaimList.getClaims().get(claimindex).getEItems().get(itemindex).getDescription());	
		
		TextView displayDate = (TextView) findViewById(R.id.textDate);
		displayDate.setText(ClaimList.getClaims().get(claimindex).getEItems().get(itemindex).getDate().toString());
		
		TextView displayCat = (TextView) findViewById(R.id.textCategory);
		displayCat.setText(ClaimList.getClaims().get(claimindex).getEItems().get(itemindex).getCategory());
		
		TextView displayAmt = (TextView) findViewById(R.id.textItemAmount);
		displayAmt.setText(ClaimList.getClaims().get(claimindex).getEItems().get(itemindex).getAmt_Cur().getAmount());
		
		TextView displayCur = (TextView) findViewById(R.id.textItemCurrency);
		displayCur.setText(ClaimList.getClaims().get(claimindex).getEItems().get(itemindex).getAmt_Cur().getCurrency());
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
