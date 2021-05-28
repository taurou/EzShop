package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class createProductTypeIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ProductID;
	User user;
	ProductType product;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop = new it.polito.ezshop.data.EZShop(1);
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {	
		ProductID = shop.createProductType("Milk", "6291041500213", 1.5, "Milk Company");
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("cashier", "cashier");
		ProductID = shop.createProductType("Milk", "6291041500213", 1.5, "Milk Company");
	}
	
	@Test (expected = InvalidProductDescriptionException.class)
	public void NullDescriptionTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType(null, "6291041500213", 1.5, "Milk Company");
	}
	
	@Test (expected = InvalidProductDescriptionException.class)
	public void BlankDescriptionTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType(" ", "6291041500213", 1.5, "Milk Company");
	}
	
	@Test (expected = InvalidPricePerUnitException.class)
	public void NegativePriceTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Milk1", "6291041500213", -5, "Milk Company");
	}
	
	@Test (expected = InvalidPricePerUnitException.class)
	public void ZeroPriceTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Milk2", "6291041500213", 0, "Milk Company");
	}
	
	@Test (expected = InvalidProductCodeException.class)
	public void InvalidBarcodeTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		
		ProductID = shop.createProductType("Milk2", "124963", 3, "Milk Company");
	}
	
	@Test
	public void RepeatedProductTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Milk2", "6291041500213", 5, "Milk Company");
		assertEquals(-1, shop.createProductType("Milk2", "6291041500213", 5, "Milk Company"), 0.01);
	}
	
	@Test
	public void VerifyCreateProductTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Milk2", "6291041500213", 5, "Milk Company");
		assertEquals(ProductID, shop.getProductTypeByBarCode("6291041500213").getId(),0);
	}
	
}
	