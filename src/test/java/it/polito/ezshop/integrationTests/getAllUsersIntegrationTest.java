package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class getAllUsersIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	List<User> usersList;
	Integer AdminID, CashierID, ShopManagerID;
	User user;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {	
		shop = new it.polito.ezshop.data.EZShop(1);
		usersList = new LinkedList<User>();
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		AdminID = shop.createUser("admin", "admin", "Administrator");
		ShopManagerID = shop.createUser("ShopManager", "ShopManager", "ShopManager");
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws UnauthorizedException  {
		usersList = shop.getAllUsers();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NotAdminTest() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException  {
		user = shop.login("cashier", "cashier");
		usersList = shop.getAllUsers();
	}	
	
	@Test
	public void VerifyGetUser() throws InvalidUserIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		usersList = shop.getAllUsers();
		assertEquals(usersList.get(0).getId(), CashierID);
		assertEquals(usersList.get(1).getId(), AdminID);
		assertEquals(usersList.get(2).getId(), ShopManagerID);
	}
	
	
	
	
}