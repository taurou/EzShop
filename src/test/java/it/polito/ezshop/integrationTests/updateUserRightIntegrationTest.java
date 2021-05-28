package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class updateUserRightIntegrationTest {
	
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
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException  {
		isDone = shop.updateUserRights(CashierID,"Administrator");
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NotAdminTest() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException, InvalidPasswordException, InvalidUsernameException  {
		user = shop.login("cashier", "cashier");
		isDone = shop.updateUserRights(CashierID,"Administrator");
	}
	
	@Test (expected = InvalidUserIdException.class)
	public void NegativeIDTest() throws InvalidRoleException, InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateUserRights(-5,"Administrator");
	}
	
	@Test (expected = InvalidUserIdException.class)
	public void NullIDTest() throws InvalidRoleException, InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateUserRights(-1,"Administrator");
	}
	
	@Test (expected = InvalidRoleException.class)
	public void InvalidRoleTest() throws InvalidRoleException, InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateUserRights(CashierID,"InvalidRole");
	}
	
	@Test
	public void verifyUpdateUserRightsTest() throws InvalidRoleException, InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		Integer invalidID = 123895267;
		
		/* Invalid ID */
		assertFalse(shop.updateUserRights(invalidID,"Administrator"));
		
		/* Normal Case */
		assertTrue(shop.updateUserRights(CashierID,"Administrator"));
		
		
	}	
	
	
}