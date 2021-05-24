package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class RecordBalanceUpdateIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;

	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = UnauthorizedException.class)
	public void noRightsTest () throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.recordBalanceUpdate(0);
		
	}
	
	@Test
	public void balanceNegativeTest () throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		
		assertFalse(shop.recordBalanceUpdate(-1));		//shop.computeBalance()+toBeAdded < 0
		
	}
	
	@Test
	public void creditAndDebitTest () throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		
		assertTrue(shop.recordBalanceUpdate(10));		
		assertSame(shop.data.balanceOperations.get(1).getType(),"CREDIT");
		
		assertTrue(shop.recordBalanceUpdate(-5));		
		assertSame(shop.data.balanceOperations.get(2).getType(),"DEBIT");
		//System.out.println(shop.computeBalance());
		
	}

}
