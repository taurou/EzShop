package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

public class AddProductToSaleIntegrationTesting {

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
	@Test public void addTwoProductsTest() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException {
		Integer transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 5));
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 3));
		assertEquals(shop.getProductTypeByBarCode("978020137962").getQuantity(), Integer.valueOf(2));
		assertFalse(shop.addProductToSale(transactionId, "978020137962", 5));
		assertTrue(shop.endSaleTransaction(transactionId));
		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 12, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().size(), 1);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getAmount(), 8);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getPricePerUnit(), 1.50, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getBarCode(), "978020137962");
		
		transactionId  = shop.startSaleTransaction(); //beginning of transaction n 2
		
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 2));
		assertFalse(shop.addProductToSale(transactionId, "978020137962", 3));
		assertEquals(shop.getProductTypeByBarCode("978020137962").getQuantity(), Integer.valueOf(0));
		assertTrue(shop.addProductToSale(transactionId, "978020231240", 3));
		assertEquals(shop.getProductTypeByBarCode("978020231240").getQuantity(), Integer.valueOf(7));
		assertTrue(shop.endSaleTransaction(transactionId));
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getAmount(), 2);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getPricePerUnit(), 1.50, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getBarCode(), "978020137962");
		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 9, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(1).getAmount(), 3);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(1).getPricePerUnit(), 2.00 , 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(1).getBarCode(), "978020231240");

	}

	
	
	@Test public void InvalidAmountAndNotExistingProductTest() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException {
		Integer transactionId  = shop.startSaleTransaction();
		assertFalse(shop.addProductToSale(transactionId, "978022311247", 5)); //not existing product
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 3));
		
		
		assertFalse(shop.addProductToSale(transactionId, "978022311247", 6)); //unavailable amount

		assertEquals(shop.getProductTypeByBarCode("978020137962").getQuantity(), Integer.valueOf(7));
		assertTrue(shop.endSaleTransaction(transactionId));
		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 4.50, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().size(), 1);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getAmount(), 3);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getPricePerUnit(), 1.50, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getBarCode(), "978020137962");

	}
	
	@Test public void InvalidTransactionOrStatusTest() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException {
		Integer transactionId  = shop.startSaleTransaction();
		assertFalse(shop.addProductToSale(212, "978022311247", 5)); //not existing transactionID
		
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 3));
		assertTrue(shop.endSaleTransaction(transactionId));
		assertFalse(shop.addProductToSale(transactionId, "978020137962", 1)); 

		assertEquals(shop.getProductTypeByBarCode("978020137962").getQuantity(), Integer.valueOf(7));
		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 4.50, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().size(), 1);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getAmount(), 3);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getPricePerUnit(), 1.50, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getBarCode(), "978020137962");

	}


	
	@Test (expected = InvalidQuantityException.class)
	public void InvalidQtyTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
		shop.addProductToSale(23, "978020137238", -5); //negative amount

	}
	@Test (expected = InvalidTransactionIdException.class)
	public void InvalidTransactionIdTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
		shop.addProductToSale(null, "978020137238", 5); 

	}
	@Test (expected = InvalidTransactionIdException.class)
	public void InvalidTransactionIdTest2() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
		shop.addProductToSale(-3, "978020137238", 5); 

	}
	
	
	
	
}


