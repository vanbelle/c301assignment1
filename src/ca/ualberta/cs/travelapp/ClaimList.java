package ca.ualberta.cs.travelapp;

import java.util.ArrayList;

public class ClaimList {
	protected ArrayList<String> claimList;
	
	public ClaimList() {
		claimList = new ArrayList<String>();				
	}

	public ArrayList<String> getClaims() {
		return claimList;
	}
	
	public void addClaim(String claimname) {
		claimList.add(claimname);
	}
	
	public void removeClaim (String claimname){
		claimList.remove(claimname);
	}	
}
