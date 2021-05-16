package it.polito.ezshop.unitTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.model.SaleTransaction;
import it.polito.ezshop.model.TicketEntry;

public class SaleTransactionUnitTest {
   
	
	Integer ticketNumber = 1;
	SaleTransaction transaction = new SaleTransaction(ticketNumber);
	
	@Before
	public void setUp() {
		
		transaction = new SaleTransaction(ticketNumber);
		 
	}

	@Test public void ConstructorTest() {
		
		assertEquals(transaction.getStatus(), "OPEN");
		assertEquals(transaction.getTicketNumber(), ticketNumber);
		assertEquals(transaction.getDiscountRate(), 0, 0);
		assertEquals(transaction.getDiscountRate(), 0, 0);
		assertEquals(transaction.getEntries().size(), 0);
		assertEquals(transaction.getPrice(), 0, 0);
		assertEquals(transaction.products.isEmpty(), true);
		
		
	}
	
	@Test
	public void testCalculatePrice() {
		
		
		transaction.products.put("1", new TicketEntry("1", "", 5, 1, 0.50));
		transaction.products.put("2", new TicketEntry("2", "", 6, 2));
		transaction.products.put("3", new TicketEntry("3", "", 7, 3));
		transaction.products.put("4", new TicketEntry("4", "", 8, 4));
		assertEquals(67.5, transaction.calculatePrice(), 0.0001);
		transaction.products.put("5", new TicketEntry("5", "", 8, 4, 0.75));
		assertEquals(75.5, transaction.calculatePrice(), 0.0001);
		transaction.setDiscountRate(0.5);
		assertEquals(37.75, transaction.calculatePrice(), 0.0001);
	}
	
	@Test
	public void testGettersAndSetters() {
		transaction.setPrice(5);
		transaction.setStatus("OPEN");
		transaction.setTicketNumber(1);
		//transaction.setEntries(new List<TicketEntry>);
	}
	


}
