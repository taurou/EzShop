package it.polito.ezshop.integrationTests;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;


import it.polito.ezshop.exceptions.InvalidOrderIdException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.BalanceOperation;
import it.polito.ezshop.model.Order;
import it.polito.ezshop.model.ProductType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;





public class PayOrderIntegrationTest {
	Order o; 
	ProductType product;
	it.polito.ezshop.data.EZShop shop;	
	
	@Before 
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.login("admin", "adminpass");
		product = new ProductType("Prodottoprova", "6291041500213", 29.30, 14029, "Prodottodescription");
		o = new Order (product, 27.00, 100, 121768, "ISSUED", LocalDate.now());
		shop.data.orders.put(121768, o);
		shop.data.balanceOperations.put(shop.data.balanceOperationIDs, new BalanceOperation(shop.data.balanceOperationIDs++,
				LocalDate.now(), 10000, "SALE"));
		
	}
	
	@Test 
	public void payOrderSuccessTest() throws InvalidOrderIdException, UnauthorizedException{
		assertTrue(shop.payOrder(121768));
		assertEquals(shop.data.orders.get(121768).getStatus(), "PAYED");
		assertEquals(shop.computeBalance(), 7300, 0.001);
		int a = shop.data.balanceOperationIDs - 1; 
		assertEquals(o.getBalanceId(), Integer.valueOf(a));
		
		BalanceOperation bo = shop.data.balanceOperations.get(a);
		assertEquals(bo.getBalanceId(), a);
		assertEquals(bo.getMoney(), -o.getQuantity()*o.getPricePerUnit(), 0.001);
		assertEquals(bo.getType(), "ORDER");


	}
	
	@Test
	public void negativeBalanceTest() throws InvalidOrderIdException, UnauthorizedException{
		shop.data.balanceOperations.put(shop.data.balanceOperationIDs, new BalanceOperation(shop.data.balanceOperationIDs++,
		LocalDate.now(), -10000, "ORDER"));
		
		assertFalse(shop.payOrder(121768));
		assertEquals(shop.computeBalance(), 0, 0.001);

	}
	

	@Test (expected = InvalidOrderIdException.class)
	public void negativeIdTest() throws InvalidOrderIdException, UnauthorizedException{
		shop.payOrder(-12168);
		
	}

	
	@Test (expected = UnauthorizedException.class)
	public void userIsAdminTest() throws InvalidOrderIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("notadmin", "notadminpass", "Cashier");
		shop.login("notadmin", "notadminpass");
		shop.payOrder(12168);		
	}
	
	@Test (expected = UnauthorizedException.class)
	public void userIsUserNullTest() throws InvalidOrderIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.data.loggedInUser=null;
		shop.payOrder(12168);		
	}
	
	@Test
	public void orderIdNotExistsTest() throws InvalidOrderIdException, UnauthorizedException{
		assertFalse(shop.payOrder(1217268));
	}
	
	@Test
	public void orderNotExistsTest() throws InvalidOrderIdException, UnauthorizedException{
		shop.data.orders.put(2121, null);
		assertFalse(shop.payOrder(2121));
	}	
	
	@Test
	public void orderNotIssued() throws InvalidOrderIdException, UnauthorizedException{
		Order a = shop.data.orders.get(121768);
		a.setOrderId(91726);
		a.setStatus("PAYED");
		shop.data.orders.put(91726, a);
		assertFalse(shop.payOrder(91726));
	}
	
		
}
