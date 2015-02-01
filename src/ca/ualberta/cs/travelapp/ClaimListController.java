package ca.ualberta.cs.travelapp;

import java.io.IOException;


public class ClaimListController
{

	private static ClaimList claimlist = null;

	static public ClaimList getClaimList() {
		if (claimlist == null) {
			try {
				claimlist = ClaimListManager.getManager().loadClaimList();
				claimlist.addListener(new Listener() {
					
					@Override
					public void update() {
						saveClaimList();
					}
				});
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
			}
		}
		return claimlist;
	}
	
	static public void saveClaimList() {
		try {
			ClaimListManager.getManager().saveClaimList(getClaimList());
		} catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
		}
	}
	
	public void sort() {
		getClaimList().sort();
	}

	public void addClaim(Claim claim) {
		getClaimList().addClaim(claim);
	}
}
