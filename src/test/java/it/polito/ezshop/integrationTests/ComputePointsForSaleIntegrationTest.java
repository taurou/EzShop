package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidCustomerCardException;
import it.polito.ezshop.exceptions.InvalidCustomerIdException;
import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidProductIdException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class ComputePointsForSaleIntegrationTest {

	it.polito.ezshop.data.EZShop shop ; 
	Integer transactionId;
	
	@Before
	public void setUp() throws InvalidCustomerNameException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidCustomerIdException, InvalidCustomerCardException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException {
		shop= new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.login("admin", "adminpass");
		shop.createProductType("Banana", "978020137962", 1.50, "Bananas");
		shop.createProductType("Apple", "978020137238", 3.30, "Apples");
		shop.createProductType("Strawberry", "978020131243", 4.30, "Strawberries");
		shop.createProductType("Tomato", "978020231240", 2.00, "Tomatoes");
		
		shop.updatePosition(shop.data.barcodeToId.get("978020137962"), "10-20-20");
		shop.updatePosition(shop.data.barcodeToId.get("978020137238"), "10-20-40");
		shop.updatePosition(shop.data.barcodeToId.get("978020131243"), "10-20-10");
		shop.updatePosition(shop.data.barcodeToId.get("978020231240"), "10-20-03");
		
		shop.updateQuantity(shop.data.barcodeToId.get("978020137962"), 10);
		shop.updateQuantity(shop.data.barcodeToId.get("978020137238"), 10);
		shop.updateQuantity(shop.data.barcodeToId.get("978020131243"), 10);
		shop.updateQuantity(shop.data.barcodeToId.get("978020231240"), 10);
	}
	@Test public void addProductsTest1() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException {
		Integer transactionId  = shop.startSaleTransaction(); 
		
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 2));
		assertTrue(shop.addProductToSale(transactionId, "978020231240", 3));
		assertTrue(shop.endSaleTransaction(transactionId));
		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 9, 0.001);
		assertEquals(shop.computePointsForSale(transactionId), 0);

	}
	
	@Test public void addProductsTest2() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException {
		Integer transactionId  = shop.startSaleTransaction();
		
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 6));
		assertTrue(shop.addProductToSale(transactionId, "978020231240", 3));
		assertEquals(shop.computePointsForSale(transactionId), 1);

	}
	@Test public void addProductsTest3() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException {
		Integer transactionId  = shop.startSaleTransaction();
		
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 4));
		assertTrue(shop.addProductToSale(transactionId, "978020231240", 7));
		assertTrue(shop.endSaleTransaction(transactionId));
		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 20, 0.001);
		assertEquals(shop.computePointsForSale(transactionId), 2);
		
		assertEquals(shop.computePointsForSale(48), -1);


	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void thrownTest1() throws InvalidTransactionIdException, UnauthorizedException {
		shop.endSaleTransaction(-10);
	}

	@Test (expected = InvalidTransactionIdException.class)
	public void thrownTest2() throws InvalidTransactionIdException, UnauthorizedException {
		shop.endSaleTransaction(null);
	}

	
	
}