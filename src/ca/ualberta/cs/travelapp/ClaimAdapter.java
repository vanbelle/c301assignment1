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

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ClaimAdapter extends BaseExpandableListAdapter{
	
	private Context ctx;
	private HashMap<String, ArrayList<String>> Claim_list;
	private List<String> Claims_List;
	
	public ClaimAdapter(Context ctx, List<String> claims, HashMap<String, ArrayList<String>> claimlist ) {
		this.ctx = ctx;
		this.Claim_list = claimlist;
		this.Claims_List = claims;
	}

	@Override
	public int getGroupCount() {
		return Claims_List.size();
	}

	@Override
	public int getChildrenCount(int arg0) {
		return Claim_list.get(Claims_List.get(arg0)).size();
	}

	@Override
	public Object getGroup(int arg0) {
		return Claims_List.get(arg0);
	}

	@Override
	public Object getChild(int parent, int child) {
		return Claim_list.get(Claims_List.get(parent)).get(child);
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public long getChildId(int parent, int child) {
		return child;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(final int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
		String group_title = (String) getGroup(parent);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.parent_layout, parentView, false);
		}
        Button morebutton = (Button) convertView.findViewById(R.id.seemorebutton);
		morebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx, ItemViewActivity.class);
				intent.putExtra("position", parent);
				ctx.startActivity(intent);
			}
		});
		TextView parent_textview = (TextView) convertView.findViewById(R.id.parent_txt);
		parent_textview.setTypeface(null, Typeface.BOLD);
		parent_textview.setText(group_title);
		return convertView;
	}

	@Override
	public View getChildView(int parent, int child, boolean lastChild, View convertView, ViewGroup parentView) {
		String child_title = (String) this.getChild(parent, child);
		if(convertView == null){
			LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(R.layout.child_layout, parentView, false);			
		}
		TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
		child_textview.setText(child_title);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	
	

}
