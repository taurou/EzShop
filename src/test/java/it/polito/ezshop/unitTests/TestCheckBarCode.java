package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezshop.data.EZShop;

public class TestCheckBarCode {
    EZShop ezshop = new EZShop();
	
	@Test
	public void testNull() {
		assertTrue(ezshop.checkBarcodeValidity(null));
	}
	
	@Test
	public void testBlank() {
		assertTrue(ezshop.checkBarcodeValidity("    "));
	}
	
	@Test
	public void testAllDigits1() {
		assertTrue(ezshop.checkBarcodeValidity("a1234"));
		
	}
	
	@Test
	public void testLengthNotCorrect() {
		assertTrue(ezshop.checkBarcodeValidity("1234")); 
	}
	
	@Test
	public void testRespectGs1() {
		assertFalse(ezshop.checkBarcodeValidity("6291041500213")); //a valid barcode number, example at https://www.gs1.org/services/how-calculate-check-digit-manually
		assertTrue(ezshop.checkBarcodeValidity("6291041500214"));	//an invalid barcode number
	}
	    
}
