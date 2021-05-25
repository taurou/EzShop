package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class DeleteReturnTransactionIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;

	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = UnauthorizedException.class)
	public void noLoggedTest() throws InvalidTransactionIdException, UnauthorizedException{
		shop.deleteReturnTransaction(1);
	}
	
	@Test (expected =  InvalidTransactionIdException.class)
	public void invalidId1Test() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		shop.deleteReturnTransaction(null);
	}
	
	@Test (expected =  InvalidTransactionIdException.class)
	public void invalidId2Test() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		shop.deleteReturnTransaction(-1);
	}
	
	@Test 
	public void tranNotExistTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		assertFalse(shop.deleteReturnTransaction(1));
	}
	
}
