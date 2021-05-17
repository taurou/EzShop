package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import it.polito.ezshop.model.TicketEntry;

public class TestTicketEntry {
	
	 TicketEntry ticket = new TicketEntry("6291041500213","olio",10,5);
	
	@Test
	public void testSetDiscountRate(){ 
		assertEquals(0,ticket.getDiscountRate(),0.01);
		ticket.setDiscountRate(10);
		assertEquals(10,ticket.getDiscountRate(),0.01);
		ticket.setDiscountRate(20);
		assertNotEquals(20,ticket.getDiscountRate(),0.01);
		assertEquals(10,ticket.getDiscountRate(),0.01);
	}
	
	@Test
	public void testAmount() {
		assertEquals(5,ticket.getAmount(),0.01);
		
		ticket.addAmount(10);
		assertEquals(15,ticket.getAmount(),0.01);
		
		ticket.addAmount(-5);
		assertEquals(10,ticket.getAmount(),0.01);
		
		ticket.setAmount(30);
		assertEquals(30,ticket.getAmount(),0.01);
	}
	
	@Test
	public void testSetters() {
		ticket.setBarCode("1234");
		ticket.setProductDescription("birra");
		ticket.setPricePerUnit(11);
		assertEquals(ticket.getBarCode(),"1234");
		assertEquals(ticket.getProductDescription(),"birra");
		assertEquals(ticket.getPricePerUnit(),11,0.01);
	}

}
