package it.polito.ezshop.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import it.polito.ezshop.data.TicketEntry;

public class SaleTransaction implements it.polito.ezshop.data.SaleTransaction, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6782988927774381138L;
	protected Integer ticketNumber;
	public List<TicketEntry> entries;
	public HashMap<String, it.polito.ezshop.model.TicketEntry> products;
	double discountRate, price = 0;
	String status;

	/* TO TEST */

	public double calculatePrice() {
		price = (1 - discountRate) * products.values().stream()
				.mapToDouble(x -> x.getAmount() * x.getPricePerUnit() * (1 - x.getDiscountRate())).sum();
		return price;
	}



	

	/* END TO TEST */
	public boolean removeProduct(it.polito.ezshop.model.ProductType prod, Integer amount) {

		if (prod == null || !products.containsKey(prod.getBarCode()) || amount <=0)
			return false;
		
		it.polito.ezshop.model.TicketEntry t = products.get(prod.getBarCode());
		if(amount > t.getAmount())
			return false;
		// this.price -= amount * t.getPricePerUnit() * (1 - t.getDiscountRate());
		if (amount == t.getAmount()) {
			entries.remove(products.get(t.getBarCode()));
			products.remove(t.getBarCode());
		}
		
			t.addAmount(-amount);
			prod.addQuantity(+amount);
		

		return true;
	}
	public boolean addProductDelRet(it.polito.ezshop.model.ProductType prod, Integer amount) {
		if (prod == null || amount <=0)
			return false;

		if (products.containsKey(prod.getBarCode())) {

			products.get(prod.getBarCode()).addAmount(amount);

			prod.addQuantity(amount);

			return true;
		} else {
			it.polito.ezshop.model.TicketEntry entry = new it.polito.ezshop.model.TicketEntry(prod.getBarCode(),
					prod.productDescription, prod.pricePerUnit, amount);
			prod.addQuantity(amount);
			entries.add(entry);
			products.put(prod.getBarCode(), entry);
			return true;
		}
	}
	
	

	
	
	
	
	public boolean addProduct(it.polito.ezshop.model.ProductType prod, Integer amount) {
		
		if (prod == null || amount <=0)
			return false;

		if (products.containsKey(prod.getBarCode())) {
			if(prod.getQuantity()<amount)
				return false;
						products.get(prod.getBarCode()).addAmount(amount);

			prod.addQuantity(-amount);

			return true;
		} else {
			it.polito.ezshop.model.TicketEntry entry = new it.polito.ezshop.model.TicketEntry(prod.getBarCode(),
					prod.productDescription, prod.pricePerUnit, amount);
			prod.addQuantity(-amount);
			entries.add(entry);
			products.put(prod.getBarCode(), entry);
			return true;
		}
	}
	public boolean applyProductDiscount(String barcode, double discountRate) {
		it.polito.ezshop.model.TicketEntry prod = products.get(barcode);

		if(prod == null || discountRate <=0 || discountRate >1)
			return false;

		prod.setDiscountRate(discountRate);

		return true;
	}

	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SaleTransaction(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
		this.status = "OPEN";
		products = new HashMap<>();
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
