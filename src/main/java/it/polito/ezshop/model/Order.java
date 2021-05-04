package it.polito.ezshop.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements it.polito.ezshop.data.Order, Serializable {
	Integer balanceId;
	String productCode;
	it.polito.ezshop.model.ProductType product;
	double pricePerUnit;
	int quantity;
	String status;
	Integer orderId;
	BalanceOperation operation;
	
	

	public Integer getBalanceId() {
		return balanceId;
	}

	public Order(it.polito.ezshop.model.ProductType product, double pricePerUnit, int quantity, Integer orderId, String status, LocalDate date) {
		super();
		this.product = product;
		this.productCode = this.product.getBarCode();
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
		this.orderId = orderId;
		this.status=status;
	}

	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	
}
