package ca.ualberta.cs.travelapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ItemViewActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_expenseitems);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_view, menu);
		return true;
	}

}
