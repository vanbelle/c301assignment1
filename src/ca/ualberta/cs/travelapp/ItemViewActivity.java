package ca.ualberta.cs.travelapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemViewActivity extends Activity {
	int index;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_expenseitems);
		index = getIntent().getIntExtra("position", 0);
		String string = ClaimListController.getClaimList().getClaims().get(index).getClaimName()+" - "+ClaimListController.getClaimList().getClaims().get(index).getStatus();
		
		TextView displayName = (TextView) findViewById(R.id.textClaimName);
		displayName.setText(string);
		
		TextView displaydescription = (TextView) findViewById(R.id.textClaimDescription);
		String s = ClaimListController.getClaimList().getClaims().get(index).getDescription();
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
		displaydescription.setText(ClaimListController.getClaimList().getClaims().get(index).getDescription());
		
		TextView displayDate = (TextView) findViewById(R.id.textStarttoEndDate);
		String startdate = new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getStartDate());
		String endDate = new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getEndDate());
		displayDate.setText(startdate+" - "+endDate);
		
		
		//add new expense item button
        Button itembutton = (Button) findViewById(R.id.AddExpenseItemButton);
		itembutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemViewActivity.this, AddItemActivity.class);
				startActivity(intent);				
			}
		});
//		
//		//Expense Item List
//		ListView listview = (ListView) findViewById(R.id.listExpenseItems);
//		ArrayList<ExpenseItem> list = ClaimListController.getClaimList().getClaims().get(index).getEItems();
//		ArrayAdapter<ExpenseItem> itemAdapter = new ArrayAdapter<ExpenseItem>(this, android.R.layout.simple_list_item_1, list);
//		listview.setAdapter(itemAdapter);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent intent = new Intent(ItemViewActivity.this, ExpenseItemActivity.class);
//				intent.putExtra("claimposition", index);
//				intent.putExtra("itemposition", position);
//				startActivity(intent);
//				
//			}
//		});
//	
//	//Total List
//	ListView totalview = (ListView) findViewById(R.id.listTotalSum);
//	ArrayList<Amt_Cur> total = ClaimListController.getClaimList().getClaims().get(index).getTotalSum();
//	ArrayAdapter<Amt_Cur> totalAdapter = new ArrayAdapter<Amt_Cur>(this, android.R.layout.simple_list_item_1, total);
//	totalview.setAdapter(totalAdapter);
	
	}
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_view, menu);
		return true;
	}

}
