package it.polito.ezshop.integrationTests;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class GetCreditsAndDebitsIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;

	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test (expected = UnauthorizedException.class)
	public void noRightsTest() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");  
		shop.login("Fra", "psw");
		
		shop.getCreditsAndDebits(null, null);
		
		
	}
	
	@Test 
	public void validGetsTest() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");  
		shop.login("Fra", "psw");
		
		shop.getCreditsAndDebits(null, null);
	
		
		shop.getCreditsAndDebits(null, null);
		shop.recordBalanceUpdate(10);
		shop.recordBalanceUpdate(-5);
		shop.recordBalanceUpdate(8);
		shop.getCreditsAndDebits(null, LocalDate.now().plusDays(10));
		shop.getCreditsAndDebits(LocalDate.now().minusDays(10), null);
		shop.getCreditsAndDebits(LocalDate.now().minusDays(10), LocalDate.now().plusDays(10));
	}

}
