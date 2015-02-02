package ca.ualberta.cs.travelapp;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends Activity
{

	private EditText recipient;
	private EditText subject;
	private EditText body;
	public String item;
	public String total;
	int index;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
		ClaimListManager.initManager(this.getApplicationContext());
		EIManager.initManager(this.getApplicationContext());
		//TSManager.initManager(this.getApplicationContext());
		
		final int index = getIntent().getIntExtra("claimposition", 0);

		recipient = (EditText) findViewById(R.id.recipient);
		subject = (EditText) findViewById(R.id.subject);
		body = (EditText) findViewById(R.id.body);
		
		String claim = ClaimListController.getClaimList().getClaims().get(index).toString();
		

		ArrayList<ExpenseItem> EIC = EIController.getItemList().getItems();
		ArrayList<ExpenseItem> EI = new ArrayList<ExpenseItem>();
		String c = ClaimListController.getClaimList().getClaims().get(index).getClaimName();
		
		for (int i = 0; i < EIC.size(); i++){
			if(EIC.get(i).getClaimName().equals(c)) {
				EI.add(EIC.get(i));
				if (item == null) {
					item = EIC.get(i).toEmailString();
				} else {
					item += EIC.get(i).toEmailString();
				}
			}
		}

		TotalSum amounts = new TotalSum();
		amounts.getTotalSum(EI);
		for (int i = 0; i < amounts.size(); i++) {
			if (total == null) {
				total = amounts.get(i).toString()+"\n";
			} else {
				total += amounts.get(i).toString()+"\n";
			}
		}

		body.setText(claim+"Total:"+"\n"+total+"\n"+item);

		Button sendBtn = (Button) findViewById(R.id.sendEmail);
		sendBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				sendEmail();
				// after sending the email, clear the fields
				Toast.makeText(EmailActivity.this,"Email sent", Toast.LENGTH_SHORT).show();
			}
		});
	}

	protected void sendEmail() {

		Intent email = new Intent(Intent.ACTION_SEND);
		// prompts email clients only
		String uriText = "mailto:"+Uri.encode(recipient.getText().toString())+"?subject="+Uri.encode(subject.getText().toString())+"&body="+Uri.encode(body.getText().toString());
		Uri uri = Uri.parse(uriText);
		email.setData(uri);
		startActivity(Intent.createChooser(email, "Send mail ..."));
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
		return true;
	}

}
