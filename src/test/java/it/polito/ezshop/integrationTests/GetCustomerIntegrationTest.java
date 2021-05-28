package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidCustomerCardException;
import it.polito.ezshop.exceptions.InvalidCustomerIdException;
import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.Customer;

public class GetCustomerIntegrationTest {

	it.polito.ezshop.data.EZShop shop;	

	
	
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerNameException, UnauthorizedException {
		
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.createUser("cashier3", "cashier3pass", "Cashier");
		shop.createUser("manager3", "manager3pass", "ShopManager");
		shop.login("manager3", "manager3pass");
		shop.defineCustomer("Mario Rossi");	//1
		shop.defineCustomer("Mario Gialli");	//2
		shop.defineCustomer("Francesco Rossi"); 	//3

	}
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidCustomerIdTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.getCustomer(0);
	}
	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidCustomerId2Test() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.getCustomer(-43);
	}
	

	@Test 
	public void notExistingIdTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		assertNull(shop.getCustomer(4));
	}
	
	@Test 
	public void existingIdTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		it.polito.ezshop.data.Customer c = shop.getCustomer(2);
		assertEquals(c.getCustomerName(), "Mario Gialli");
		assertEquals(c.getId(), Integer.valueOf(2));
		
	}
	


	
}
