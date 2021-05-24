package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;

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

public class ReceiveCashPaymentIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;

	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = InvalidPaymentException.class)
	public void invalidCashTest() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.receiveCashPayment(1, -10);
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidIdTest() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		shop.receiveCashPayment(-1, 10);
	}
	
	@Test 
	public void saleNotExistTest() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		assertEquals(-1,shop.receiveCashPayment(1, 10),0.01);
	}
	
	@Test 
	public void cashNotEnoughTest() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");	
		shop.data.productTypes.get(prodID).addQuantity(50);
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		shop.data.saleTransactions.get(1).calculatePrice();
		assertEquals(-1,shop.receiveCashPayment(1, 10),0.01);	//10 is not enough, total price is 100
	}
	
	@Test 
	public void validPaymentTest() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");	
		shop.data.productTypes.get(prodID).addQuantity(50);
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		double price = shop.data.saleTransactions.get(1).calculatePrice();
		double cash = 150;
		assertEquals(cash-price,shop.receiveCashPayment(1, cash),0.01);	//10 is not enough, total price is 100
		//System.out.println(shop.data.balanceOperations.get(1).getMoney());
	}

}
