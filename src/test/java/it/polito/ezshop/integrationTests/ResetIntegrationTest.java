package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class ResetIntegrationTest {

	
	EZShop shop;
	
	@Before
	public void setUp() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		shop.createUser("admin", "admin", "Administrator");
		shop.login("admin", "admin");
		shop.createProductType("descr", "6291041500213", 50, "note");
		
	}
	
	
	@Test
	public void testReset() {
		
		shop.reset();
		assertTrue(shop.data.productTypes.isEmpty());
		assertEquals(1, shop.data.productTypeIDs, 0);
		
	}
	
}
