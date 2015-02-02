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
	
	public void addClaim(Claim claim) {
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
			if (claimList.get(i).getClaimName().equals(claimname)) {
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

	//sorts by the start date of a claim
	public void sort() {
		Collections.sort(claimList, new Comparator<Claim>() {
			@Override
			public int compare(Claim lhs, Claim rhs){
				return lhs.getStartDate().compareTo(rhs.getStartDate());
			}
		});
	}
	
}
