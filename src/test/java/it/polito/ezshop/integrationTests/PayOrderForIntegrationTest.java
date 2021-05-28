package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class PayOrderForIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ProductID, OrderID, Quantity;
	User user ;
	ProductType product ;
	String BarCode;
	double Price;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,  InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
		shop = new it.polito.ezshop.data.EZShop(1);
		Quantity = 5;
		Price = 1.5;
		BarCode = "6291041500213";
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Description", BarCode, Price, "Note");
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductIdException, UnauthorizedException, InvalidLocationException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException {	
		OrderID = shop.payOrderFor(BarCode, Quantity, Price);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException  {	
		user = shop.login("cashier", "cashier");
		OrderID = shop.payOrderFor(BarCode, Quantity, Price);
	}
	
	@Test (expected = InvalidProductCodeException.class)
	public void InvalidBarcodeTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		String invalidBarCode = "5498321";
		shop.payOrderFor(invalidBarCode, Quantity, Price);
	}
	
	@Test (expected = InvalidQuantityException.class)
	public void NegativeQuantityTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		int invalidQuantity = -5;
		OrderID = shop.payOrderFor(BarCode, invalidQuantity, Price);
	}
	
	@Test (expected = InvalidQuantityException.class)
	public void ZeroQuantityTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		int invalidQuantity = 0;
		OrderID = shop.payOrderFor(BarCode, invalidQuantity, Price);
	}
	
	@Test (expected = InvalidPricePerUnitException.class)
	public void NegativePriceTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		int invalidPrice = -5;
		shop.payOrderFor(BarCode, Quantity, invalidPrice);
	}
	
	@Test (expected = InvalidPricePerUnitException.class)
	public void ZeroPriceTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		int invalidPrice = 0;
		OrderID = shop.payOrderFor(BarCode, Quantity, invalidPrice);
	}
	
	@Test
	public void NonExistingBarCodeTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		String NonExistingBarCode = "123456789104";
		assertEquals(-1,shop.payOrderFor(NonExistingBarCode, Quantity, Price),0);
	}
	
	@Test
	public void NoEnoughBlanceTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		assertEquals(-1,shop.payOrderFor(BarCode, Quantity, Price),0);
	}
	
	@Test
	public void VerifyPayOrderForTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		shop.recordBalanceUpdate(1000);
		assertEquals(shop.data.barcodeToId.get(BarCode),shop.payOrderFor(BarCode, Quantity, Price), 0);
	}
	
}