package it.polito.ezshop.unitTests;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.polito.ezshop.model.ProductType;

public class TestProductType {
	
	ProductType prova = new ProductType("Milk", "978020137962", 3.50 , 101010,
			"Prova");
	
	@Before
	public void setUp() {
	prova = new ProductType("Milk", "978020137962", 3.50 , 101010,
				"Prova");
	}
	@Test
	public void testContructor() {
		assertEquals(prova.getNote() , "Milk");
		assertEquals(prova.getBarCode(), "978020137962");
		assertEquals(prova.getPricePerUnit(), Double.valueOf(3.50) );
		assertEquals(prova.getId(), Integer.valueOf(101010) );
		assertEquals(prova.getProductDescription(), "Prova");
		assertNull(prova.getLocation());
		assertEquals(prova.getQuantity(), Integer.valueOf(0));

	}
	@Test
	public void addQuantityNULLTest() { //T1
		assertEquals(prova.addQuantity(null), Integer.valueOf(0));

	}
	@Test
	public void addQuantityZeroTest() { //T2
		assertEquals(prova.addQuantity(0), Integer.valueOf(0));

	}
	@Test
	public void addQuantityPosTest() { //T3

		assertEquals(prova.addQuantity(8), Integer.valueOf(8));
		assertEquals(prova.addQuantity(1), Integer.valueOf(9));
	}
	@Test
	public void addQuantityNegTest() { //T4
		assertEquals(prova.addQuantity(-8), Integer.valueOf(-8));
		assertEquals(prova.addQuantity(-1), Integer.valueOf(-9));

	}
	@Test
	public void testGettersAndSetters() {
		prova.setBarCode("928001137862");
		assertEquals(prova.getBarCode(), "928001137862");
		prova.setId(901);
		assertEquals(prova.getId(), Integer.valueOf(901));
		prova.setLocation("10-10-10");
		assertEquals(prova.getLocation(), "10-10-10");
		prova.setNote("A very nice product");
		assertEquals(prova.getNote(), "A very nice product");
		prova.setPricePerUnit(1.20);
		assertEquals(prova.getPricePerUnit(), Double.valueOf(1.20));
		prova.setProductDescription("A very nice product, indeed");
		assertEquals(prova.getProductDescription(), "A very nice product, indeed");
		prova.setQuantity(108);
		assertEquals(prova.getQuantity(), Integer.valueOf(108));
		
		}

}