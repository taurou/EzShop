package it.polito.ezshop.unitTests;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import it.polito.ezshop.model.BalanceOperation;
import java.time.LocalDate;


public class TestBalanceOperation {

	BalanceOperation transaction;
	LocalDate day;
	
	@Before public void setUp() {
		day = LocalDate.of(2021, 01, 29);
		transaction = new BalanceOperation(121, day, -108.30, "ORDER")	;		
	}
	@Test
	public void testConstructor() {
		assertEquals(transaction.getBalanceId(), 121);
		assertEquals(transaction.getMoney(), -108.30, 0);
		assertEquals(transaction.getBalanceId(), 121);
		assertEquals(transaction.getType(), "ORDER");
		assertTrue(transaction.getDate().isEqual(LocalDate.of(2021, 1, 29)));
	}
	@Test
	public void testGettersAndSetters() {
		day = LocalDate.of(2021,  02,  20);
		transaction.setBalanceId(122);
		assertEquals(transaction.getBalanceId(), 122);
		transaction.setDate(day);
		assertTrue(transaction.getDate().isEqual(LocalDate.of(2021, 2, 20)));
		transaction.setMoney(12.30);
		assertEquals(transaction.getMoney(), 12.30, 0);
		transaction.setType("SALE");
		assertEquals(transaction.getType(), "SALE");
	}

	
}