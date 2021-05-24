package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class StartSaleTransactionIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;	
	
	@Before 
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.login("admin", "adminpass");

	}
	@Test
	public void startTest() throws UnauthorizedException {
		Integer tID = shop.data.saleTransactionIDs; 
		
		assertFalse(shop.data.saleTransactions.containsKey(tID));
		assertEquals(shop.startSaleTransaction(), tID);
		assertTrue(shop.data.saleTransactions.containsKey(tID));
		assertEquals(shop.data.saleTransactions.get(tID).getStatus(), "OPEN");

		tID++;
		assertEquals(shop.data.saleTransactionIDs, tID);
	}
	
}


