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
	int AdminID, CashierID, ProductID, OrderID, Quantity;
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
		BarCode = "6291041500213";
		AdminID = shop.createUser("admin", "admin", "Administrator");
		CashierID = shop.createUser("cashier", "cashier", "Cashier");
		user = shop.login("admin", "admin");
		ProductID = shop.createProductType("Description", BarCode, Price, "Note");
		OrderID = shop.issueOrder(BarCode, Quantity, Price);
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void NoLoggedInUserTest() throws InvalidProductIdException, UnauthorizedException, InvalidLocationException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException {	
		Orders = shop.getAllOrders();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void unauthorizedUserTest() throws InvalidProductIdException, UnauthorizedException,InvalidUsernameException, InvalidPasswordException, InvalidLocationException, InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException  {	
		user = shop.login("cashier", "cashier");
		Orders = shop.getAllOrders();
	}
	
	@Test
	public void VerifygetAllOrdersTest() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidQuantityException  {	
		user = shop.login("admin", "admin");
		assertEquals(OrderID, shop.getAllOrders().get(0).getOrderId(),0);
	}
	
}