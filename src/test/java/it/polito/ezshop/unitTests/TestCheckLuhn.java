package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import it.polito.ezshop.data.EZShop;


public class TestCheckLuhn {

		
		EZShop prova = new EZShop() ; 
		
		@Test
		public void stringIsNull(){
			assertFalse(prova.checkLuhn(null)); //T1
		}
		
		@Test
		public void cardLenghtLessThan8(){
			assertFalse(prova.checkLuhn("421")); //T2
			assertFalse(prova.checkLuhn("")); //T2b1
			assertFalse(prova.checkLuhn("7777777")); //T2b2

		}
		
		@Test
		public void cardLenghtMoreThan18(){
			assertFalse(prova.checkLuhn("95391096430642596731")); //T3
		}
		

		
		@Test
		public void cardValid(){
			assertTrue(prova.checkLuhn("017235888619510")); //T4
			assertTrue(prova.checkLuhn("58028507")); //T4b1
			assertTrue(prova.checkLuhn("9477782328153753793")); //T4b2
		}

		@Test
		public void cardNotValid(){
			assertFalse(prova.checkLuhn("3316538977")); //T5
			assertFalse(prova.checkLuhn("58028506")); //T5b1
			assertFalse(prova.checkLuhn("9477782328153753792")); //T5b2

		}
		@Test
		public void cardNotDigits() {
			assertFalse(prova.checkLuhn("33165f8977b")); //T6
			assertFalse(prova.checkLuhn("94777823w28153753792")); //T6

		}

	
	
}
