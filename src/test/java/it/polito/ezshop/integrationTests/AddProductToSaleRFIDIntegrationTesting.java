package it.polito.ezshop.integrationTests;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidOrderIdException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRFIDException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.Order;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.User;

public class AddProductToSaleRFIDIntegrationTesting {

	User u;
	Order o; 
	ProductType product;
	it.polito.ezshop.data.EZShop shop;	
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.login("admin", "adminpass");
		
		product = new ProductType("Prodottoprova", "62910415002462", 3.00, 14029, "Prodottodescription");
		product.setLocation("10-10-10");
		product.setQuantity(7);
		shop.data.productTypes.put(14029, product);
		shop.data.barcodeToId.put("62910415002462", 14029);
		
		o = new Order (product, 2, 10, 121768, "PAYED", LocalDate.now());
		shop.data.orders.put(121768, o);
		
		shop.recordOrderArrivalRFID(121768, "000000001000");	
		
		product = new ProductType("Prodottoprova2", "62910415002424", 3.40 , 14022, "Prodottodescription2");
		product.setLocation("10-10-11");
		product.setQuantity(9);

		shop.data.productTypes.put(14022, product);
		shop.data.barcodeToId.put("62910415002424", 14022);
		
		o = new Order (product, 2.30 , 5, 121767, "PAYED", LocalDate.now());
		shop.data.orders.put(121767, o);
		
		shop.recordOrderArrivalRFID(121767, "000000001100");	

	}
	
	@Test
	public void addingProductsTest() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException, InvalidProductCodeException {
		Integer transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSaleRFID(transactionId, "000000001101"));
		assertTrue(shop.addProductToSaleRFID(transactionId, "000000001102"));
		assertTrue(shop.addProductToSaleRFID(transactionId, "000000001001"));
		
		assertEquals(shop.getProductTypeByBarCode(shop.data.RFIDtoBarcode.get("1001")).getQuantity(), Integer.valueOf(16));
		assertEquals(shop.getProductTypeByBarCode(shop.data.RFIDtoBarcode.get("1101")).getQuantity(), Integer.valueOf(12));

		
		assertTrue(shop.endSaleTransaction(transactionId));
		assertEquals(shop.getSaleTransaction(transactionId).getPrice(), 9.8, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().size(), 2);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(1).getAmount(), 1);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(1).getPricePerUnit(), 3, 0.001);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getAmount(), 2);
		assertEquals(shop.getSaleTransaction(transactionId).getEntries().get(0).getPricePerUnit(), 3.4, 0.001);
		
	}
	
	@Test 
	public void notExistingRFIDTest() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		Integer transactionId  = shop.startSaleTransaction();
		assertFalse(shop.addProductToSaleRFID(transactionId, "000000002101"));

	}
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest1() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		Integer transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSaleRFID(transactionId, "000000002101 "));

	}
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest2() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		Integer transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSaleRFID(transactionId, ""));

	}
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest3() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		Integer transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSaleRFID(transactionId, " "));

	}
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest4() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		Integer transactionId  = shop.startSaleTransaction();
		assertTrue(shop.addProductToSaleRFID(transactionId, null));

	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void InvalidTransactionIdTest() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidRFIDException {
		shop.addProductToSaleRFID(null, "000000001102");

	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void InvalidTransactionIdTest2() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidRFIDException {
		shop.addProductToSaleRFID(-3, "000000001102");
	}

	@Test 
	public void NotExistingTransactionIdTest3() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException, InvalidRFIDException {
		assertFalse(shop.addProductToSaleRFID(12, "000000001102"));
	}

	
}
