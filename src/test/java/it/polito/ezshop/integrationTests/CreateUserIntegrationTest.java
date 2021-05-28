package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.EZShop;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;

public class CreateUserIntegrationTest {
	it.polito.ezshop.data.EZShop shop;
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1);
	
	}
	
	@Test(expected = InvalidUsernameException.class)
	public void nullUsernameTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser(null, "ciao", "Administrator");
		
	}
	
	@Test(expected = InvalidUsernameException.class)
	public void emptylUsernameTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("", "ciao", "Administrator");
		
	}
	
	@Test
	public void alreadyPresentlUsernameTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		assertEquals(1, shop.createUser("username", "ciao", "Administrator"), 0);
		assertEquals(-1, shop.createUser("username", "ciao", "Administrator"), 0);
	}
	
	@Test(expected = InvalidPasswordException.class)
	public void emptyPasswordTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("xyz", "", "Administrator");
		
	}
	
	@Test(expected = InvalidPasswordException.class)
	public void nullPasswordTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("xyz", null, "Administrator");
		
	}
	
	@Test(expected = InvalidRoleException.class)
	public void nullRoleTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("xyz", "password", "");
		
	}
	
	@Test(expected = InvalidRoleException.class)
	public void invalidRoleTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("xyz", "password", "qualcosa");
		
	}
	
	
	
	 @Test
	 public void returnValueTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		 
		 assertEquals(1, shop.createUser("username", "password", "ShopManager"),  0);
		 assertEquals(2, shop.createUser("username2", "password", "Administrator"), 0);
		 
	 }
	 
	 @Test 
	 public void testUserAdded() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		 assertEquals(1, shop.createUser("username", "password", "Cashier"),  0);
		 assertEquals(2, shop.createUser("username2", "password", "Administrator"), 0);
		 
		 
	 }
	
	
	

}
