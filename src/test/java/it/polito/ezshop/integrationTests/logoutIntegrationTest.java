package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class logoutIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	Integer AdminID, CashierID ;
	User user;
	boolean isDone;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {	
		shop = new it.polito.ezshop.data.EZShop(1);
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
	}
	
	@Test
	public void NoLoggedInUserTest() {
		assertFalse(shop.logout());
	}
	
	@Test
	public void verifyLogoutTest() throws InvalidUsernameException, InvalidPasswordException  {
		user = shop.login("admin", "admin");
		assertTrue(shop.logout());
	}
	
	
	
	
}