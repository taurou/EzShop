package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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

public class ReturnProductIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;
	
	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = InvalidProductCodeException.class)
	public void invalidBarCodeTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
	UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.returnProduct(1, "6291041500214", 4);
	}
	
	@Test (expected = InvalidQuantityException.class)
	public void invalidAmountTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
	UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.returnProduct(1, "6291041500213", -4);
	}
	
	@Test 
	public void transanctionNotExistTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
	UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		assertFalse(shop.returnProduct(1, "6291041500213", 4));
	}
	
	@Test 
	public void productNotExistTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
	UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		shop.startSaleTransaction();
		shop.receiveCashPayment(1, 1);
		shop.startReturnTransaction(1);
		
		assertFalse(shop.returnProduct(1, "6291041500213", 4));
	}
	
	@Test 
	public void productNotInTransactionTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
	UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		shop.createProductType("olio", "6291041500213", 10, "firstproduct");
		
		shop.startSaleTransaction();
		shop.receiveCashPayment(1, 1);
		shop.startReturnTransaction(1);
		
		assertFalse(shop.returnProduct(1, "6291041500213", 4));
	}
	
	@Test 
	public void amountTooHighTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
	UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");
		shop.data.productTypes.get(prodID).addQuantity(50);	
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		shop.receiveCashPayment(1, 100);
		shop.startReturnTransaction(1);
		
		assertFalse(shop.returnProduct(1, "6291041500213", 11));
	}
	
	@Test 
	public void validTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
	UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");
		shop.data.productTypes.get(prodID).addQuantity(50);	
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		shop.receiveCashPayment(1, 100);
		shop.startReturnTransaction(1);
		
		assertEquals(shop.data.returnSaleTransactions.get(1).getDiscountRate(),shop.data.saleTransactions.get(1).getDiscountRate(),0.01);
		assertTrue(shop.returnProduct(1, "6291041500213", 10));
	}
	
}
