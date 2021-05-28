package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;


public class DefineCustomerIntegrationTest {
	it.polito.ezshop.data.EZShop shop;	
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.createUser("cashier3", "cashier3pass", "Cashier");
		shop.createUser("manager3", "manager3pass", "ShopManager");
		shop.login("manager3", "manager3pass");

			}
	
	
	@Test (expected = InvalidCustomerNameException.class)
	public void testNullArg() throws InvalidUsernameException, InvalidPasswordException, InvalidCustomerNameException, UnauthorizedException {
		shop.defineCustomer(null);		

	}
	@Test (expected = InvalidCustomerNameException.class)
	public void testBlankArg() throws InvalidUsernameException, InvalidPasswordException, InvalidCustomerNameException, UnauthorizedException {
		shop.defineCustomer("    ");

	}
	@Test (expected = InvalidCustomerNameException.class)
	public void testEmptyArg() throws InvalidUsernameException, InvalidPasswordException, InvalidCustomerNameException, UnauthorizedException {
		shop.defineCustomer("");		

	}
	
	@Test
	public void addingCustomer() throws InvalidCustomerNameException, UnauthorizedException {
		int a = shop.data.customerIDs ;
		assertNotEquals(shop.defineCustomer("Mario Rossi"), Integer.valueOf(-1));
		assertEquals(shop.data.customers.get(a).getCustomerName(), "Mario Rossi");
		a++;
		assertEquals(shop.data.customerIDs , Integer.valueOf(a));

		assertNotEquals(shop.defineCustomer("Andrea Rossi"), Integer.valueOf(-1));
		assertEquals(shop.data.customers.get(a).getCustomerName(), "Andrea Rossi");

		a++;
		assertEquals(shop.data.customerIDs , Integer.valueOf(a));

		assertNotEquals(shop.defineCustomer("Paolo Rossi"), Integer.valueOf(-1));
		a++;
		assertEquals(shop.data.customerIDs , Integer.valueOf(a));

		assertEquals(shop.defineCustomer("Paolo Rossi"), Integer.valueOf(-1));
		assertEquals(shop.data.customerIDs , Integer.valueOf(a));

	}
	
	
	
}

