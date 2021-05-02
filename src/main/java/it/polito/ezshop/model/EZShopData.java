package it.polito.ezshop.model;

import java.io.Serializable;
import java.util.HashMap;

public class EZShopData implements Serializable {
	
	public User loggedInUser;
	public HashMap<String, User> users;
	public Integer userIDs;
	
	public EZShopData() {
		users = new HashMap<>();
		userIDs=0;
	}

}
