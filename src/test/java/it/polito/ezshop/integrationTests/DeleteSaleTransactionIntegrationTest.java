package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidPricePerUnitException;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidProductDescriptionException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import it.polito.ezshop.model.ProductType;

public class DeleteSaleTransactionIntegrationTest {
	 
	it.polito.ezshop.data.EZShop shop; 
	
	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1);  
		 
	}
	
	@Test (expected = UnauthorizedException.class)	
	public void noLoggedUserTest() throws InvalidTransactionIdException, UnauthorizedException {
		shop.deleteSaleTransaction(1);
	}
	
	//	There is not sense to test a role different from the three C,A,M. Because createUser throws directly InvalidRoleException
	/*@Test (expected = UnauthorizedException.class)
	public void noRightsTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cleaner");
		shop.login("Fra", "psw");
		shop.deleteSaleTransaction(1);
	}*/
	
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidSaleNumber1Test() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.deleteSaleTransaction(null);
		
	}
	
	@Test (expected = InvalidTransactionIdException.class)
	public void invalidSaleNumber2Test() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		
		shop.deleteSaleTransaction(-10);
		
	}
	
	
	@Test 
	public void impossibleDeletionTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "Cashier");
		shop.login("Fra", "psw");
		assertFalse(shop.deleteSaleTransaction(1));			// t==null, line 805
		
		shop.startSaleTransaction();
		shop.receiveCashPayment(1, 1);
		assertFalse(shop.deleteSaleTransaction(1));			//shop.deleteSaleTransaction(-10), line 805
		
	}
	
	@Test 
	public void deleteOpenTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
			
		
		shop.startSaleTransaction();
	
		assertTrue(shop.deleteSaleTransaction(1));		// status == "OPEN"
		
		
	}
	
	@Test  
	public void deleteClosedTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {
		ProductType p = new ProductType("firstproduct", "6291041500213", (double) 10, 1, "olio");
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		//System.out.println(shop.getAllProductTypes());
		
		//Only the ShopManager can create a ProductType
		int prodID = shop.createProductType(p.getProductDescription(), p.getBarCode(), p.getPricePerUnit(), p.getNote());	
		shop.data.productTypes.get(prodID).addQuantity(50);		//Initial quantity of olio in the shop
		assertEquals(50,shop.data.productTypes.get(prodID).getQuantity(),0.01);
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 5);
		assertEquals(45,shop.data.productTypes.get(prodID).getQuantity(),0.01);
		shop.endSaleTransaction(1);
		
		
		//System.out.println(shop.getAllProductTypes());
		
		assertTrue(shop.deleteSaleTransaction(1));		// status == "CLOSED"
		
		assertEquals(50,shop.data.productTypes.get(prodID).getQuantity(),0.01);
		
	}

}
