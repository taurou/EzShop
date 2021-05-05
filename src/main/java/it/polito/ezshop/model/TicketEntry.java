package it.polito.ezshop.model;

import java.io.Serializable;

public class TicketEntry implements it.polito.ezshop.data.TicketEntry, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3842826487450523546L;
	String barCode;
	String productDescription;
	double pricePerUnit;
	double discountRate=0;
	int amount;
	
	
	
	public TicketEntry(String barCode, String productDescription, double pricePerUnit, int amount) {
		super();
		this.barCode = barCode;
		this.productDescription = productDescription;
		this.pricePerUnit = pricePerUnit;
		this.amount = amount;
	}
	
	public void addAmount(Integer amount){
		this.amount+=amount;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public double getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}



}
