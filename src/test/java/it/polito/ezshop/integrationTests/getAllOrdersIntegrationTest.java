package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.Order;
import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;

public class getAllOrdersIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ;
	int AdminID, CashierID, ManagerID, Quantity, ProductID1, OrderID1, ProductID2, OrderID2 ;
	User user ;
	List<Order> Orders;
	ProductType product ;
	String BarCode;
	double Price;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException,  InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException {
		shop = new it.polito.ezshop.data.EZShop(1);
		Quantity = 5;
		Price = 1.5;
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		ManagerID = shop.createUser("ShopManager", "ShopManager", "ShopManager");
		user = shop.login("admin", "admin");
		ProductID1 = shop.createProductType("Description", "6291041500213", Price, "Note");
		ProductID2 = shop.createProductType("Description", "123456789104", Price, "Note");
		OrderID1 = shop.issueOrder("6291041500213", Quantity, Price);
		OrderID2 = shop.issueOrder("123456789104", Quantity, Price);
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductIdException, UnauthorizedException, InvalidLocationException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, InvalidUsernameException, InvalidPasswordException, InvalidProductDescriptionException {	
		Orders = shop.getAllOrders();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, InvalidProductDescriptionException  {	
		user = shop.login("cashier", "cashier");
		Orders = shop.getAllOrders();
	}
	
	@Test
	public void Verify1getAllOrdersTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		assertEquals(OrderID1, shop.getAllOrders().get(0).getOrderId(),0);
		assertEquals(OrderID2, shop.getAllOrders().get(1).getOrderId(),0);
	}
	
	@Test
	public void Verify2getAllOrdersTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("ShopManager", "ShopManager");
		assertEquals(OrderID1, shop.getAllOrders().get(0).getOrderId(),0);
		assertEquals(OrderID2, shop.getAllOrders().get(1).getOrderId(),0);
	}
}