package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidCustomerCardException;
import it.polito.ezshop.exceptions.InvalidCustomerIdException;
import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class AttachCardToCustomerIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ; 
	String cardn;
	Integer customerID;
	Integer customerID2;
	
	@Before
	public void setUp() throws InvalidCustomerNameException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop= new it.polito.ezshop.data.EZShop(1);
		shop.createUser("manager3", "manager3pass", "ShopManager");
		shop.login("manager3", "manager3pass");

		 customerID = shop.defineCustomer("Mario Rossini");
		 cardn = shop.createCard();
		 customerID2 = shop.defineCustomer("Lauro Rossi");

		
	}
	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidIdTest1() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.attachCardToCustomer("1010", -19);
	}
	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidIdTest2() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.attachCardToCustomer("1010", 0);
	}
	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidIdTest3() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.attachCardToCustomer("1010", null);
	}

	@Test (expected = InvalidCustomerCardException.class)
	public void invalidCardEmptyTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.attachCardToCustomer("", 1);
	}
	
	@Test (expected = InvalidCustomerCardException.class)
	public void invalidCardBlankTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.attachCardToCustomer("    ", 1);
	}
	
	@Test (expected = InvalidCustomerCardException.class)
	public void invalidCardNullTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.attachCardToCustomer(null, 1);
	}
	
	@Test (expected = InvalidCustomerCardException.class)
	public void invalidCardNotDigitsTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.attachCardToCustomer("10000002c", 1);
	}
	@Test 
	public void customerOrCardNotExistsTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {

		assertFalse(shop.attachCardToCustomer(cardn, Integer.valueOf(3)));
		assertFalse(shop.attachCardToCustomer("100000021", Integer.valueOf(customerID)));
	}
	
	@Test 
	public void cardAttachedTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		assertTrue(shop.attachCardToCustomer(cardn, customerID));

		assertEquals(shop.data.cards.get(Integer.valueOf(cardn)).getCustomer().getId(), Integer.valueOf(customerID));
		assertEquals(shop.data.customers.get(customerID).getCard().getCardNumber(), cardn);
		assertEquals(shop.data.customers.get(customerID).getCustomerCard(), cardn);

	}
	
	@Test
	public void cardAlreadyAttached() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		assertTrue(shop.attachCardToCustomer(cardn, customerID));
		assertFalse(shop.attachCardToCustomer(cardn, customerID2));
		String cardn2 = shop.createCard();
		assertTrue(shop.attachCardToCustomer(cardn2.toString(), customerID2));
		// valid insertion on customerID2
		assertEquals(shop.data.cards.get(Integer.valueOf(cardn2)).getCustomer().getId(), Integer.valueOf(customerID2));
		assertEquals(shop.data.customers.get(customerID2).getCard().getCardNumber(), cardn2);
		assertEquals(shop.data.customers.get(customerID2).getCustomerCard(), cardn2);


	}
	
}
