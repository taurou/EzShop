package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class updatePositionIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ProductID ;
	User user ;
	ProductType product ;
	boolean isDone ;
	String newPosition;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,  InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
		shop = new it.polito.ezshop.data.EZShop(1);
		newPosition = "1-2-3";
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Description", "6291041500213", 1.5, "Note");
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductIdException, UnauthorizedException, InvalidLocationException {	
		isDone = shop.updatePosition(ProductID,newPosition);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException  {	
		user = shop.login("cashier", "cashier");
		isDone = shop.updatePosition(ProductID,newPosition);
	}
	
	@Test (expected = InvalidLocationException.class)
	public void InvalidLocationTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException  {	
		user = shop.login("admin", "admin");
		String IncorrectPatternPosition = "123";
		isDone = shop.updatePosition(ProductID,IncorrectPatternPosition);
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void NullIDTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException {
		user = shop.login("admin", "admin");
		isDone = shop.updatePosition(null,newPosition);
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void NegativeIDTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException {
		user = shop.login("admin", "admin");
		isDone = shop.updatePosition(-12, newPosition);
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void ZeroIDTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException {
		user = shop.login("admin", "admin");
		isDone = shop.updatePosition(0, newPosition);
	}


	
	
	
	@Test
	public void InvalidIDTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException {
		user = shop.login("admin", "admin");
		Integer ArbitraryProductID = 692417;
		assertFalse(shop.updatePosition(ArbitraryProductID, newPosition));
	}
	
	@Test
	public void UsedPositionTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException {
		user = shop.login("admin", "admin");
		isDone = shop.updatePosition(ProductID,newPosition);
		assertFalse(shop.updatePosition(ProductID,newPosition));
	}
	
	@Test
	public void VerifyupdatePositionTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException, InvalidProductCodeException {
		user = shop.login("admin", "admin");
		assertTrue(shop.updatePosition(ProductID,newPosition));
		assertEquals(newPosition,shop.getProductTypeByBarCode("6291041500213").getLocation());
	}
		
}