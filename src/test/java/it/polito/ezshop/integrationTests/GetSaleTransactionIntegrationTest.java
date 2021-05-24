package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class GetSaleTransactionIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;
	
	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)	
	public void invalidIdTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.getSaleTransaction(-10); 
	}
	
	@Test
	public void tryOpenTransactionTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.startSaleTransaction();

		assertNull(shop.getSaleTransaction(1)); 
	}
	
	@Test
	public void tryClosedTransactionTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.startSaleTransaction();
		shop.endSaleTransaction(1);

		assertSame(shop.getSaleTransaction(1),shop.data.saleTransactions.get(1)); 
	}
	
	@Test
	public void tryPayedTransactionTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw"); 
		
		shop.startSaleTransaction();
		shop.receiveCashPayment(1, 1);

		assertSame(shop.getSaleTransaction(1),shop.data.saleTransactions.get(1));
	}
	
	@Test
	public void noRightTransactionTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.startSaleTransaction();
		shop.endSaleTransaction(1);
		
		assertNull(shop.getSaleTransaction(2));
	}
}
