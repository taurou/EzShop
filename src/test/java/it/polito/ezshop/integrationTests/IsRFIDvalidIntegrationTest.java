package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidOrderIdException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidProductIdException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRFIDException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class IsRFIDvalidIntegrationTest {
	
    EZShop shop = new EZShop(1);
	//TC1
	@Test
	public void testNull() {
		assertFalse(shop.isRFIDvalid(null));
	}
	//TC2
	/*
	@Test
	public void testNonString() {
		assertFalse(ezshop.isRFIDvalid(123));
	}
	*/
	
	@Test
	public void testBlank() {
		assertFalse(shop.isRFIDvalid(" "));
	}
	
	//TC3
	@Test
	public void testLength() {
		assertFalse(shop.isRFIDvalid(""));
	}	
	
	@Test
	public void testNotDigits() {
		assertFalse(shop.isRFIDvalid("00000000ABCD"));
	}
	
	// TC4
	@Test
	public void testUsedRFID() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException, InvalidOrderIdException, InvalidLocationException, InvalidRFIDException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductIdException {
		shop.createUser("admin", "admin", "Administrator");	
		shop.login("admin", "admin");
		shop.createProductType("Banana", "978020137962", 1.50, "Bananas");
		shop.updatePosition(shop.data.barcodeToId.get("978020137962"), "10-20-20");
		shop.updateQuantity(shop.data.barcodeToId.get("978020137962"), 10);
		int OrderId = shop.issueOrder("978020137962", 10, 1.50);
		shop.payOrder(OrderId);
		shop.recordOrderArrivalRFID(OrderId,"000000009853");
		
		assertFalse(shop.isRFIDvalid("000000009853"));
	}
	
	@Test
	public void VerifyIsRFIDvalid() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException, InvalidOrderIdException, InvalidLocationException, InvalidRFIDException {
		assertTrue(shop.isRFIDvalid("000000001234"));
	}
	
}