package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.SaleTransaction;
import it.polito.ezshop.model.TicketEntry;

public class SaleTransactionIntegrationTest {

	Integer ticketNumber = 1;
	SaleTransaction transaction;
	ProductType product;
	TicketEntry tEntry;
	Integer quantity=5;
	String barcode;
	
	@Before
	public void setUp() {
	    barcode = "6291041500213";
		transaction = new SaleTransaction(ticketNumber);
		product = new ProductType("",barcode , 5.0, 5, "") ;
		tEntry = new TicketEntry(barcode, "", product.getPricePerUnit(), quantity);
		transaction.products.put(barcode, tEntry);
	}
	
	@Test
	public void addProductTest() {
		product.setQuantity(10);
		assertFalse(transaction.addProduct(product, -5));
		assertFalse(transaction.addProduct(product, 0));
		assertFalse(transaction.addProduct(null, -5));
		assertFalse(transaction.addProduct(product, 25));
		assertTrue(transaction.addProduct(product, 1));
		assertEquals(product.getQuantity(), 9, 0);
		assertEquals(tEntry.getAmount(), quantity+1);
		transaction.products.clear();
		assertTrue(transaction.addProduct(product, 1));
		assertTrue(transaction.products.containsKey(barcode));
		assertEquals(transaction.products.get(barcode).getAmount(), 1, 0);
		assertEquals(product.getQuantity(), 8, 0);
	}
	
	@Test
	public void removeProductTest() {
		assertFalse(transaction.removeProduct(null, 5));
		assertFalse(transaction.removeProduct(new ProductType("","6291041500214" , 1.0, 1, ""), 5));
		assertFalse(transaction.removeProduct(product, -5));
		assertFalse(transaction.removeProduct(product, 0));
		assertFalse(transaction.removeProduct(product,  30));
		assertTrue(transaction.removeProduct(product,  5));
		setUp();
		assertTrue(transaction.removeProduct(product,  3));	
		assertEquals(3, product.getQuantity(), 0);
		assertEquals(transaction.products.get(barcode).getAmount(), 2);
	}

	@Test
	public void applyProductDiscountTest() {
		
		assertFalse(transaction.applyProductDiscount(barcode, -1));
		assertTrue(transaction.applyProductDiscount(barcode, 0.1));
		assertFalse(transaction.applyProductDiscount(barcode, 0));
		
		
	}
}

