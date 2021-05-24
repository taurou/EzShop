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

public class ModifyPointsOnCardIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ; 
	String cardn;
	String cardNonEx;
	Integer customerID;
	
	@Before
	public void setUp() throws InvalidCustomerNameException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerIdException, InvalidCustomerCardException {
		shop= new it.polito.ezshop.data.EZShop(1);
		shop.createUser("manager3", "manager3pass", "ShopManager");
		shop.login("manager3", "manager3pass");
		 cardn = shop.createCard();
		 shop.attachCardToCustomer(cardn, shop.defineCustomer("Mario Rossini")) ;
		 Integer i = Integer.valueOf(cardn);
		 i++;
		 cardNonEx = i.toString();
		 

	}
	


	@Test (expected = InvalidCustomerCardException.class)
	public void invalidCardEmptyTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.modifyPointsOnCard("", 1);
	}
	
	@Test (expected = InvalidCustomerCardException.class)
	public void invalidCardBlankTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.modifyPointsOnCard("    ", 1);
	}
	
	@Test (expected = InvalidCustomerCardException.class)
	public void invalidCardNullTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.modifyPointsOnCard(null, 1);
	}
	
	@Test (expected = InvalidCustomerCardException.class)
	public void invalidCardNotDigitsTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.modifyPointsOnCard("10000002c", 1);
	}
	@Test 
	public void cardNotExistsOrNotAttachedTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		assertFalse(shop.modifyPointsOnCard(cardNonEx, 10));
		cardNonEx = shop.createCard();
		assertFalse(shop.modifyPointsOnCard(cardNonEx, 10));
	}
	
	@Test
	public void cardAddingPointsTest() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		shop.modifyPointsOnCard(cardn, 10);

		assertEquals(shop.data.cards.get(Integer.valueOf(cardn)).getPoints(), Integer.valueOf(10));
		assertFalse(shop.modifyPointsOnCard(cardn, -11));
		assertEquals(shop.data.cards.get(Integer.valueOf(cardn)).getPoints(), Integer.valueOf(10));
		assertTrue(shop.modifyPointsOnCard(cardn, -10));
		assertEquals(shop.data.cards.get(Integer.valueOf(cardn)).getPoints(), Integer.valueOf(0));

		}
}
