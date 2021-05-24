package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class StartReturnTransactionIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;
	
	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void nullTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.startReturnTransaction(null);
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidSaleNumberTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.startReturnTransaction(-5);
		
	}
	
	@Test
	public void saleNotExistTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		assertSame(-1, shop.startReturnTransaction(1));
		
	}
	
	
	@Test
	public void notPayedTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.startSaleTransaction();
		
		assertSame(-1, shop.startReturnTransaction(1));
		
	}
	
	@Test
	public void validReturnTransactionTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.startSaleTransaction();
		shop.receiveCashPayment(1, 1);
		
		shop.startSaleTransaction();
		shop.receiveCashPayment(2, 1);
		
		assertSame(1, shop.startReturnTransaction(2)); 
		assertSame(2, shop.startReturnTransaction(2));
		
	}
}
