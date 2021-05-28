package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class updateQuantityIntegrationTest {
	
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
		product = shop.getProductTypeByBarCode("6291041500213");
		product.setLocation("51");
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductIdException, UnauthorizedException {	
		isDone = shop.updateQuantity(ProductID, 5);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("cashier", "cashier");
		isDone = shop.updateQuantity(ProductID, 5);
	}
	
	@Test (expected = InvalidProductIdException.class)
	public void NullIDTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		isDone = shop.updateQuantity(null, 5);//check the comment in updateQuantity function to fix this error
	}
	
	

	@Test
	public void NegativeValueToBeAddedTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		assertFalse(shop.updateQuantity(ProductID, -5));
	}
	
	@Test
	public void VerifyUpdateQuantityTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidProductCodeException {
		user = shop.login("admin", "admin");
		assertTrue(shop.updateQuantity(ProductID, 5));
		assertEquals(5, shop.getProductTypeByBarCode("6291041500213").getQuantity(),0);
	}
	
	
	
}