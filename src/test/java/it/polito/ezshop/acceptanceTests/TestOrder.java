package it.polito.ezshop.acceptanceTests;

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
		assertTrue(order.getBalanceId() == 5);
	}
	
	@Test
	public void testgetProductCode(){
		assertTrue(order.getProductCode() == "B");
		order.setProductCode("ABC123");
		assertTrue(order.getProductCode() == "ABC123");
	}
	
	@Test
	public void testgetPricePerUnit(){
		assertTrue(order.getPricePerUnit() == 2.5);		
		order.setPricePerUnit(5.5);
		assertTrue(order.getPricePerUnit() == 5.5);
	}
	
	@Test
	public void testgetQuantity(){
		assertTrue(order.getQuantity() == 5);
		order.setQuantity(6);
		assertTrue(order.getQuantity() == 6);
	}
	
	@Test
	public void testgetStatus(){
		assertTrue(order.getStatus() == "ABC");
		order.setStatus("K12");
		assertTrue(order.getStatus() == "k12");
	}
	
	@Test
	public void testgetOrderId(){
		assertTrue(order.getOrderId() == 12);
		order.setOrderId(5);
		assertTrue(order.getOrderId() == 5);
	}
}
	
	
	