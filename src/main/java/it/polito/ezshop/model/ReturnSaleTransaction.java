package it.polito.ezshop.model;

public class ReturnSaleTransaction extends SaleTransaction {
	
	it.polito.ezshop.model.SaleTransaction returnOfSaleTransaction;
	boolean committed;

	public ReturnSaleTransaction(Integer ticketNumber, it.polito.ezshop.model.SaleTransaction saleTransaction) {
		super(ticketNumber);
		this.returnOfSaleTransaction=saleTransaction;
	    committed=false;
	}

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
