package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidCustomerCardException;
import it.polito.ezshop.exceptions.InvalidCustomerIdException;
import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class DeleteCustomerIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;	

	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
		
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.createUser("cashier3", "cashier3pass", "Cashier");
		shop.createUser("manager3", "manager3pass", "ShopManager");
		shop.login("manager3", "manager3pass");
		shop.defineCustomer("Mario Rossi");
		shop.defineCustomer("Mario Gialli");
		shop.defineCustomer("Francesco Rossi");
		shop.attachCardToCustomer(shop.createCard(), 1);
		

	}

	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidCustomerId1Test() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.deleteCustomer(null);
	}
	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidCustomerId2Test() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.deleteCustomer(-29);
	}
	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidCustomerId3Test() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.deleteCustomer(0);
	}
	
	@Test
	public void notExistingCustomerTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		
		assertFalse(shop.deleteCustomer(5));
		
	}

	@Test
	public void deleteTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		assertTrue(shop.data.customers.containsKey(1));
		String cardn = shop.data.customers.get(1).getCustomerCard();
		assertEquals(shop.data.cards.get(Integer.valueOf(cardn)).getCustomer().getId(), Integer.valueOf(1));
		
		
		assertTrue(shop.deleteCustomer(1));
		assertNull(shop.data.cards.get(Integer.valueOf(cardn)).getCustomer());
		assertFalse(shop.data.customers.containsKey(1));

		assertTrue(shop.deleteCustomer(3));
		assertFalse(shop.data.customers.containsKey(3));

		
		assertEquals(shop.data.customers.size(), 1);

	}

	

}
