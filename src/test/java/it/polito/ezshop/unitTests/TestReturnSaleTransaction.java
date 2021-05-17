package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.ReturnSaleTransaction;
import it.polito.ezshop.model.SaleTransaction;
import it.polito.ezshop.model.TicketEntry;


public class TestReturnSaleTransaction {
	
	/*	ADD THIS IN ReturnSaleTransaction FOR BLACKBOX TESTING
  	if(amount<=0)
		return false;
	if(!(returnOfSaleTransaction.products.containsKey(te.getBarCode())))
		return false;
	 */
	
	ReturnSaleTransaction returntransaction = new ReturnSaleTransaction( 28, new SaleTransaction(28));
	
	@Test
	public void testAddReturnProduct(){
		assertEquals(0,returntransaction.getPrice(),0.01);
		returntransaction.addReturnProduct(new TicketEntry("6291041500213","olio",10,5), 5);
		assertEquals(50,returntransaction.getPrice(),0.01);	// What's the meaning of line 37 and line 41 in ReturnSaleTransaction?
	}
	
	
	ProductType producttype = new ProductType("firstproduct", "6291041500213", (double) 10, 1, "olio");
	
	SaleTransaction saletransaction = new SaleTransaction(28);
	
	TicketEntry ticket = new TicketEntry("6291041500213","olio",10,5);
	
	@Test
	public void testAddReturnProductNegativeAmount(){
		ReturnSaleTransaction returnsaletransaction = new ReturnSaleTransaction(28, saletransaction);
		producttype.addQuantity(5);
		returnsaletransaction.returnOfSaleTransaction.addProduct(producttype, 5);
		assertFalse(returnsaletransaction.addReturnProduct(ticket, -5));
	}
	
	@Test
	public void testAddReturnProductCorrectAmount(){
		ReturnSaleTransaction returnsaletransaction = new ReturnSaleTransaction(28, saletransaction);
		returnsaletransaction.returnOfSaleTransaction.addProduct(producttype, 5);
		producttype.addQuantity(5);
		returnsaletransaction.returnOfSaleTransaction.addProduct(producttype, 5);
		assertTrue(returnsaletransaction.addReturnProduct(ticket, 5)); 
	}
	
	@Test
	public void testAddReturnProduct2(){
		saletransaction.addProduct(producttype, 10);
		ReturnSaleTransaction returnsaletransaction = new ReturnSaleTransaction(28, saletransaction);
		
		assertTrue(returnsaletransaction.addReturnProduct(ticket, 5));
	}
	
	@Test 
	public void testIsCommitted() {
		ReturnSaleTransaction returnsaletransaction = new ReturnSaleTransaction(28, saletransaction);
		returnsaletransaction.setCommitted(true);
		assertTrue(returnsaletransaction.isCommitted());
	}
	

}
 