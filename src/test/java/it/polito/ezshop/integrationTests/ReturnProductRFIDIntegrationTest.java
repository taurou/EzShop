package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidOrderIdException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRFIDException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.Order;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.User;

public class ReturnProductRFIDIntegrationTest {
	
	User u;
	Order o; 
	ProductType product;
	it.polito.ezshop.data.EZShop shop;	
	Integer transactionId;
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException, InvalidTransactionIdException, InvalidQuantityException {
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

		transactionId  = shop.startSaleTransaction();
		shop.addProductToSaleRFID(transactionId, "000000001101");
		shop.addProductToSaleRFID(transactionId, "000000001102");
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidId1Test() throws InvalidTransactionIdException, InvalidRFIDException, UnauthorizedException {
		shop.returnProductRFID(null, "000000001101");
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidId2Test() throws InvalidTransactionIdException, InvalidRFIDException, UnauthorizedException {
		shop.returnProductRFID(-1, "000000001101");
	}
	
	@Test (expected = UnauthorizedException.class)
	public void noRightsTest() throws InvalidTransactionIdException, InvalidRFIDException, UnauthorizedException {
		shop.logout();
		shop.returnProductRFID(1, "000000001101");
	}
	
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest4() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		shop.returnProductRFID(transactionId, null);
	}
	
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest3() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		shop.returnProductRFID(transactionId, " ");
	}
	
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest2() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		shop.returnProductRFID(transactionId, "");

	}
	
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest1() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		shop.returnProductRFID(transactionId, "000000002101012345");
	}
	
	
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest5() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		shop.returnProductRFID(transactionId, "cd");
	}

	@Test 
	public void notExistingRFIDTest() throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException {
		assertFalse(shop.returnProductRFID(transactionId, "000000002101"));
	}
	
	@Test
	public void transNotExistTest() throws InvalidTransactionIdException, InvalidRFIDException, UnauthorizedException {
		assertFalse(shop.returnProductRFID(transactionId, "000000001101"));
	}
	
	@Test
	public void goodTest() throws InvalidTransactionIdException, InvalidRFIDException, UnauthorizedException, InvalidPaymentException {
		shop.receiveCashPayment(1, 100);
		shop.startReturnTransaction(transactionId);  
		assertTrue(shop.returnProductRFID(transactionId, "000000001101"));
	} 
	
}
