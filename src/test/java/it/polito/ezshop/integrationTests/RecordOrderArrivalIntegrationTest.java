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
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.Order;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.User;

public class RecordOrderArrivalIntegrationTest {

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
	public void payedOrCompletedOrderTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
		assertTrue(shop.recordOrderArrival(121768));	
		assertEquals(shop.data.orders.get(121768).getStatus(), "COMPLETED");
		
		
		product = new ProductType("Prodottoprova2", "6291041500212", 27.30, 14022, "Prodottodescription2");
		product.setLocation("10-10-10");
		product.setQuantity(9);

		shop.data.productTypes.put(14022, product);
		shop.data.barcodeToId.put("6291041500212", 14022);
		
		o = new Order (product, 27.00, 50, 121767, "COMPLETED", LocalDate.now());
		shop.data.orders.put(121767, o);
		
		assertTrue(shop.recordOrderArrival(121767));	
		assertEquals(shop.data.orders.get(121767).getStatus(), "COMPLETED");

		
		assertEquals(shop.data.productTypes.get(14029).getQuantity(), Integer.valueOf(107));
		assertEquals(shop.data.productTypes.get(14022).getQuantity(), Integer.valueOf(9));
	
		
	
	}

	@Test
	public void unPaidOrderTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
		product = new ProductType("Prodottoprova2", "6291041500212", 27.30, 14022, "Prodottodescription2");
		product.setLocation("10-10-10");
		product.setQuantity(9);

		shop.data.productTypes.put(14022, product);
		shop.data.barcodeToId.put("6291041500212", 14022);
		
		o = new Order (product, 27.00, 50, 121767, "ISSUED", LocalDate.now());
		shop.data.orders.put(121767, o);
		
		assertFalse(shop.recordOrderArrival(121767));	
		assertEquals(shop.data.orders.get(121767).getStatus(), "ISSUED");

	}
	
	
	
	@Test (expected = UnauthorizedException.class)
	public void userIsAdminTest() throws InvalidOrderIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidLocationException{
		shop.createUser("notadmin", "notadminpass", "Cashier");
		shop.login("notadmin", "notadminpass");
		shop.recordOrderArrival(12168);		
	}
	
	@Test (expected = UnauthorizedException.class)
	public void userIsUserNullTest() throws InvalidOrderIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidLocationException{
		shop.data.loggedInUser=null;
		shop.recordOrderArrival(12168);		
	}
	
	
	@Test (expected = InvalidOrderIdException.class)
	public void invalidOrderIdTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
		shop.recordOrderArrival(-12168);		

	}
	
	
	@Test (expected = InvalidLocationException.class)
	public void invalidLocation() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
	
		product = new ProductType("Prodottoprova2", "6291041500212", 27.30, 14022, "Prodottodescription2");
		product.setQuantity(9);
		shop.data.productTypes.put(14022, product);
		shop.data.barcodeToId.put("6291041500212", 14022);
		o = new Order (product, 27.00, 50, 121767, "PAYED", LocalDate.now());
		shop.data.orders.put(121767, o);
		shop.recordOrderArrival(121767);
		
	}
	
	@Test
	public void nullOrderOrProductTest() throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException{
		assertFalse(shop.recordOrderArrival(1268));	
		shop.data.productTypes.remove(14029);
		assertFalse(shop.recordOrderArrival(121768));	

	}	

	
	
}

