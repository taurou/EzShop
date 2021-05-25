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

public class EndReturnTransactionIntegrationTest {
	
	it.polito.ezshop.data.EZShop shop;

	
	@Before
	public void setUp() {
		
		shop = new it.polito.ezshop.data.EZShop(1); 
		
	}
	
	@Test
	public void alreadyCommitedTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidPaymentException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		shop.startSaleTransaction();
		shop.receiveCashPayment(1, 1);
		
		shop.startReturnTransaction(1);
		
		shop.endReturnTransaction(1, true);
		assertFalse(shop.endReturnTransaction(1, true));
		
	}
	
	@Test
	public void notAlreadyCommitedTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidPaymentException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");	
		shop.data.productTypes.get(prodID).addQuantity(50);
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		shop.receiveCashPayment(1, 100);
		
		shop.startReturnTransaction(1);
		
		//System.out.println(shop.data.returnSaleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		//System.out.println(shop.data.saleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		
		shop.returnProduct(1, "6291041500213", 6);
		
		//System.out.println(shop.data.returnSaleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		//System.out.println(shop.data.saleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		
		assertTrue(shop.endReturnTransaction(1, true));
		
		//System.out.println(shop.data.returnSaleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		//System.out.println(shop.data.saleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		assertEquals(6, shop.data.returnSaleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum() );
		assertEquals(4, shop.data.saleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum() );
		
	}
	
	@Test
	public void deleteReturnTransTest() throws InvalidTransactionIdException, UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidPaymentException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		int prodID = shop.createProductType("olio", "6291041500213", 10, "firstproduct");	
		shop.data.productTypes.get(prodID).addQuantity(50);
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 10);
		shop.receiveCashPayment(1, 100);
		
		shop.startReturnTransaction(1);
		
		shop.returnProduct(1, "6291041500213", 6);
		
		//System.out.println(shop.data.returnSaleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		//System.out.println(shop.data.saleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		
		assertTrue(shop.endReturnTransaction(1, false));
		//System.out.println(shop.data.returnSaleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		//System.out.println(shop.data.saleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		assertEquals(10,shop.data.saleTransactions.get(1).products.values().stream().mapToInt(x-> x.getAmount()).sum());
		
		
	}

}
