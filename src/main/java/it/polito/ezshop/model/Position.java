package it.polito.ezshop.model;

import java.io.Serializable;

import it.polito.ezshop.data.ProductType;

public class Position implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4834154879739494091L;
	private ProductType product;
	private String position;

	public Position(String position) {
		this.position=position;
	}

	public ProductType getProduct() {
		return product;
	}

	public void setProduct(ProductType product) {
		this.product = product;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
