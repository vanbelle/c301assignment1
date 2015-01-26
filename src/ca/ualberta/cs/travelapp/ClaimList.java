package ca.ualberta.cs.travelapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ClaimList {
	protected static ArrayList<Claim> claimList;
	
	public static HashMap<String, ArrayList<Amt_Cur>> getInfo(){
		HashMap<String, ArrayList<Amt_Cur>> claimlistdetails = new HashMap<String, ArrayList<Amt_Cur>>();
		for (int i = 0; i < claimList.size(); i++) {
			claimlistdetails.put(claimList.get(i).getClaimName()+'-'+claimList.get(i).getStatus(), claimList.get(i).getTotalSum());
		}
		return claimlistdetails;
	}
	
	public ClaimList() {
		claimList = new ArrayList<Claim>();				
	}

	public static ArrayList<Claim> getClaims() {
		return claimList;
	}
	
	public static void addClaim(String ClaimName,  Date StartDate, Date EndDate, String Status, String Description) throws ClaimAlreadyExistsException {
		for (int i = 0; i < claimList.size(); i++) {
			if( claimList.get(i).getClaimName() == ClaimName) {
				throw new ClaimAlreadyExistsException();
			}
		}
		Claim claim = new Claim(ClaimName, StartDate, EndDate, Status,Description);
		claimList.add(claim);
	}
	
	public void removeClaim (String claimname){
		for (int i = 0; i < claimList.size() ; i++) {
			if (claimList.get(i).getClaimName() == claimname) {
				claimList.remove(i);
			}
		}
	}	
}
