package ca.ualberta.cs.travelapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ClaimListController
{

	private static ClaimList claimlist = null;
	
	
	
	public HashMap<String, ArrayList<Amt_Cur>> getInfo() {
		HashMap<String, ArrayList<Amt_Cur>> claimlistdetails = new HashMap<String, ArrayList<Amt_Cur>>();
		return claimlistdetails;
		//TODO fix hashmap
	}

	static public ClaimList getClaimList() {
		if (claimlist == null) {
			try {
				claimlist = ClaimListManager.getManager().loadClaimList();
				claimlist.addListener(new Listener() {
					
					@Override
					public void update() {
						saveStudentList();
					}
				});
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize StudentList from StudentListManager");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize StudentList from StudentListManager");
			}
		}
		return claimlist;
	}
	
	static public void saveStudentList() {
		try {
			ClaimListManager.getManager().saveClaimList(getClaimList());
		} catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Could not deserialize StudentList from StudentListManager");
		}
	}

	public void addClaim(String ClaimName, String Description, Date StartDate, Date EndDate, String Status) throws ClaimAlreadyExistsException {
		getClaimList().addClaim(ClaimName, StartDate, EndDate, Status, Description);
	}

}
