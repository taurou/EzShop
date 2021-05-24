package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class LoginIntegrationTest {

	it.polito.ezshop.data.EZShop shop;
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		
		shop = new it.polito.ezshop.data.EZShop(1);
	    shop.createUser("admin", "admin", "Administrator");
	}
	
	
	@Test (expected = InvalidUsernameException.class)
	public void emptyUsernameTest() throws InvalidUsernameException, InvalidPasswordException {
		shop.login("", "password");
	}
	
	@Test (expected = InvalidUsernameException.class)
	public void nullUsernameTest() throws InvalidUsernameException, InvalidPasswordException {
		shop.login(null, "password");
	}
	
	@Test (expected = InvalidPasswordException.class)
	public void emptyPasswordTest() throws InvalidUsernameException, InvalidPasswordException {
		shop.login("ciao", "");
	}
	
	@Test (expected = InvalidPasswordException.class)
	public void nullPasswordTest() throws InvalidUsernameException, InvalidPasswordException {
		shop.login("ciao", null);
	}
	
	@Test
	public void verifyLogin() throws InvalidUsernameException, InvalidPasswordException {
		assertEquals(shop.login("admin", "admin"), shop.data.loggedInUser);
		
	}
	
}
