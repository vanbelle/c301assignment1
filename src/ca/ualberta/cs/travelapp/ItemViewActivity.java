package ca.ualberta.cs.travelapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
		EIManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
		index = getIntent().getIntExtra("position", 0);
		String string = ClaimListController.getClaimList().getClaims().get(index).getClaimName()+" - "+ClaimListController.getClaimList().getClaims().get(index).getStatus();
		
		TextView displayName = (TextView) findViewById(R.id.textClaimName);
		displayName.setText(string);
		
		TextView displaydescription = (TextView) findViewById(R.id.textClaimDescription);
		String s = ClaimListController.getClaimList().getClaims().get(index).getDescription();
		displaydescription.setText(s);
		
		TextView displayDate = (TextView) findViewById(R.id.textStarttoEndDate);
		String startdate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getStartDate());
		String endDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getEndDate());
		displayDate.setText(startdate+" - "+endDate);
		
		
		//add new expense item button
        Button itembutton = (Button) findViewById(R.id.AddExpenseItemButton);
		itembutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemViewActivity.this, AddItemActivity.class);
				intent.putExtra("position", index);
				startActivity(intent);
			}
		});
		
		//edit button
        Button editbutton = (Button) findViewById(R.id.buttonedit);
		editbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemViewActivity.this, EditClaimActivity.class);
				intent.putExtra("position", index);
				startActivity(intent);
			}
		});
		
		//email button
        Button emailbutton = (Button) findViewById(R.id.buttonemail);
		emailbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemViewActivity.this, EmailActivity.class);
				intent.putExtra("position", index);
				startActivity(intent);
			}
		});	
		
		//Expense Item List
		ListView listview = (ListView) findViewById(R.id.listExpenseItems);
		ArrayList<ExpenseItem> list = ClaimListController.getClaimList().getClaims().get(index).getEItems();
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
	
		//Total List
		ListView totalview = (ListView) findViewById(R.id.listTotalSum);
		ArrayList<Amt_Cur> total = ClaimListController.getClaimList().getClaims().get(index).getTotalSum();
		ArrayAdapter<Amt_Cur> totalAdapter = new ArrayAdapter<Amt_Cur>(this, android.R.layout.simple_list_item_1, total);
		totalview.setAdapter(totalAdapter);
		
		//Delete Button
		Button deletebutton = (Button) findViewById(R.id.buttondelete);
		deletebutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(ItemViewActivity.this);
				adb.setMessage("Delete this Claim?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String name = ClaimListController.getClaimList().getClaims().get(index).getClaimName();
						ClaimListController.getClaimList().removeClaim(name);
						for (int i = 0; i < EIController.getItemList().size(); i++) {
							if (EIController.getItemList().getItems().get(i).getClaimName() == name) {
								EIController.getItemList().getItems().remove(i);
							}
						}
						Intent intent = new Intent(ItemViewActivity.this, MainActivity.class);
						startActivity(intent);
					}
				});
				adb.setNegativeButton("Cancel", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				adb.show();
			}
		});
	
	}
	
	public void onResume() {
		super.onResume();
		ListView EIlistview = (ListView) findViewById(R.id.listExpenseItems);
		ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
		final ArrayList<ExpenseItem> EI = new ArrayList<ExpenseItem>();
		String claim = ClaimListController.getClaimList().getClaims().get(index).getClaimName();
		
		for (int i = 0; i < EIC.size(); i++){
			if(EIC.get(i).getClaimName() == claim) {
				EI.add(EIC.get(i));
			}
		}
				
		final ArrayAdapter<ExpenseItem> ItemAdapter = new ArrayAdapter<ExpenseItem>(this, android.R.layout.simple_list_item_1, EI);
		EIlistview.setAdapter(ItemAdapter);
		EIController.getItemList().addListener(new Listener() {
			@Override
			public void update() {
				EI.clear();
				
				ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
				final ArrayList<ExpenseItem> EI = new ArrayList<ExpenseItem>();
				String claim = ClaimListController.getClaimList().getClaims().get(index).getClaimName();
				
				for (int i = 0; i < EIC.size(); i++){
					if(EIC.get(i).getClaimName() == claim) {
						EI.add(EIC.get(i));
					}
				}
				ItemAdapter.notifyDataSetChanged();
			}
		});
		
		
//		ListView TSlistview = (ListView) findViewById(R.id.listTotalSum);
//		Collection<Amt_Cur> TSC = ClaimListController.getClaimList().getClaims().get(index).getTotalSum();
//		final ArrayList<Amt_Cur> TS = new ArrayList<Amt_Cur>(TSC);
//		final ArrayAdapter<Amt_Cur> TSAdapter = new ArrayAdapter<Amt_Cur>(this, android.R.layout.simple_list_item_1, TS);
//		TSlistview.setAdapter(TSAdapter);
	}
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_view, menu);
		return true;
	}

}
