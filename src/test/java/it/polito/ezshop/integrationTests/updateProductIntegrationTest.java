package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class updateProductIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ProductID, ShopManagerID;
	Double newPrice;
	User user;
	ProductType product;
	boolean isDone;
	// boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,  InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
		shop = new it.polito.ezshop.data.EZShop(1);
		newPrice = 5.5;
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		ShopManagerID = shop.createUser("ShopManager", "ShopManager", "ShopManager");
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Description", "6291041500213", 1.5, "Note");
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException {	
		
		isDone = shop.updateProduct(ProductID, "newDescription", "6291041500213", newPrice, "newNote");
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("cashier", "cashier");
		isDone = shop.updateProduct(ProductID, "newDescription", "6291041500213", newPrice, "newNote");
	}
	
	@Test (expected = InvalidProductDescriptionException.class)
	public void NullDescriptionTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateProduct(ProductID, null, "6291041500213", newPrice, "newNote");
	}
	
	@Test (expected = InvalidProductDescriptionException.class)
	public void BlankDescriptionTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateProduct(ProductID, " ", "6291041500213", newPrice, "newNote");
	}
	
	@Test (expected = InvalidPricePerUnitException.class)
	public void NegativePriceTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateProduct(ProductID, "newDescription", "6291041500213", -5.5, "newNote");
	}
	
	@Test (expected = InvalidPricePerUnitException.class)
	public void ZeroPriceTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateProduct(ProductID, "newDescription", "6291041500213", 0, "newNote");
	}
	
	@Test (expected = InvalidProductCodeException.class)
	public void InvalidBarcodeTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateProduct(ProductID, "newDescription", "12629100210", newPrice, "newNote");
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void NullIDTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateProduct(-1, "newDescription", "6291041500213", newPrice, "newNote");
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void NagativeIDTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateProduct(-5, "newDescription", "6291041500213", newPrice, "newNote");
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void ZeroIDTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateProduct(0, "newDescription", "6291041500213", newPrice, "newNote");
	}
	
	@Test
	public void NotExistingIDTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		Integer NotExistingID = 948536;
		assertFalse(shop.updateProduct(NotExistingID, "newDescription", "6291041500213", newPrice, "newNote"));
	}
		
	@Test
	public void VerifyUpdatrProductAdminTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		assertTrue(shop.updateProduct(ProductID, "newDescription", "6291041500213", newPrice, "newNote"));
		assertEquals("newDescription", shop.getProductTypeByBarCode("6291041500213").getProductDescription());
		assertEquals(newPrice, shop.getProductTypeByBarCode("6291041500213").getPricePerUnit(), 0.01);
		assertEquals("newNote", shop.getProductTypeByBarCode("6291041500213").getNote());		
	}
	
	@Test
	public void VerifyUpdatrProductManagerTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("ShopManager", "ShopManager");
		assertTrue(shop.updateProduct(ProductID, "newDescription", "6291041500213", newPrice, "newNote"));
		assertEquals("newDescription", shop.getProductTypeByBarCode("6291041500213").getProductDescription());
		assertEquals(newPrice, shop.getProductTypeByBarCode("6291041500213").getPricePerUnit(), 0.01);
		assertEquals("newNote", shop.getProductTypeByBarCode("6291041500213").getNote());		
	}
	
}
	