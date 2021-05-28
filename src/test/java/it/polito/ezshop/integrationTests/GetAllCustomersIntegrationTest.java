package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import java.util.List;

import it.polito.ezshop.data.Customer;
import it.polito.ezshop.exceptions.InvalidCustomerNameException;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;

public class GetAllCustomersIntegrationTest {

	it.polito.ezshop.data.EZShop shop;	
	
	@Before
	public void setUp() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		shop = new it.polito.ezshop.data.EZShop(1);
		shop.createUser("manager3", "manager3pass", "ShopManager");
		shop.login("manager3", "manager3pass");

			}

	@Test
	public void listTest() throws UnauthorizedException, InvalidCustomerNameException {
		shop.defineCustomer("Mario Rossi");
		Integer custid = shop.defineCustomer("Gino Petit");
		shop.defineCustomer("Antonella La Barra");
		shop.defineCustomer("Paolo Colombina");
		List<Customer> listaClienti = shop.getAllCustomers();
		assertEquals(listaClienti.get(0).getCustomerName(), "Mario Rossi");
		assertEquals(listaClienti.get(1).getId(), custid);
		assertEquals(listaClienti.get(2).getCustomerName(), "Antonella La Barra");
		assertEquals(listaClienti.get(3).getCustomerName(), "Paolo Colombina");

	}

}
