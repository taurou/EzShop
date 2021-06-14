package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidLocationException;
import it.polito.ezshop.exceptions.InvalidOrderIdException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRFIDException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.Order;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.User;

public class RecordOrderArrivalRFIDIntegrationTest {

	User u;
	Order o; 
	ProductType product;
	it.polito.ezshop.data.EZShop shop;	
	
	
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException{
		
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("admin", "adminpass", "Administrator");
		shop.login("admin", "adminpass");
		
		product = new ProductType("Prodottoprova", "6291041500213", 29.30, 14029, "Prodottodescription");
		product.setLocation("10-10-10");
		product.setQuantity(7);
		shop.data.productTypes.put(14029, product);
		shop.data.barcodeToId.put("6291041500213", 14029);
		
		o = new Order (product, 27.00, 100, 121768, "PAYED", LocalDate.now());
		shop.data.orders.put(121768, o);
		
	}

	@Test
	public void payedOrCompletedOrderTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {
		assertTrue(shop.recordOrderArrivalRFID(121768, "000000001000"));	
		assertEquals(shop.data.orders.get(121768).getStatus(), "COMPLETED");
		assertEquals(shop.data.RFIDtoBarcode.get("1000"), "6291041500213" );
		assertEquals(shop.data.RFIDtoBarcode.get("1099"), "6291041500213" );
		
		
		product = new ProductType("Prodottoprova2", "6291041500212", 27.30, 14022, "Prodottodescription2");
		product.setLocation("10-10-10");
		product.setQuantity(9);

		shop.data.productTypes.put(14022, product);
		shop.data.barcodeToId.put("6291041500212", 14022);
		
		o = new Order (product, 27.00, 50, 121767, "COMPLETED", LocalDate.now());
		shop.data.orders.put(121767, o);
		
		assertTrue(shop.recordOrderArrivalRFID(121767, "000000001100"));	
		assertEquals(shop.data.orders.get(121767).getStatus(), "COMPLETED");

		
		assertEquals(shop.data.productTypes.get(14029).getQuantity(), Integer.valueOf(107));
		assertEquals(shop.data.productTypes.get(14022).getQuantity(), Integer.valueOf(9));
		
		assertEquals(shop.data.RFIDtoBarcode.get("1100"), "6291041500212" );
		assertEquals(shop.data.RFIDtoBarcode.get("1149"), "6291041500212" );
		

		
	
	}
	@Test (expected = InvalidRFIDException.class)
	public void overlapRFIDsTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {
		assertTrue(shop.recordOrderArrivalRFID(121768, "000000001000"));	
		assertEquals(shop.data.orders.get(121768).getStatus(), "COMPLETED");
		assertTrue(shop.data.RFIDtoBarcode.containsKey("1000"));
		assertTrue(shop.data.RFIDtoBarcode.containsKey("1099"));
		
		product = new ProductType("Prodottoprova2", "6291041500212", 27.30, 14022, "Prodottodescription2");
		product.setLocation("10-10-10");
		product.setQuantity(9);

		shop.data.productTypes.put(14022, product);
		shop.data.barcodeToId.put("6291041500212", 14022);
		
		o = new Order (product, 27.00, 50, 121767, "COMPLETED", LocalDate.now());
		shop.data.orders.put(121767, o);
		
		shop.recordOrderArrivalRFID(121767, "000000001099");			
	
	}

	
	
	
	@Test (expected = UnauthorizedException.class)
	public void userIsAdminTest() throws InvalidOrderIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidLocationException, InvalidRFIDException{
		shop.createUser("notadmin", "notadminpass", "Cashier");
		shop.login("notadmin", "notadminpass");
		shop.recordOrderArrivalRFID(12168, "000000001099");		
	}
	
	@Test (expected = UnauthorizedException.class)
	public void userIsUserNullTest() throws InvalidOrderIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidLocationException, InvalidRFIDException{
		shop.data.loggedInUser=null;
		shop.recordOrderArrivalRFID(12168, "000000001099");		
	}
	
	
	@Test (expected = InvalidOrderIdException.class)
	public void invalidOrderIdTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {
		shop.recordOrderArrivalRFID(-12168, "000000001099");		

	}
	
	
	@Test (expected = InvalidLocationException.class)
	public void invalidLocation() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {
	
		product = new ProductType("Prodottoprova2", "6291041500212", 27.30, 14022, "Prodottodescription2");
		product.setQuantity(9);
		shop.data.productTypes.put(14022, product);
		shop.data.barcodeToId.put("6291041500212", 14022);
		o = new Order (product, 27.00, 50, 121767, "PAYED", LocalDate.now());
		shop.data.orders.put(121767, o);
		shop.recordOrderArrivalRFID(121767, "000000001099");
		
	}
	
	@Test
	public void nullOrderOrProductTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException{
		assertFalse(shop.recordOrderArrivalRFID(1268,  "000000001099"));	
		shop.data.productTypes.remove(14029);
		assertFalse(shop.recordOrderArrivalRFID(121768,  "000000001099"));	

	}	

	@Test (expected = InvalidRFIDException.class)
	public void RFIDNULLTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {
		shop.recordOrderArrivalRFID(121768, null);			

	}
	
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest1() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {
		shop.recordOrderArrivalRFID(121768, "    ");			

	}
	
	@Test (expected = InvalidRFIDException.class)
	public void InvalidRFIDTest2() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException, InvalidRFIDException {
		shop.recordOrderArrivalRFID(121768, "2390102 ");			

	}

	
	
	
}

