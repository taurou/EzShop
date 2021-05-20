package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.model.Position;
import it.polito.ezshop.model.ProductType;

public class TestPosition {
	
	Position position = new Position("this is a position");
	
	ProductType product = new ProductType("A", "B", 1.1, 1,"C");
	
	@Test
	public void testgetProduct(){
		position.setProduct(product);
		assertTrue(position.getProduct() == product);
	}
	
	
	@Test
	public void testgetPosition(){
		position.setPosition("this is a position!");
		assertTrue(position.getPosition() == "this is a position!");
	}
	
}
