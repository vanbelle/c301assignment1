package ca.ualberta.cs.travelapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ClaimList implements Serializable {
	
	protected transient ArrayList<Listener> listeners = null;
	private static final long serialVersionUID = -1730153377529914363L;
	protected ArrayList<Claim> claimList = null;
	
	public ClaimList() {
		claimList = new ArrayList<Claim>();	
		listeners = new ArrayList<Listener>();
	}

	public ArrayList<Claim> getClaims() {
		return claimList;
	}
	
	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	
	public void addClaim(String ClaimName,  Date StartDate, Date EndDate, String Status, String Description) throws ClaimAlreadyExistsException {
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

	public int size()
	{
		return claimList.size();
	}
	
	public void addListener(Listener l) {
		getListeners().add(l);
	}
	
	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}
	
	public void removeListener(Listener l) {
	 	getListeners().remove(l);
	}
	
}
