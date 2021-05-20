package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.data.EZShop;

public class testcheckPosition {
	
    EZShop ezshop = new EZShop();
	//TC1
	@Test
	public void testNull() {
		assertFalse(ezshop.checkPosition(null));
	}
	//TC2
	@Test
	public void testNonString() {
		assertFalse(ezshop.checkPosition("123"));
	}
	//TC3
	@Test
	public void testLength() {
		// position.length < 3
		assertFalse(ezshop.checkPosition("1-2"));
		// position.length > 3
		assertFalse(ezshop.checkPosition("1-2-3-4"));
		// position.length = 3
		assertTrue(ezshop.checkPosition("1-2-3"));
	}	
	// TC4
	@Test
	public void testPattern() {
		assertFalse(ezshop.checkPosition("1-2-3-"));
		assertFalse(ezshop.checkPosition("-1-2-3"));
		assertFalse(ezshop.checkPosition("12-3"));
		assertFalse(ezshop.checkPosition("1-23"));
	}
	
}