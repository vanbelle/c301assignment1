package ca.ualberta.cs.travelapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	
	public void addClaim(Claim claim) throws ClaimAlreadyExistsException {
		for (int i = 0; i < claimList.size(); i++) {
			if( claimList.get(i).getClaimName() == claim.getClaimName()) {
				throw new ClaimAlreadyExistsException();
			}
		}
		claimList.add(claim);
		notifyListeners();
	}
	
	public void removeClaim (String claimname){
		for (int i = 0; i < claimList.size() ; i++) {
			if (claimList.get(i).getClaimName() == claimname) {
				claimList.remove(i);
			}
		}
		notifyListeners();
	}

	public boolean contains(String claimname) {
		for (int i = 0; i < claimList.size(); i++) {
			if (claimList.get(i).getClaimName() == claimname) {
				return true;
			}
		}
		return false;
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

	public void sort() {
		Collections.sort(claimList, new Comparator<Claim>() {
			@Override
			public int compare(Claim lhs, Claim rhs){
				return lhs.getStartDate().compareTo(rhs.getStartDate());
			}
		});
	}
	
}
