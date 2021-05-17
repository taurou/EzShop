package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.model.User;

public class TestUser {
	
	User user = new User(1,"Mario","psw","Cashier");
	
	@Test
	public void testIsAdmin1(){
		assertFalse(user.isAdmin());
	}
	
	@Test
	public void testIsAdmin2(){
		user.setRole("Administrator");
		assertTrue(user.isAdmin());
	}
	
	@Test public void  testGettersAndSetters() {
		user.setId(1);
		user.setPassword("psw");
		user.setRole("Cashier");
		user.setUsername("Mario");
		assertEquals(user.getId(), 1, 0);
		assertEquals(user.getPassword(), "psw");
		assertEquals(user.getRole(), "Cashier");
		assertEquals(user.getUsername(), "Mario");
		
	}
	
	 
}
