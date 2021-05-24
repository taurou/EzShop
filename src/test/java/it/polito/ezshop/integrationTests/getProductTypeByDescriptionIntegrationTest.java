package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class getProductTypeByDescriptionIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ManagerID, ProductID1, ProductID2, ProductID3;
	User user;
	List<ProductType> products;
	ProductType product1, product2, product3;
	boolean isDone;
	String Description;
	// boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,  InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
		shop = new it.polito.ezshop.data.EZShop(1);
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		ManagerID = shop.createUser("ShopManager", "ShopManager", "ShopManager");		
		user = shop.login("admin", "admin");
		Description = "Description";
		ProductID1 = shop.createProductType(Description, "6291041500213", 1.5, "Note");
		ProductID2 = shop.createProductType(Description, "123456789104", 2.5, "Note");
		ProductID3 = shop.createProductType(Description, "923456789100", 3.5, "Note");
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductCodeException, UnauthorizedException {	
		products = shop.getProductTypesByDescription(Description);
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductCodeException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("cashier", "cashier");
		products = shop.getProductTypesByDescription(Description);
	}
	
	@Test
	public void VerifyGetProductTypesByDescriptionAdminTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("admin", "admin");
		products = shop.getProductTypesByDescription(Description);
		product1 = shop.getProductTypeByBarCode("6291041500213");
		product2 = shop.getProductTypeByBarCode("123456789104");
		product3 = shop.getProductTypeByBarCode("923456789100");
		assertTrue(products.contains(product1));
		assertTrue(products.contains(product2));
		assertTrue(products.contains(product3));
		String arbitraryDescription = "jkdshsf";
		assertTrue(shop.getProductTypesByDescription(arbitraryDescription).isEmpty());
	}
	
	@Test
	public void VerifyGetProductTypesByDescriptionManagerTest() throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
	InvalidPricePerUnitException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException {
		user = shop.login("ShopManager", "ShopManager");
		products = shop.getProductTypesByDescription(Description);
		product1 = shop.getProductTypeByBarCode("6291041500213");
		product2 = shop.getProductTypeByBarCode("123456789104");
		product3 = shop.getProductTypeByBarCode("923456789100");
		assertTrue(products.contains(product1));
		assertTrue(products.contains(product2));
		assertTrue(products.contains(product3));
		String arbitraryDescription = "jkdshsf";
		assertTrue(shop.getProductTypesByDescription(arbitraryDescription).isEmpty());
	}
}