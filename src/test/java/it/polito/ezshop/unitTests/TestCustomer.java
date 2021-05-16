package it.polito.ezshop.unitTests;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polito.ezshop.model.Customer;
import it.polito.ezshop.model.Card;

public class TestCustomer {
	

	Customer c;
	@Before public void setUp(){
		c= new Customer ("Name Surname", 3287);
	}
	@Test   
	public void testConstructor() {
		assertEquals(c.getCustomerName(), "Name Surname");
		assertEquals(c.getId(), Integer.valueOf(3287));
		assertNull(c.getPoints());
		assertNull(c.getCard());
		assertNull(c.getCustomerCard());
	}
	@Test
	public void testInsertionOfCard() {
		Card crd = new Card(1995925699);
		c.setCard(crd);
		c.setCustomerCard(crd.getCardNumber());
		assertNotNull(c.getCard());
		assertEquals(c.getCustomerCard(), "1995925699");
	}
	@Test
	public void testOtherGettersAndSetters() {
		c.setCustomerName("Name2 Surname2");
		assertEquals(c.getCustomerName(), "Name2 Surname2");
		c.setId(1209);
		assertEquals(c.getId(), Integer.valueOf(1209));
		c.setPoints(1003);
		assertEquals(c.getPoints(), Integer.valueOf(1003));

	}
	
}
