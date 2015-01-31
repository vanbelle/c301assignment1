package ca.ualberta.cs.travelapp;

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
	public String item = null;
	public String total = null;
	int index;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
		ClaimListManager.initManager(this.getApplicationContext());
		EIManager.initManager(this.getApplicationContext());
		
		final int index = getIntent().getIntExtra("position", 0);

		recipient = (EditText) findViewById(R.id.recipient);
		subject = (EditText) findViewById(R.id.subject);
		body = (EditText) findViewById(R.id.body);
		
		String claim = ClaimListController.getClaimList().getClaims().get(index).toString();
		//String total = ;//TODO
		for (int i = 0; i < EIController.getItemList().getItems().size(); i++){
			if (EIController.getItemList().getItems().get(i).getClaimName() == ClaimListController.getClaimList().getClaims().get(index).getClaimName())
			{
				item +=EIController.getItemList().getItems().get(index).toEmailString();
		
			}
		}
		body.setText(claim+total+item);

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
		email.setType("message/rfc822");
		email.putExtra(Intent.EXTRA_EMAIL, recipient.getText().toString());
		email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
		email.putExtra(Intent.EXTRA_TEXT, body.getText().toString());
		startActivity(Intent.createChooser(email, "Choose an Email Client :"));
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
		return true;
	}

}
