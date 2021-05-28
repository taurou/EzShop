package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class GetUserIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	Integer AdminID, CashierID ;
	User user;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {	
		shop = new it.polito.ezshop.data.EZShop(1);
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidUserIdException, UnauthorizedException  {
		user = shop.getUser(AdminID);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NotAdminTest() throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {
		user = shop.login("cashier", "cashier");
		user = shop.getUser(AdminID);
	}	
	
	@Test (expected = InvalidUserIdException.class)
	public void NegativeIDTest() throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		user = shop.getUser(-5);
	}
	
	@Test (expected = InvalidUserIdException.class)
	public void NullIDTest() throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		user = shop.getUser(-1);
	}
	
	@Test
	public void VerifyGetUser() throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		assertTrue(shop.getUser(AdminID) == user);
		assertTrue(shop.getUser(CashierID).getId() == CashierID);
	}
	
	
	
	
}