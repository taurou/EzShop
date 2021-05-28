package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidCustomerCardException;
import it.polito.ezshop.exceptions.InvalidCustomerIdException;
import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.InvalidDiscountRateException;
import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidProductIdException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class ApplyDiscountRateToProductIntegrationTest {

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
	@Test public void discountOneProductsTest() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidDiscountRateException {
		Integer transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 6));
		assertTrue(shop.addProductToSale(transactionId, "978020231240", 3));
		
		assertTrue(shop.applyDiscountRateToProduct(transactionId, "978020137962", 0.30));

		assertTrue(shop.endSaleTransaction(transactionId));

		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 12.30 , 0.01);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getDiscountRate(), 0.3, 0.01);
		
		transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 2));
		assertTrue(shop.addProductToSale(transactionId, "978020231240", 3));
		

		assertTrue(shop.endSaleTransaction(transactionId));


		
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().size(), 2);

		transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 2));
		assertTrue(shop.addProductToSale(transactionId, "978020231240", 3));
		
		assertFalse(shop.applyDiscountRateToProduct(transactionId, "978020137962", 0));

		assertTrue(shop.endSaleTransaction(transactionId));

		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 9 , 0.01);

		
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getDiscountRate(), 0, 0.01);

	}

	
	@Test public void TransactionNotExistsTest() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidDiscountRateException {
		assertFalse(shop.applyDiscountRateToSale(212, 0.30));	
	}
	
	@Test public void TransactionAlreadyPaidTest() throws UnauthorizedException, InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, InvalidDiscountRateException, InvalidPaymentException {
		Integer transactionId  = shop.startSaleTransaction();
		
		assertTrue(shop.addProductToSale(transactionId, "978020137962", 3));
		assertTrue(shop.endSaleTransaction(transactionId));
		shop.receiveCashPayment(transactionId, 10.20);
		assertFalse(shop.applyDiscountRateToSale(transactionId, 0.3));
	}


	

	
	@Test (expected = InvalidTransactionIdException.class)
	public void InvalidTransactionIdTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidDiscountRateException {
		shop.applyDiscountRateToSale(null, 0.5); 

	}
	@Test (expected = InvalidTransactionIdException.class)
	public void InvalidTransactionIdTest2() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidDiscountRateException {
		shop.applyDiscountRateToSale(-87, 0.5); 

	}
	
	@Test (expected = InvalidDiscountRateException.class)
	public void InvalidDiscountTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidDiscountRateException {
		Integer transactionId  = shop.startSaleTransaction();
		shop.applyDiscountRateToSale(transactionId, 1.5); 

	}
	
	@Test (expected = InvalidDiscountRateException.class)
	public void InvalidDiscountTest2() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidDiscountRateException {
		Integer transactionId  = shop.startSaleTransaction();
		shop.applyDiscountRateToSale(transactionId, -1.5); 

	}

	
	
	
}
