package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUserIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class DeleteUserIntegrationTest {

	it.polito.ezshop.data.EZShop shop;
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1);
	
	}
	
	@Test (expected = UnauthorizedException.class)
	public void authorizationTest1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
		Integer id1 = shop.createUser("username", "password", "Administrator");
		Integer id2 = shop.createUser("username2", "password", "Cashier");
		Integer id3 = shop.createUser("username3", "password", "ShopManager");
		
		shop.login("username2", "password");
		
		shop.deleteUser(id3);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void authorizationTest2() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
		Integer id1 = shop.createUser("username", "password", "Administrator");
		Integer id2 = shop.createUser("username2", "password", "Cashier");
		Integer id3 = shop.createUser("username3", "password", "ShopManager");
		
		shop.login("username3", "password");
		
		shop.deleteUser(id2);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void authorizationTest4() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
		Integer id1 = shop.createUser("username", "password", "Administrator");
		Integer id2 = shop.createUser("username2", "password", "Cashier");
		Integer id3 = shop.createUser("username3", "password", "ShopManager");
		
		
		
		shop.deleteUser(id2);
	}
	
	@Test
	public void authorizationTest3() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
		Integer id1 = shop.createUser("username", "password", "Administrator");
		Integer id2 = shop.createUser("username2", "password", "Cashier");
		Integer id3 = shop.createUser("username3", "password", "ShopManager");
		
		shop.login("username", "password");
		
		assertTrue(shop.deleteUser(id2));
	}
	
	
	@Test
	public void authorizationTest5() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
		Integer id1 = shop.createUser("username", "password", "Administrator");
		Integer id2 = shop.createUser("username2", "password", "Cashier");
		Integer id3 = shop.createUser("username3", "password", "ShopManager");
		
		shop.login("username", "password");
		
		assertFalse(shop.deleteUser(id1));
	}
	
	@Test (expected = InvalidUserIdException.class)
	public void idTest1() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
		Integer id1 = shop.createUser("username", "password", "Administrator");
		
		shop.login("username", "password");
		
		shop.deleteUser(-1);
	}
	
	
	
	
	@Test (expected = InvalidUserIdException.class)
	public void idTest2() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
		Integer id1 = shop.createUser("username", "password", "Administrator");
		
		shop.login("username", "password");
		
		shop.deleteUser(null);
	}
	
	@Test
	public void eliminationVerificationTest() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
		Integer id1 = shop.createUser("username", "password", "Administrator");
		Integer id2 = shop.createUser("username2", "password", "Cashier");
		Integer id3 = shop.createUser("username3", "password", "ShopManager");
		
		shop.login("username", "password");
		
		assertTrue(shop.deleteUser(id2));
		assertFalse(shop.data.users.containsKey(id2));
	}
	
	
}


