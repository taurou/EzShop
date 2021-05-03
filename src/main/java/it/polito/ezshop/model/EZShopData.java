package it.polito.ezshop.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class EZShopData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6489647510184707833L;
	
	public User loggedInUser;
	public HashMap<String, User> users;
	public HashMap<Integer, String> idToUsername;
	public Integer userIDs, productTypeIDs, orderIDs, customerIDs, cardIDs, saleTransactionIDs;
	public HashMap<String, Position> positions;
	public HashMap<Integer, Order> orders;
	public HashMap<Integer, Customer> customers;
	public HashMap<Integer, Card> cards;
	public LinkedHashMap<Integer, SaleTransaction> saleTransactions;
	public double balance;
	
	
	public HashMap<Integer, ProductType> productTypes;
	
	public EZShopData() {
		users = new HashMap<>();
		idToUsername = new HashMap<>();
		productTypes = new HashMap<>();
		positions = new HashMap<>();
		orders = new HashMap<Integer, Order>();
		customers = new HashMap<>();
		cards = new HashMap<>();
		saleTransactions = new LinkedHashMap<>();
		userIDs=1;
		productTypeIDs=1;
		orderIDs=1;
		customerIDs=1;
		cardIDs=1000000000;
		saleTransactionIDs=1;
	}

}
