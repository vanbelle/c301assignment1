package ca.ualberta.cs.travelapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class EditClaimActivity extends Activity
{
    private EditText startDate;
    private EditText endDate;
    
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    
    private SimpleDateFormat df;	
    
    int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.editclaim);
		ClaimListManager.initManager(this.getApplicationContext());
		EIManager.initManager(this.getApplicationContext());
		
		EditText claimname = (EditText) findViewById(R.id.EntersetClaimName);
		EditText status = (EditText) findViewById(R.id.editsetClaimStatus);
		EditText description = (EditText) findViewById(R.id.EntersetClaimDescription);
		endDate = (EditText) findViewById(R.id.EntersetClaimEndDate);
		startDate = (EditText) findViewById(R.id.EntersetClaimStartDate);
		
		final int index = getIntent().getIntExtra("claimposition", 0);
		
	    df = new SimpleDateFormat("MM/ddyyyy", Locale.US);
	        
        startDate.setInputType(InputType.TYPE_NULL);
        endDate.setInputType(InputType.TYPE_NULL);
        
	    setDateTimeField();
	    
	    claimname.setText(ClaimListController.getClaimList().getClaims().get(index).getClaimName());
		status.setText(ClaimListController.getClaimList().getClaims().get(index).getStatus());
		description.setText(ClaimListController.getClaimList().getClaims().get(index).getDescription());
		String start = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getStartDate());
		String end = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getEndDate());
		startDate.setText(start);
		endDate.setText(end);
		
        Button savebutton = (Button) findViewById(R.id.buttonsaveClaim);
		savebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				String status = ClaimListController.getClaimList().getClaims().get(index).getStatus().trim();
				if (status.equals("In Progress") || status.equals("Returned")) {
					try {
						editClaimAction(v);
					} catch (ParseException e)
					{
						e.printStackTrace();
					} 
				} else {
					onlyStatus(v);
				}
			}
		});
		

	}
	
	public void onlyStatus(View v) {
		EditText status = (EditText) findViewById(R.id.editsetClaimStatus);
		Toast.makeText(this, "Only Status is editable", Toast.LENGTH_LONG).show();
		ClaimListController.getClaimList().getClaims().get(index).setStatus(status.getText().toString());
		Toast.makeText(this, "Status Changed",Toast.LENGTH_SHORT).show();
	}
	
	public void editClaimAction(View v) throws ParseException{
		EditText claimname = (EditText) findViewById(R.id.EntersetClaimName);
		EditText status = (EditText) findViewById(R.id.editsetClaimStatus);
		EditText description = (EditText) findViewById(R.id.EntersetClaimDescription);
		EditText endDate = (EditText) findViewById(R.id.EntersetClaimEndDate);
		EditText startDate = (EditText) findViewById(R.id.EntersetClaimStartDate);
		df =new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		
		String oldName = ClaimListController.getClaimList().getClaims().get(index).getClaimName();
		Date start = df.parse(startDate.getText().toString());
		Date end = df.parse(endDate.getText().toString());	
		
		ClaimListController ct = new ClaimListController();
		Claim claim = new Claim(claimname.getText().toString(), start, end, status.getText().toString(), description.getText().toString());

		if (ClaimListController.getClaimList().getClaims().contains(claimname.getText().toString())) {
			Toast.makeText(this, "A claim with that name already exists", Toast.LENGTH_LONG).show();
		} else {
			ClaimListController.getClaimList().getClaims().remove(index);
			if (!claim.getClaimName().equals(oldName)){
				for (int i = 0; i < EIController.getItemList().getItems().size(); i++){
					if (EIController.getItemList().getItems().get(i).getClaimName().equals(oldName)){
						EIController.getItemList().getItems().get(i).setClaimName(claim.getClaimName());
					}
				}
			}
			ct.addClaim(claim);
			ct.sort();
			ClaimListController.saveClaimList();

			Toast.makeText(this, "Claim Changed",Toast.LENGTH_SHORT).show();

			claimname.setText(ClaimListController.getClaimList().getClaims().get(index).getClaimName());
			status.setText(ClaimListController.getClaimList().getClaims().get(index).getStatus());
			description.setText(ClaimListController.getClaimList().getClaims().get(index).getDescription());
			String start2 = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getStartDate());
			String end2 = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(index).getEndDate());
			startDate.setText(start2);
			endDate.setText(end2);
		}
	}

	

    private void setDateTimeField() {
        startDate.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
			    fromDatePickerDialog.show();
			}
		});
        endDate.setOnClickListener(new View.OnClickListener() {
        
        	@Override
        	public void onClick(View view) {
        		toDatePickerDialog.show();   
        	}
        });
        
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(df.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        
        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(df.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}
