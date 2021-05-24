package it.polito.ezshop.integrationTests;


import static org.junit.Assert.assertEquals;

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
import it.polito.ezshop.model.SaleTransaction;

public class ComputeBalanceIntegrationTest { 
	
	it.polito.ezshop.data.EZShop shop = new it.polito.ezshop.data.EZShop(1); 
	
	@Test
	public void balanceTest() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidPaymentException {
		ProductType p = new ProductType("firstproduct", "6291041500213", (double) 10, 1, "olio");
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw"); 
		
		//Only the ShopManager can create a ProductType
		int prodID = shop.createProductType(p.getProductDescription(), p.getBarCode(), p.getPricePerUnit(), p.getNote());
		shop.data.productTypes.get(prodID).addQuantity(50);
		//System.out.println(shop.data.productTypes.get(prodID).getQuantity());
		
		shop.startSaleTransaction();
		shop.addProductToSale(1, "6291041500213", 6);
		
		
		SaleTransaction t = shop.data.saleTransactions.get(1);
		t.calculatePrice();
		assertEquals(6, t.products.values().stream().mapToInt(x -> x.getAmount()).sum(),0.01);
		//System.out.println(shop.data.saleTransactions.get(1).calculatePrice());
		//System.out.println(shop.data.saleTransactions.get(1).getPrice());
		double change = shop.receiveCashPayment(1, 70);
		assertEquals(10, change, 0.01);
		//System.out.print(change); 
		shop.endSaleTransaction(1);
		assertEquals(60, shop.computeBalance(),0.01);
	}
	
	@Test
	public void balanceZeroTest() throws UnauthorizedException, InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop.createUser("Fra", "psw", "ShopManager");
		shop.login("Fra", "psw");
		
		assertEquals(0, shop.computeBalance(),0.01);
	}

}
