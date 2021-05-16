package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import it.polito.ezshop.model.Order;
import it.polito.ezshop.model.ProductType;
import java.time.LocalDate;


public class TestOrder {
	
	ProductType product = new ProductType("A", "B", 1.1, 1,"C");
	//ProductType(note, barCode, pricePerUnit, id, productDescription)
	LocalDate date = LocalDate.of(2021,5,16);
	Order order = new Order(product, 2.5, 5, 12, "ABC", date); // notice: localDate is an argument never used.
	//Order(product, pricePerUnit, quantity, orderId, status, date) {
	
	@Test
	public void testgetBalanceId(){
		order.setBalanceId(5);
		assertEquals(order.getBalanceId(), 5, 0);
	}
	
	@Test
	public void testgetProductCode(){
		assertEquals(order.getProductCode(), "B");
		order.setProductCode("ABC123");
		assertEquals(order.getProductCode(), "ABC123");
	}
	
	@Test
	public void testgetPricePerUnit(){
		assertEquals(order.getPricePerUnit(), 2.5, 0);		
		order.setPricePerUnit(5.5);
		assertEquals(order.getPricePerUnit(), 5.5, 0);
	}
	
	@Test
	public void testgetQuantity(){
		assertEquals(order.getQuantity(), 5, 0);
		order.setQuantity(6);
		assertEquals(order.getQuantity(), 6, 0);
	}
	
	@Test
	public void testgetStatus(){
		assertEquals(order.getStatus(), "ABC");
		order.setStatus("K12");
		assertEquals(order.getStatus(), "K12");
	}
	
	@Test
	public void testgetOrderId(){
		assertEquals(order.getOrderId(),12, 0);
		order.setOrderId(5);
		assertEquals(order.getOrderId(), 5, 0);
	}
}
	
	
	