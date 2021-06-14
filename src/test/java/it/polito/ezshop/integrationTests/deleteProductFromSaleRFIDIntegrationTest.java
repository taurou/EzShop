package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.*;

public class deleteProductFromSaleRFIDIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop ; 
	int OrderId;
	Integer transactionId;

	@Before
	public void setUp() throws InvalidCustomerNameException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerIdException, InvalidCustomerCardException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException, InvalidOrderIdException, InvalidRFIDException, InvalidTransactionIdException {
		shop= new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "admin", "Administrator");
		shop.createUser("cashier", "cashier", "Cashier");	
		shop.login("admin", "admin");
		
		// Product 1
		shop.createProductType("Banana", "978020137962", 1.50, "Bananas");
		shop.updatePosition(shop.data.barcodeToId.get("978020137962"), "10-20-20");
		shop.updateQuantity(shop.data.barcodeToId.get("978020137962"), 10);
		
		OrderId = shop.issueOrder("978020137962", 10, 1.50);
		shop.payOrder(OrderId);
		shop.recordOrderArrivalRFID(OrderId,"000000009853");
		//Product 2
		shop.createProductType("Apple", "978020137238", 1.50, "Apples");
		shop.updatePosition(shop.data.barcodeToId.get("978020137238"), "10-20-21");
		shop.updateQuantity(shop.data.barcodeToId.get("978020137238"), 10);
		OrderId = shop.issueOrder("978020137238", 10, 1.50);
		shop.payOrder(OrderId);
		shop.recordOrderArrivalRFID(OrderId,"000000001234");
		shop.logout();
	}
	
	@Test (expected = InvalidRFIDException.class)  
	public void testBlankRFID() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		shop.deleteProductFromSaleRFID(transactionId, " ");
		shop.logout();
	}
	
	@Test (expected = InvalidRFIDException.class)  
	public void testNullRFID() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		shop.deleteProductFromSaleRFID(transactionId, null);
		shop.logout();
	}
	
	@Test (expected = InvalidRFIDException.class)  
	public void testNotDigitRFID() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		shop.deleteProductFromSaleRFID(transactionId, "ABC");
		shop.logout();
	}
	
	@Test
	public void testNotRegisteredRFID() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		assertFalse(shop.deleteProductFromSaleRFID(transactionId, "000000001010"));
		shop.logout();
	}
	
	@Test (expected = UnauthorizedException.class)
	public void testNoLogin() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		assertFalse(shop.deleteProductFromSaleRFID(transactionId, "000000009853"));
	}
	
	@Test (expected = InvalidTransactionIdException.class)  
	public void testNullId() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		shop.deleteProductFromSaleRFID(null , "000000009853");
		shop.logout();
	}
	
	@Test (expected = InvalidTransactionIdException.class)  
	public void testZeroId() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		shop.deleteProductFromSaleRFID(0 , "000000009853");
		shop.logout();
	}
	
	@Test (expected = InvalidTransactionIdException.class)  
	public void testNegativeId() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		shop.deleteProductFromSaleRFID(-53, "000000009853");
		shop.logout();
	}
	
	@Test  
	public void testNotRegisteredId() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		assertFalse(shop.deleteProductFromSaleRFID(123, "000000009853"));
		shop.logout();
	}
	
	@Test
	public void testNotAddedRFIDToTransaction() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		assertFalse(shop.deleteProductFromSaleRFID(transactionId, "000000001234"));
		shop.logout();
	}
	
	@Test
	public void testNotAddedProductToTransaction() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		assertFalse(shop.deleteProductFromSaleRFID(transactionId, "000000009853"));
		shop.logout();
	}
	
	@Test
	public void VerifyDeleteProductFromSaleRFID() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException {
		shop.login("admin", "admin");
		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000009853");
		assertTrue(shop.deleteProductFromSaleRFID(transactionId, "000000009853"));
		shop.logout();
	}
	
	
	
	
}
