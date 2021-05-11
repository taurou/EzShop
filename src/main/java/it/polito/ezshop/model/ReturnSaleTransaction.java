package it.polito.ezshop.model;

public class ReturnSaleTransaction extends SaleTransaction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2697175701011876018L;
	it.polito.ezshop.model.SaleTransaction returnOfSaleTransaction;
	boolean committed;

	public ReturnSaleTransaction(Integer ticketNumber, it.polito.ezshop.model.SaleTransaction saleTransaction) {
		super(ticketNumber);
		this.returnOfSaleTransaction=saleTransaction;
	    committed=false;
	}
	
	
	/* TO TEST */
	
	public boolean addReturnProduct(it.polito.ezshop.model.TicketEntry prod, Integer amount) {

		/*
		 * it.polito.ezshop.model.TicketEntry entry = new
		 * it.polito.ezshop.model.TicketEntry(prod.getBarCode(),
		 * prod.productDescription, prod.pricePerUnit, amount); entries.add(entry);
		 * price+=prod.getPricePerUnit()*amount*(1-entry.getDiscountRate()); return
		 * true;
		 */

		
			it.polito.ezshop.model.TicketEntry entry = new it.polito.ezshop.model.TicketEntry(prod.getBarCode(),
					prod.productDescription, prod.pricePerUnit, amount, prod.getDiscountRate() );
			double money=prod.getPricePerUnit()*amount*(1-prod.getDiscountRate());
			returnOfSaleTransaction.price-=money;
			

			products.put(prod.getBarCode(), entry);
			price *=money;
			return true;
		
	}

	/* TO TEST */
	
	public boolean isCommitted() {
		return committed;
	}

	public void setCommitted(boolean committed) {
		this.committed = committed;
		
	}

	public SaleTransaction getReturnOfSaleTransaction() {
		return returnOfSaleTransaction;
	}
	
	

	

}
