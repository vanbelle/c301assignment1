package ca.ualberta.cs.travelapp;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends Activity
{
	private EditText Date;
	int claimindex;
	int itemindex;
    
    private DatePickerDialog DatePickerDialog;
    
    private SimpleDateFormat df;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.edititem);
		ClaimListManager.initManager(this.getApplicationContext());
		EIManager.initManager(this.getApplicationContext());
		
		EditText name = (EditText) findViewById(R.id.editsetItemName);
		EditText category = (EditText) findViewById(R.id.editsetItemCategory);
		EditText description = (EditText) findViewById(R.id.editsetItemDescription);
		Date = (EditText) findViewById(R.id.editsetItemDate);
		EditText amount = (EditText) findViewById(R.id.EntersetClaimStartDate);
		EditText currency = (EditText) findViewById(R.id.editsetItemCurrency);
		
		setDateTimeField();
		
		final int claimindex = getIntent().getIntExtra("claimposition", 0);
		final int itemindex = getIntent().getIntExtra("itemposition", 0);
		
		name.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getItemName());
		category.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getCategory());
		description.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getDescription());
		currency.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getAmt_Cur().getCurrency());
		amount.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getAmt_Cur().getAmount().toString());
		String date = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getDate());
		Date.setText(date);
		
        Button savebutton = (Button) findViewById(R.id.buttonsavesetItem);
		savebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				try
				{
					editItemAction(v);
				} catch (ParseException e)
				{
					e.printStackTrace();
				} catch (ClaimAlreadyExistsException e)
				{
					System.out.println("That Claim Name has already been used");
					e.printStackTrace();
				}
			}
		});
	}
	
	public void editItemAction(View v) throws ParseException, ClaimAlreadyExistsException {
		EditText name = (EditText) findViewById(R.id.editsetItemName);
		EditText category = (EditText) findViewById(R.id.editsetItemCategory);
		EditText description = (EditText) findViewById(R.id.editsetItemDescription);
		EditText amount = (EditText) findViewById(R.id.EntersetClaimStartDate);
		EditText currency = (EditText) findViewById(R.id.editsetItemCurrency);
		
		Date start = df.parse(Date.getText().toString());
		String cname = ClaimListController.getClaimList().getClaims().get(claimindex).getClaimName();
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,###.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		BigDecimal amt = (BigDecimal) decimalFormat.parse(amount.getText().toString());
		
		
		EIController EC = new EIController();
		ExpenseItem item = new ExpenseItem(cname, name.getText().toString(), category.getText().toString(), description.getText().toString(), start, amt,currency.getText().toString());
		EC.addItem(item);
		ClaimListController.saveClaimList();
		
		Toast.makeText(this, "Claim Changed",Toast.LENGTH_SHORT).show();
		
		name.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getItemName());
		category.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getCategory());
		description.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getDescription());
		currency.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getAmt_Cur().getCurrency());
		amount.setText(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getAmt_Cur().getAmount().toString());
		String date = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(ClaimListController.getClaimList().getClaims().get(claimindex).getEItems().get(itemindex).getDate());
		Date.setText(date);
	}
	

    private void setDateTimeField() {
        Date.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
			    DatePickerDialog.show();
			}
		});

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date.setText(df.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
