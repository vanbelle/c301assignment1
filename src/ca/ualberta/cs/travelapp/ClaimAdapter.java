package ca.ualberta.cs.travelapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ClaimAdapter extends BaseExpandableListAdapter{
	
	private Context ctx;
	private HashMap<String, ArrayList<Amt_Cur>> Claim_list;
	private ArrayList<String> Claims_List;
	
	public ClaimAdapter(Context ctx, HashMap<String, ArrayList<Amt_Cur>> Claim_list, ArrayList<String> Claims_List ) {
		this.ctx = ctx;
		this.Claim_list = Claim_list;
		this.Claims_List = Claims_List;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
		String group_title = (String) getGroup(parent);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.parent_layout, parentView, false);
		}
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
