package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class CreateCardIntegrationTest {
	it.polito.ezshop.data.EZShop shop;
	
	@Test 
	
	public void creationTest() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpw", "Administrator");
		shop.login("admin", "adminpw");

		int c = shop.data.cardIDs;

		assertEquals(shop.createCard(), Integer.valueOf(c).toString());
		assertTrue(shop.data.cards.containsKey( Integer.valueOf(c)));
		c++;
		assertEquals(shop.data.cardIDs, Integer.valueOf(c));
		shop.createCard();
		assertTrue(shop.data.cards.containsKey( Integer.valueOf(c)));
		c++;
		assertEquals(shop.data.cardIDs,  Integer.valueOf(c));

		
		
	}
	
	
}
