package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class getAllProductTypesIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ManagerID, ProductID1, ProductID2, ProductID3;
	User user;
	List<ProductType> products;
	ProductType product1, product2, product3;
	boolean isDone;
	String Description;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,  InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
		shop = new it.polito.ezshop.data.EZShop(1);
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		ManagerID = shop.createUser("ShopManager", "ShopManager", "ShopManager");
		products = new LinkedList<ProductType>();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws UnauthorizedException {	
		products = shop.getAllProductTypes();
	}
	
	@Test 
	public void NoProductsTest() throws UnauthorizedException,InvalidUsernameException, InvalidPasswordException  {	
		user = shop.login("ShopManager", "ShopManager");
		products = new LinkedList<ProductType>();
		assertTrue(shop.getAllProductTypes().isEmpty());
		}
	
	@Test
	public void VerifyGetAllProductTypesTest() throws InvalidProductCodeException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidProductDescriptionException, InvalidPricePerUnitException {
		user = shop.login("admin", "admin"); 
		Description = "Description";
		ProductID1 = shop.createProductType(Description, "6291041500213", 1.5, "Note");
		ProductID2 = shop.createProductType(Description, "123456789104", 2.5, "Note");
		ProductID3 = shop.createProductType(Description, "923456789100", 3.5, "Note");
		products.add(shop.getProductTypeByBarCode("6291041500213"));
		products.add(shop.getProductTypeByBarCode("123456789104"));
		products.add(shop.getProductTypeByBarCode("923456789100"));
		assertEquals(products, shop.getAllProductTypes());
	}
}