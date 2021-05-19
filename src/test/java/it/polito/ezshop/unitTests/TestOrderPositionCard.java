package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.model.Order;
import it.polito.ezshop.model.ProductType;
import jdk.jfr.Timestamp;
import it.polito.ezshop.model.Position;
import it.polito.ezshop.model.Card;
import it.polito.ezshop.model.Customer;

import java.time.LocalDate;

public class TestOrderPositionCard{
    
    /* Test Class "Order" */
    @Test
    public void testOrder () {
        ProductType product = new ProductType("A", "B", 1.1, 1,"C");
        // ProductType(note, barCode, pricePerUnit, id, productDescription)
        LocalDate date = LocalDate.of(2021,5,16);
        Order order = new Order(product, 2.5, 5, 12, "ABC", date);
        // Order(product, pricePerUnit, quantity, orderId, status, date)

        // Test Set and Get Balance
        order.setBalanceId(5);
		assertEquals(order.getBalanceId(), 5, 0);

        // Test Set and Get ProductCode
        assertEquals(order.getProductCode(), "B");
		order.setProductCode("ABC123");
		assertEquals(order.getProductCode(), "ABC123");

        // Test Set and Get PricePerUnit
        assertEquals(order.getPricePerUnit(), 2.5, 0);		
		order.setPricePerUnit(5.5);
		assertEquals(order.getPricePerUnit(), 5.5, 0);

        // Test Set and Get Quantity
        assertEquals(order.getQuantity(), 5, 0);
		order.setQuantity(6);
		assertEquals(order.getQuantity(), 6, 0);

        // Test Set and Get Status
		assertEquals(order.getStatus(), "ABC");
		order.setStatus("K12");
		assertEquals(order.getStatus(), "K12");

        // Test Set and Get Order ID
        assertEquals(order.getOrderId(),12, 0);
		order.setOrderId(5);
		assertEquals(order.getOrderId(), 5, 0);
    }

    /* Test Class "Position" */
    @Test
    public void testPosition() { 
        Position position = new Position("this is a position");
        ProductType product = new ProductType("A", "B", 1.1, 1,"C");
        
        // Test Set and Get Product
        position.setProduct(product);
		assertTrue(position.getProduct() == product);

        // Test Set and Get Position
        position.setPosition("this is a position!");
		assertTrue(position.getPosition() == "this is a position!");
    }

    /* Test Class "Card" */
    @Test
    public void testCard() { 
        Card card = new Card(123);
        Customer customer = new Customer("ABC",456);

        // Test Set and Get Customer
        card.setCustomer(customer);
		assertTrue(card.getCustomer() == customer);

        // Test Add and Get Points
        assertEquals(0,card.getPoints(),0.01);
		card.setCustomer(customer);
		card.addPoints(10);
		assertEquals(10,card.getPoints(),0.01);
		assertEquals(10,customer.getPoints(),0.01);
		card.addPoints(-5);
		assertEquals(5,card.getPoints(),0.01);
		assertEquals(5,customer.getPoints(),0.01);

        // Test Get Card Number
        assertEquals(card.getCardNumber(), "123");
    }
}