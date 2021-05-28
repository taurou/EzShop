package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class CheckIfAdminCashManTest {
	
	it.polito.ezshop.data.EZShop shop = new it.polito.ezshop.data.EZShop(1);
	
	@Test (expected=UnauthorizedException.class)
	public void noLoggedUserTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		assertNull(shop.login("Aldo", "admin"));	//login returns null if there is no user logged in
		shop.checkifAdminCashMan();
	
	}
	
	@Test
	public void validRightsTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Aldo", "admin", "Administrator");
		shop.login("Aldo", "admin");
		assertFalse(shop.checkifAdminCashMan());
	} 
 
}
