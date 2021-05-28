package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class getProductTypeByBarCodeIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ProductID, ManagerID;
	User user;
	ProductType product;
	boolean isDone;
	String BarCode;
	// boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,  InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
		shop = new it.polito.ezshop.data.EZShop(1);
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		ManagerID = shop.createUser("ShopManager", "ShopManager", "ShopManager");		
		user = shop.login("admin", "admin");
		BarCode = "6291041500213";
		ProductID = shop.createProductType("Description", BarCode, 1.5, "Note");
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductCodeException, UnauthorizedException {	
		product = shop.getProductTypeByBarCode(BarCode);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductCodeException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("cashier", "cashier");
		product = shop.getProductTypeByBarCode(BarCode);
	}
	
	@Test (expected = InvalidProductCodeException.class)
	public void InvalidBarcodeTest() throws InvalidProductCodeException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		product = shop.getProductTypeByBarCode("9728845652");
	}
	/*
	@Test (expected = InvalidProductCodeException.class)
	public void NullBarcodeTest() throws InvalidProductCodeException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		product = shop.getProductTypeByBarCode(null);
	}
	
	@Test (expected = InvalidProductCodeException.class)
	public void BlankBarcodeTest() throws InvalidProductCodeException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("ShopManager", "ShopManager");
		product = shop.getProductTypeByBarCode(" ");
	}
	*/
	@Test
	public void VerifyGetProductTypeByBarCodeAdminTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		product = shop.getProductTypeByBarCode("6291041500213");
		assertEquals("Description", product.getProductDescription());
		assertEquals(null,shop.getProductTypeByBarCode("123456789104"));
	}
	
	@Test
	public void VerifyGetProductTypeByBarCodeManagerTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("ShopManager", "ShopManager");
		product = shop.getProductTypeByBarCode("6291041500213");
		assertEquals("Description", product.getProductDescription());
		assertEquals(null,shop.getProductTypeByBarCode("123456789104"));
	}
	
}