/*
Travel App: Keeps tracks of expenses and claims for various trips.

Copyright [2015] Sarah Van Belleghem vanbelle@ualberta.ca
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package ca.ualberta.cs.travelapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;


public class MainActivity extends Activity implements View.OnClickListener{
	HashMap<String, ArrayList<String>> claimlist;
	List<String> claims;
	ExpandableListView Exp_List;
	ClaimAdapter listAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClaimListManager.initManager(this.getApplicationContext());
        
        //for expandable list
        Exp_List = (ExpandableListView) findViewById(R.id.listofClaimItems);
        prepareListData();//the get info stuff
        listAdapter = new ClaimAdapter(this, claims, claimlist);
        Exp_List.setAdapter(listAdapter);
        
        //add a listener
//        ClaimListController.getClaimList().addListener(new Listener()
//		{		
//			@Override
//			public void update()
//			{
//				claimlist.clear();
//				//claims.clear();
//				prepareListData();
//				listAdapter.notifyDataSetChanged();			
//			}
//		});
        
        //to initialize click-ability for claim button
        Button claimbutton = (Button) findViewById(R.id.AddClaimButton);
		claimbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddClaimActivity.class);
				startActivity(intent);				
			}
		});
		
		
    }
    
    private void prepareListData() {
        claims = new ArrayList<String>();
        claimlist = new HashMap<String, ArrayList<String>>();
        ClaimListController.getClaimList();
    
         //Adding parent data
        for (int i = 0; i < ClaimListController.getClaimList().size(); i++){
        	String string = ClaimListController.getClaimList().getClaims().get(i).getClaimName()+" - "+ClaimListController.getClaimList().getClaims().get(i).getStatus();
        	claims.add(string);
        	// Adding child data
        	ArrayList<String> children = new ArrayList<String>();
        	for (int j = 0; i < ClaimListController.getClaimList().getClaims().get(i).getTotalSum().size(); j++){
        		children.add(ClaimListController.getClaimList().getClaims().get(i).getTotalSum().get(j).toString());
        	}
        	claimlist.put(claims.get(i), children); // Header, Child data
        }
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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



	@Override
	public void onClick(View v)
	{

		// TODO Auto-generated method stub
		
	}
}
