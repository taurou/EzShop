package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidCreditCardException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class ReturnCashPaymentIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;

	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidId1Test() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.returnCashPayment(null);
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidId2Test() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.returnCashPayment(-1);
		
	}
	
	@Test 
	public void returnTraNotExistTest() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		assertEquals(-1,shop.returnCashPayment(1),0.01);
		
	}
	
	@Test 
	public void returnTraNotEndedTest() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");	
		shop.data.productTypes.get(prodID).addQuantity(50);
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		shop.receiveCashPayment(1, 100);
		
		shop.startReturnTransaction(1);
		shop.returnProduct(1, "6291041500213", 6);
		
		assertEquals(-1,shop.returnCashPayment(1),0.01);
		
	}
	
	@Test 
	public void validReturnTraTest() throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");	
		shop.data.productTypes.get(prodID).addQuantity(50);
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		shop.receiveCashPayment(1, 100);
		
		shop.startReturnTransaction(1);
		shop.returnProduct(1, "6291041500213", 6);
		shop.endReturnTransaction(1, true);
		
		
		
		assertEquals(shop.data.returnSaleTransactions.get(1).calculatePrice(),shop.returnCashPayment(1),0.01);
		
		assertEquals(shop.data.returnSaleTransactions.get(1).getPrice(),shop.data.balanceOperations.get(2).getMoney(),0.01);
		
		
	}
}
