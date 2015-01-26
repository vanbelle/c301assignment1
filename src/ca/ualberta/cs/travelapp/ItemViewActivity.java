package ca.ualberta.cs.travelapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ItemViewActivity extends Activity {
	int index;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_expenseitems);
		index = getIntent().getIntExtra("position", 0);
		
		TextView displayName = (TextView) findViewById(R.id.textClaimName);
		displayName.setText(ClaimList.getClaims().get(index).getClaimName()+" ("+ClaimList.getClaims().get(index).getStatus()+")");
		
		TextView displaydescription= (TextView) findViewById(R.id.textItemDescription);
		displaydescription.setText(ClaimList.getClaims().get(index).getDescription());
		
		TextView displayDate = (TextView) findViewById(R.id.textDate);
		displayDate.setText(ClaimList.getClaims().get(index).getStartDate()+" - "+ClaimList.getClaims().get(index).getEndDate());
		
		//add new expense item button
        Button itembutton = (Button) findViewById(R.id.AddExpenseItemButton);
		itembutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemViewActivity.this, AddItemActivity.class);
				startActivity(intent);				
			}
		});
		
		//Expense Item List
		ListView listview = (ListView) findViewById(R.id.listExpenseItems);
		ArrayList<ExpenseItem> list = ClaimList.getClaims().get(index).getEItems();
		ArrayAdapter<ExpenseItem> itemAdapter = new ArrayAdapter<ExpenseItem>(this, android.R.layout.simple_list_item_1, list);
		listview.setAdapter(itemAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ItemViewActivity.this, ExpenseItemActivity.class);
				intent.putExtra("claimposition", index);
				intent.putExtra("itemposition", position);
				startActivity(intent);
				
			}
		});
    }
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_view, menu);
		return true;
	}

}
