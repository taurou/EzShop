package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class deleteProductTypeIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ProductID;
	User user;
	ProductType product;
	boolean isDone;
	// boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,  InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
		shop = new it.polito.ezshop.data.EZShop(1);
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Description", "6291041500213", 1.5, "Note");
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException {	
		isDone = shop.deleteProductType(ProductID);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("cashier", "cashier");
		isDone = shop.deleteProductType(ProductID);
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void NullIDTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.deleteProductType(null);
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void NagativeIDTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.deleteProductType(-ProductID);
		}
	
	@Test (expected = InvalidProductIdException.class)
	public void ZeroIDTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.deleteProductType(0);	
		}
	
	@Test
	public void NotExistingIDTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		Integer NotExistingID = 936;
		assertFalse(shop.deleteProductType(NotExistingID));
	}
		
	@Test
	public void VerifyDeleteProductTypeTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		product = shop.getProductTypeByBarCode("6291041500213");
		assertTrue(shop.deleteProductType(ProductID));
		assertFalse(shop.getAllProductTypes().contains(product));
	}
	
	
	
}
	