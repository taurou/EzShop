package it.polito.ezshop.model;

import java.io.Serializable;

public class ProductType implements it.polito.ezshop.data.ProductType, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7337288552175743339L;
	Integer quantity;
	String location;
	String note;
	String productDescription;
	String barCode;
	Double pricePerUnit;
	Integer id;
	
	public ProductType(java.lang.String note, java.lang.String barCode, Double pricePerUnit, Integer id,
			java.lang.String productdescription) {
		super();
		this.note = note;
		this.barCode = barCode;
		this.pricePerUnit = pricePerUnit;
		this.id = id;
		this.productDescription = productdescription;
		this.location=null;
		this.quantity=0;
	}
	
	/* TO TEST */
	
	public Integer addQuantity(Integer quantity) {
		if(quantity != null) {
		this.quantity+=quantity;
		return this.quantity;
		}
		return this.quantity;
	
	}

	/* END */
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public Double getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
