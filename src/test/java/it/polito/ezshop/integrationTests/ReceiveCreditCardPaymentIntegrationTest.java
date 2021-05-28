package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidCreditCardException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class ReceiveCreditCardPaymentIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;

	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = InvalidCreditCardException.class)
	public void invalidCreditCard1Test() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.receiveCreditCardPayment(1, null);
		
	}
	
	@Test (expected = InvalidCreditCardException.class)
	public void invalidCreditCard2Test() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.receiveCreditCardPayment(1, "   ");
		
	}
	
	@Test (expected = InvalidCreditCardException.class)
	public void invalidCreditCard3Test() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.receiveCreditCardPayment(1, "10000");			//To check
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidId1Test() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.receiveCreditCardPayment(null, "8377159820");			//8377159820 valid luhn 
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidId2Test() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.receiveCreditCardPayment(-10, "8377159820");			
		
	}
	
	
	
	@Test 
	public void saleNotExistTest() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		assertFalse(shop.receiveCreditCardPayment(1, "8377159820"));		
		
	}
	
	@Test
	public void cardNotRegisteredTest() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		shop.startSaleTransaction();
		
		assertFalse(shop.receiveCreditCardPayment(1, "8377159820"));
		
	}
	
	@Test
	public void notEnoughMoneyRegisteredTest() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");
		shop.data.productTypes.get(prodID).addQuantity(50);
		
		shop.readCreditard("creditcards.txt");		//To load the creditcards
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 20);
		double price = shop.data.saleTransactions.get(1).calculatePrice();
		//System.out.println(price);
		
		assertFalse(shop.receiveCreditCardPayment(1, "4485370086510891"));			//4485370086510891 is the first creditCardCode in creditcards.txt
		
		assertEquals(150,shop.data.creditCards.get("4485370086510891"),0.01);
	}
	
	@Test
	public void validPaymentWithCardTest() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");
		shop.data.productTypes.get(prodID).addQuantity(50);
		
		shop.readCreditard("creditcards.txt");		//To load the creditcards
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		double price = shop.data.saleTransactions.get(1).calculatePrice();
		//System.out.println(price);
		
		assertTrue(shop.receiveCreditCardPayment(1, "4485370086510891"));			//4485370086510891 is the first creditCardCode in creditcards.txt
		
		assertEquals(50,shop.data.creditCards.get("4485370086510891"),0.01);
	}
	

}
