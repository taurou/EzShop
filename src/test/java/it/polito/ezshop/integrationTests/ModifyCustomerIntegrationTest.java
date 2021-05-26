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

public class ModifyCustomerIntegrationTest {

	it.polito.ezshop.data.EZShop shop;	

	
	
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerNameException, UnauthorizedException {
		
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.createUser("cashier3", "cashier3pass", "Cashier");
		shop.createUser("manager3", "manager3pass", "ShopManager");
		shop.login("manager3", "manager3pass");
		shop.defineCustomer("Mario Rossi");
		shop.defineCustomer("Mario Gialli");
		shop.defineCustomer("Francesco Rossi");

	}

	@Test (expected = InvalidCustomerNameException.class)
	public void nullNameTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.modifyCustomer(null, null, null);
	}
	
	@Test (expected = InvalidCustomerNameException.class)
	public void blankNameTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.modifyCustomer(null, "", null);
	}
	
	@Test (expected = InvalidCustomerNameException.class)
	public void emptyNameTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.modifyCustomer(null, "    ", null);
	}

	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidCustomerId1Test() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.modifyCustomer(null, "Giancarlo Rossi", null);
	}
	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidCustomerId2Test() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.modifyCustomer(-29, "Giancarlo Rossi", null);
	}
	
	@Test (expected = InvalidCustomerIdException.class)
	public void invalidCustomerId3Test() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.modifyCustomer(0, "Giancarlo Rossi", null);
	}
	

	

	
	@Test (expected = InvalidCustomerCardException.class)
	public void notExistingCardPassedTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.modifyCustomer(1, "Giancarlo Rossi", "1000000009");
	}
	
	@Test (expected = InvalidCustomerCardException.class)
	public void notDigitsPassedTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		shop.modifyCustomer(1, "Giancarlo Rossi", "1000000b01");
	}
	

	@Test 
	public void custCodeNotExists() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException, InvalidCustomerNameException {  
		String cardn = shop.createCard();
		assertFalse(shop.attachCardToCustomer(cardn, 73));
	}
	@Test
	public void modifyCustomerCardTest() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
		String cardn = shop.createCard();
		shop.attachCardToCustomer(cardn, 1);
		cardn = shop.createCard();
		assertTrue(shop.modifyCustomer(1, "Giancarlo Rossi", cardn));
		assertEquals(shop.data.cards.get(Integer.valueOf(cardn)).getCustomer().getId(), Integer.valueOf(1));
		assertEquals(shop.data.customers.get(1).getCard().getCardNumber(), cardn);
		assertEquals(shop.data.customers.get(1).getCustomerName(), "Giancarlo Rossi");
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
