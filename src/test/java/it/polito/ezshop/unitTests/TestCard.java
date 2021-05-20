package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.model.Card;
import it.polito.ezshop.model.Customer;

public class TestCard {
	
	Card card = new Card(123);
	
	Customer customer = new Customer("ABC",456);
	
	@Test
	public void testgetCustomer(){
		card.setCustomer(customer);
		assertTrue(card.getCustomer() == customer);
	}
	
	
	@Test
	public void testaddPoints(){                       // Problem here !!
		assertEquals(0,card.getPoints(),0.01);
		card.setCustomer(customer);
		card.addPoints(10);
		assertEquals(10,card.getPoints(),0.01);
		assertEquals(10,customer.getPoints(),0.01);
		
		card.addPoints(-5);
		assertEquals(5,card.getPoints(),0.01);
		assertEquals(5,customer.getPoints(),0.01);
	}
	
	@Test
	public void testgetCardNumber(){
		assertEquals(card.getCardNumber(), "123");
	}
	
}
