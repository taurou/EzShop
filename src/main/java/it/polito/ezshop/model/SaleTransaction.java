package it.polito.ezshop.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.polito.ezshop.data.TicketEntry;

public class SaleTransaction implements it.polito.ezshop.data.SaleTransaction, Serializable {
	
	protected Integer ticketNumber ;
	public List<TicketEntry> entries;
	//public HashMap<String, it.polito.ezshop.model.TicketEntry> products;
	double discountRate, price=0;
	String status;
	
	
	public boolean applyProductDiscount(String barcode) {
		
		/*it.polito.ezshop.model.TicketEntry prod = products.get(barcode);
		
		double d = prod.getDiscountRate();
		Integer q = prod.getAmount();
		double p = prod.getPricePerUnit();
		price-=d*q*p;*/
		
		return true;
	}
	
	public boolean addProduct(it.polito.ezshop.model.ProductType prod, Integer amount) {
		
		it.polito.ezshop.model.TicketEntry entry = new it.polito.ezshop.model.TicketEntry(prod.getBarCode(), prod.productDescription, prod.pricePerUnit, amount);
		entries.add(entry);
		price+=prod.getPricePerUnit()*amount*(1-entry.getDiscountRate());
		return true;
		
		
		/*if(products.containsKey(prod.getBarCode())) {
			products.get(prod.getBarCode()).addAmount(amount);
			prod.addQuantity(-amount);
			price+=prod.getPricePerUnit()*amount*(1-products.get(prod.getBarCode()).getDiscountRate());
			return true;
		}else {
		it.polito.ezshop.model.TicketEntry entry = new it.polito.ezshop.model.TicketEntry(prod.getBarCode(), prod.productDescription, prod.pricePerUnit, amount);
		
		prod.addQuantity(-amount);
		products.put(prod.getBarCode(), entry);
		entries.add(entry);
		price+=prod.getPricePerUnit()*amount;
		return true;
		}*/
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public SaleTransaction(Integer ticketNumber) {
		this.ticketNumber=ticketNumber;
		this.status="OPEN";
		//products = new HashMap<>();
		entries = new LinkedList<>();
	}
	public Integer getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public List<TicketEntry> getEntries() {
		return entries;
	}
	public void setEntries(List<TicketEntry> entries) {
		this.entries = entries;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	
}
