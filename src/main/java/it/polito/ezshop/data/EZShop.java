package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.BalanceOperation;
import it.polito.ezshop.model.Card;
import it.polito.ezshop.model.EZShopData;
import it.polito.ezshop.model.Position;
import it.polito.ezshop.model.ReturnSaleTransaction;
import it.polito.ezshop.model.SaleTransaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
public class EZShop implements EZShopInterface {

	EZShopData data;

	public EZShop() {
		super();
		loadData();

	}

	public boolean loadData() {

		try {
			FileInputStream fis = new FileInputStream("EZShopData.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			data = (EZShopData) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException fnf) {
			data = new EZShopData();
			data.loggedInUser = null;
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		} catch (ClassNotFoundException a) {
			System.out.println("Class not found");
			a.printStackTrace();
			return false;
		}

		return false;
	}

	public boolean saveData() {

		if (this.data == null) {
			return false;
		}

		try {
			FileOutputStream fos = new FileOutputStream("EZShopData.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.data);
			oos.close();
			fos.close();
			System.out.printf("Serialized data is saved");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return true;
	}
	//48 = 0

	private boolean checkBarcodeValidity(String barcode) {
		if(barcode == null || barcode.isBlank() || !barcode.chars().allMatch(Character::isDigit)) return false;
		int l = barcode.length();
		if(l != 12 && l != 13 && l!=14) return false;
		Integer sum=0, nextTen=10;
		
		char[] barcodeChar = barcode.toCharArray();
        for(int i=0; i< l; i++) {
        	sum+= i%2 == 0 ? (barcodeChar[i]-48)*3 : (barcodeChar[i]-48);
        }
        while(nextTen < sum) {
        	nextTen+=10;
        }
        
        if(nextTen-sum == barcodeChar[l-1]) return true;

		return false;
	}
	
	@Override
	public void reset() {
       
	}

	@Override
	public Integer createUser(String username, String password, String role)
			throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
		if (username == null || username.isBlank())
			throw new InvalidUsernameException();
		if (password == null || password.isBlank())
			throw new InvalidPasswordException();
		if (role.compareTo("Administrator") != 0 && role.compareTo("Cashier") != 0
				&& role.compareTo("ShopManager") != 0)
			throw new InvalidRoleException();
		if (data.users.containsKey(username))
			return -1;
		data.users.put(username, new it.polito.ezshop.model.User(data.userIDs, username, password, role));
		data.idToUsername.put(data.userIDs, username);
		saveData();
		return data.userIDs++;
	}

	@Override
	public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
		if (data.loggedInUser == null || !data.loggedInUser.isAdmin())
			throw new UnauthorizedException();
		if (id < 1 || id == null)
			throw new InvalidUserIdException();
		if (!data.idToUsername.containsKey(id))
			throw new InvalidUserIdException();

		if (data.users.remove(data.idToUsername.get(id)) == null)
			return false;
		if (data.idToUsername.remove(id) == null)
			return false;
		
		return saveData();
	}

	@Override
	public List<User> getAllUsers() throws UnauthorizedException {
		if (data.loggedInUser == null || !data.loggedInUser.isAdmin())
			throw new UnauthorizedException();

		List<User> list = new LinkedList<User>();
		if (data.users.size() == 0)
			return list;
		list.addAll(data.users.values());

		return list;
	}

	@Override
	public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
		if (data.loggedInUser == null || !data.loggedInUser.isAdmin())
			throw new UnauthorizedException();
		if (id < 1 || id == null)
			throw new InvalidUserIdException();

		return data.users.get(data.idToUsername.get(id));
	}

	@Override
	public boolean updateUserRights(Integer id, String role)
			throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
		if (data.loggedInUser == null || !data.loggedInUser.isAdmin())
			throw new UnauthorizedException();
		if (id < 1 || id == null)
			throw new InvalidUserIdException();
		if (role.compareTo("Administrator") != 0 && role.compareTo("Cashier") != 0
				&& role.compareTo("ShopManager") != 0)
			throw new InvalidRoleException();

		if (data.users.get(data.idToUsername.get(id)) == null)
			return false;
		data.users.get(data.idToUsername.get(id)).setRole(role);
		
		return saveData();
	}

	@Override
	public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
		if (username == null || username.isBlank())
			throw new InvalidUsernameException();
		if (password == null || password.isBlank())
			throw new InvalidPasswordException();
		data.loggedInUser = data.users.get(username);
		return data.loggedInUser;
	}

	@Override
	public boolean logout() {
		if (data.loggedInUser == null)
			return false;
		data.loggedInUser = null;

		return true;
	}

	@Override
	public Integer createProductType(String description, String productCode, double pricePerUnit, String note)
			throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
			UnauthorizedException {

		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		if (description == null || description.isBlank())
			throw new InvalidProductDescriptionException();
		if (pricePerUnit <= 0)
			throw new InvalidPricePerUnitException();
		if(checkBarcodeValidity(productCode) ) throw new InvalidProductCodeException();

		Integer productId = Integer.valueOf(productCode);
		if (note == null)
			note = "";
		if (data.productTypes.containsKey(productId))
			return -1;
		data.productTypes.put(productId,
				new it.polito.ezshop.model.ProductType(note, productCode, pricePerUnit, productId, description));
		saveData();
		return productId;
	}

	@Override
	public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
			throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
			InvalidPricePerUnitException, UnauthorizedException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		if (newPrice <= 0)
			throw new InvalidPricePerUnitException();
		if (newDescription == null || newDescription.isBlank())
			throw new InvalidProductDescriptionException();
		if (id <= 0 || id == null)
			throw new InvalidProductIdException();
		if(checkBarcodeValidity(newCode) ) throw new InvalidProductCodeException();

		Integer productId = Integer.valueOf(newCode);
		if (!data.productTypes.containsKey(id))
			return false;
		if (data.productTypes.containsKey(productId))
			return false;
		it.polito.ezshop.model.ProductType tmp;
		tmp = data.productTypes.get(id);
		tmp = data.productTypes.get(id);
		tmp.setProductDescription(newDescription);
		tmp.setPricePerUnit(newPrice);
		tmp.setNote(newNote);
		tmp.setBarCode(newCode);

		data.productTypes.remove(id);
		data.productTypes.put(productId, tmp);
		
		return saveData();
	}

	@Override
	public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		// check id
		if (data.productTypes.remove(id) == null)
			return false;
		
		return saveData();
	}

	@Override
	public List<ProductType> getAllProductTypes() throws UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		LinkedList<ProductType> list = new LinkedList<ProductType>();
		if (data.productTypes.size() == 0)
			return list;
		list.addAll(data.productTypes.values());
		return list;
	}

	@Override
	public ProductType getProductTypeByBarCode(String barCode)
			throws InvalidProductCodeException, UnauthorizedException {

		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		if(checkBarcodeValidity(barCode) ) throw new InvalidProductCodeException();
		Integer productId = Integer.valueOf(barCode);

		return data.productTypes.get(productId);
	}

	@Override
	public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();

		// does collector create an empty list?
		return data.productTypes.values().stream().filter(x -> x.getProductDescription().contains(description))
				.collect(Collectors.toList());

	}

	@Override
	public boolean updateQuantity(Integer productId, int toBeAdded)
			throws InvalidProductIdException, UnauthorizedException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		it.polito.ezshop.model.ProductType p = data.productTypes.get(productId);
		if (p == null || p.getLocation() == null)
			return false;
		if (toBeAdded < 0 && p.getQuantity() + toBeAdded < 0)
			return false;

		if (p.addQuantity(toBeAdded) >= 0) {
			
			return saveData();
		}
		return false; 
	}

	@Override
	public boolean updatePosition(Integer productId, String newPos)
			throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		// check position format
		if(checkBarcodeValidity(String.valueOf(productId)) ) throw new InvalidProductIdException();
		if (data.positions.containsKey(newPos))
			return false;
		if (!data.productTypes.containsKey(productId))
			return false;
		String oldPos = data.productTypes.get(productId).getLocation();
		if (oldPos != null)
			data.positions.remove(oldPos);
		if (newPos == null || newPos.isBlank())
			data.productTypes.get(productId).setLocation(null);
		else
			data.productTypes.get(productId).setLocation(newPos);
		Position p = new Position(newPos);
		data.positions.put(newPos, p);
		p.setProduct(data.productTypes.get(productId));
		
		return saveData();
	}

	@Override
	public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException,
			InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		if(checkBarcodeValidity(productCode) ) throw new InvalidProductCodeException();
		if (quantity <= 0)
			throw new InvalidQuantityException();
		if (pricePerUnit <= 0)
			throw new InvalidPricePerUnitException();
		it.polito.ezshop.model.ProductType p = data.productTypes.get(Integer.valueOf(productCode));
		if (p == null)
			return -1;
		data.orders.put(data.orderIDs,
				new it.polito.ezshop.model.Order(p, pricePerUnit, quantity, data.orderIDs, "ISSUED"));
		saveData();
		return data.orderIDs++;
	}

	@Override
	public Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
			throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
			UnauthorizedException {
		if(checkBarcodeValidity(productCode) ) throw new InvalidProductCodeException();
		if (quantity <= 0)
			throw new InvalidQuantityException();
		if (pricePerUnit <= 0)
			throw new InvalidPricePerUnitException();
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();

		it.polito.ezshop.model.ProductType p = data.productTypes.get(Integer.valueOf(productCode));
		if (p == null || computeBalance() < pricePerUnit * quantity)
			return -1;
		data.orders.put(data.orderIDs,
				new it.polito.ezshop.model.Order(p, pricePerUnit, quantity, data.orderIDs, "PAYED"));
		data.balanceOperations.put(data.balanceOperationIDs, new BalanceOperation(data.balanceOperationIDs++, LocalDate.now(),-pricePerUnit * quantity, "ORDER"));
        data.orders.get(data.orderIDs).setBalanceId(data.balanceOperationIDs);
		return data.orderIDs++;
	}

	@Override
	public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		if (orderId <= 0)
			throw new InvalidOrderIdException();
		it.polito.ezshop.model.Order o;
		o = data.orders.get(orderId);
		if (o == null || o.getStatus().compareTo("ISSUED") != 0)
			return false;
		o.setStatus("PAYED");
		
		o.setBalanceId(data.balanceOperationIDs);
		data.balanceOperations.put(data.balanceOperationIDs, new BalanceOperation(data.balanceOperationIDs++, LocalDate.now(),-o.getPricePerUnit() * o.getQuantity(), "ORDER"));
		saveData();
		
		return true;
	}

	@Override
	public boolean recordOrderArrival(Integer orderId)
			throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		if (orderId <= 0)
			throw new InvalidOrderIdException();
		it.polito.ezshop.model.Order o = data.orders.get(orderId);
		if (o == null)
			return false;
		it.polito.ezshop.model.ProductType p = data.productTypes.get(Integer.valueOf(o.getProductCode()));
		if (p.getLocation() == null)
			throw new InvalidLocationException();
		if (o.getStatus().compareTo("COMPLETED") == 0)
			return true;
		if (o.getStatus().compareTo("PAYED") != 0)
			return false;

		o.setStatus("COMPLETED");
		p.addQuantity(o.getQuantity());
		
		return saveData();
	}

	@Override
	public List<Order> getAllOrders() throws UnauthorizedException {
		if (data.loggedInUser == null
				|| (!data.loggedInUser.isAdmin() && data.loggedInUser.getRole().compareTo("ShopManager") == 0))
			throw new UnauthorizedException();
		LinkedList<Order> l = new LinkedList<>();
		l.addAll(data.orders.values());
		return l;
	}

	@Override
	public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (customerName == null || customerName.isBlank())
			throw new InvalidCustomerNameException();

		if (data.customers.size() != 0 && data.customers.values().stream()
				.filter(c -> c.getCustomerName().compareTo(customerName) == 0).count() != 0)
			return -1;
		data.customers.put(data.customerIDs, new it.polito.ezshop.model.Customer(customerName, data.customerIDs));
		saveData();
		return data.customerIDs++;
	}

	@Override
	////OPEN ISSUEEEEE//
	public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
			throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException,
			UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (newCustomerName == null || newCustomerName.isBlank())
			throw new InvalidCustomerNameException();
		if (id == null || id <= 0)
			throw new InvalidCustomerIdException();
		it.polito.ezshop.model.Customer c = data.customers.get(id);
		if (c == null)
			return false;
		if (newCustomerCard == null || newCustomerCard.isBlank() || newCustomerCard.length() != 10 || !newCustomerCard.chars().allMatch(Character::isDigit)
				|| !data.cards.containsKey(Integer.valueOf(newCustomerCard)))
			throw new InvalidCustomerCardException();
		it.polito.ezshop.model.Card card = data.cards.get(Integer.valueOf(newCustomerCard));
		if (card.getCustomer() != null)
			return false;
		c.setCustomerCard(newCustomerCard);
		card.setCustomer(c);
		
		return saveData();
	}

	@Override
	public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (id == null || id <= 0)
			throw new InvalidCustomerIdException();
		it.polito.ezshop.model.Customer c = data.customers.get(id);
		if(c == null) return false;
		data.customers.remove(id);
		data.cards.get(Integer.valueOf(c.getCustomerCard())).setCustomer(null);
		
		return saveData();
	}

	@Override
	public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (id == null || id <= 0) throw new InvalidCustomerIdException();
			return data.customers.get(id);
		
	}

	@Override
	public List<Customer> getAllCustomers() throws UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		LinkedList<Customer> l = new LinkedList<>();
		l.addAll(data.customers.values());
		return l;
	}

	@Override
	public String createCard() throws UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();

		data.cards.put(data.cardIDs, new it.polito.ezshop.model.Card(data.cardIDs));
		Integer c = data.cardIDs++;
		saveData();
		return c.toString();
	}

	@Override
	public boolean attachCardToCustomer(String customerCard, Integer customerId)
			throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (customerId == null || customerId <= 0) throw new InvalidCustomerIdException();
		if (customerCard == null || customerCard.isBlank() || !customerCard.chars().allMatch(Character::isDigit) ) throw new InvalidCustomerCardException();
		
		it.polito.ezshop.model.Customer c = data.customers.get(customerId);
		it.polito.ezshop.model.Card card = data.cards.get(Integer.valueOf(customerCard));
		if(c == null || card==null || card.getCustomer() != null) return false;
		
		c.setCustomerCard(customerCard);
		card.setCustomer(c);
		
		return saveData();
	}

	@Override
	public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)
			throws InvalidCustomerCardException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (customerCard == null || customerCard.isBlank() || !customerCard.chars().allMatch(Character::isDigit) ) throw new InvalidCustomerCardException();
		it.polito.ezshop.model.Card card = data.cards.get(Integer.valueOf(customerCard));
		if( card==null || card.getCustomer() == null || card.getPoints()+pointsToBeAdded<0) return false;
		
		card.addPoints(pointsToBeAdded);
		
		return saveData();
	}

	@Override
	public Integer startSaleTransaction() throws UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		data.saleTransactions.put(data.saleTransactionIDs, new SaleTransaction(data.saleTransactionIDs));
		return data.saleTransactionIDs++;
	}

	@Override
	public boolean addProductToSale(Integer transactionId, String productCode, int amount)
			throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
			UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if( amount < 0) throw new InvalidQuantityException();
		if(checkBarcodeValidity(productCode) ) throw new InvalidProductCodeException();
		if (transactionId == null || transactionId <=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.ProductType p = data.productTypes.get(Integer.valueOf(productCode));
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(transactionId);
		if (p == null || p.getQuantity()<amount || t == null || t.getStatus().compareTo("OPEN")!=0) return false;
		t.addProduct(p, amount);
		
		
		return true;
	}

	@Override
	public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
			throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
			UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if( amount < 0) throw new InvalidQuantityException();
		if(checkBarcodeValidity(productCode) ) throw new InvalidProductCodeException();
		if (transactionId == null || transactionId <=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.ProductType p = data.productTypes.get(Integer.valueOf(productCode));
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(transactionId);
		if (p == null || p.getQuantity()<amount || t == null || t.getStatus().compareTo("OPEN")!=0) return false;
		t.addProduct(p, -amount);
		
		
		return true;
	}

	@Override
	public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
			throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
			UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if( discountRate < 0 || discountRate >1.00) throw new InvalidDiscountRateException();
		if(checkBarcodeValidity(productCode) ) throw new InvalidProductCodeException();
		if (transactionId == null || transactionId <=0) throw new InvalidTransactionIdException();
		
		it.polito.ezshop.model.ProductType p = data.productTypes.get(Integer.valueOf(productCode));
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(transactionId);
		if (p == null ||  t == null || t.getStatus().compareTo("OPEN")!=0) return false;
		
		//t.products.get(productCode).setDiscountRate(discountRate);
		t.applyProductDiscount(productCode);
		return true;
	}

	@Override
	public boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
			throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if( discountRate < 0 || discountRate >1.00) throw new InvalidDiscountRateException();
		if (transactionId == null || transactionId <=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(transactionId);
		if ( t == null || t.getStatus().compareTo("PAYED")==0) return false;
		t.setDiscountRate(discountRate);
		return true;
	}

	@Override
	public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (transactionId == null || transactionId <=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(transactionId);
		if( t == null ) return -1;
		return (int) Math.floor(t.getPrice());
	}

	@Override
	public boolean endSaleTransaction(Integer transactionId)
			throws InvalidTransactionIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (transactionId == null || transactionId <=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(transactionId);
		if( t == null  || t.getStatus().compareTo("CLOSED")==0 || t.getStatus().compareTo("PAYED")==0) return false;
		t.setStatus("CLOSED");
		
		
		return saveData();
	}

	@Override
	public boolean deleteSaleTransaction(Integer saleNumber)
			throws InvalidTransactionIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (saleNumber == null || saleNumber <=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(saleNumber);
		if( t == null  || t.getStatus().compareTo("PAYED")==0) return false;
		data.saleTransactions.remove(saleNumber);
		t.entries.forEach( x -> data.productTypes.get(Integer.valueOf(x.getBarCode())).addQuantity(x.getAmount()));
		
		return saveData();
	}

	@Override
	public SaleTransaction getSaleTransaction(Integer transactionId)
			throws InvalidTransactionIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (transactionId == null || transactionId <=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(transactionId);
		if( t == null  || t.getStatus().compareTo("CLOSED")!=0) return null;
		
		return t;
	}

	@Override
	public Integer startReturnTransaction(Integer saleNumber)
			throws /* InvalidTicketNumberException, */InvalidTransactionIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (saleNumber == null || saleNumber <=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(saleNumber);
		if( t == null  || t.getStatus().compareTo("PAYED")!=0) return -1;
		data.returnSaleTransactions.put(data.returnTransactionIDs, new ReturnSaleTransaction(data.returnTransactionIDs, t));
		return data.returnTransactionIDs++;
	}

	@Override
	public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException,
			InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (returnId == null || returnId <=0) throw new InvalidTransactionIdException();
		if(checkBarcodeValidity(productCode) ) throw new InvalidProductCodeException();
		if (amount <=0) throw new InvalidQuantityException();
		it.polito.ezshop.model.ProductType p = data.productTypes.get(Integer.valueOf(productCode));
		it.polito.ezshop.model.ReturnSaleTransaction rt = data.returnSaleTransactions.get(returnId);
		if(rt == null ) return false;
		it.polito.ezshop.data.TicketEntry te = rt.getReturnOfSaleTransaction().getEntries().stream().filter( x -> x.getBarCode().compareTo(productCode)==0).findFirst().orElse(null);
		if (p == null || te == null || te.getAmount() < amount) return false;
		
		rt.addProduct(p, amount);
		
		return true;
	}

	@Override
	public boolean endReturnTransaction(Integer returnId, boolean commit)
			throws InvalidTransactionIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (returnId == null || returnId <=0) throw new InvalidTransactionIdException();
		ReturnSaleTransaction rt = data.returnSaleTransactions.get(returnId);
		if (rt.isCommitted() == true) return false;
		if(commit) {
		rt.setCommitted(true);
		rt.entries.forEach( x -> rt.getReturnOfSaleTransaction().addProduct(data.productTypes.get(Integer.valueOf(x.getBarCode())), -x.getAmount()));
		
		
		}else {
			data.returnSaleTransactions.remove(returnId);
			
		}
		
		return saveData();
	}

	@Override
	public boolean deleteReturnTransaction(Integer returnId)
			throws InvalidTransactionIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (returnId == null || returnId <=0) throw new InvalidTransactionIdException();
		ReturnSaleTransaction rt = data.returnSaleTransactions.get(returnId);
		if(rt == null || rt.getStatus().compareTo("PAYED")==0) return false;
		rt.entries.forEach( x -> rt.getReturnOfSaleTransaction().addProduct(data.productTypes.get(Integer.valueOf(x.getBarCode())), x.getAmount()));
		
		
		return saveData();
	}

	@Override
	public double receiveCashPayment(Integer ticketNumber, double cash)
			throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if(cash <= 0 ) throw new InvalidPaymentException();
		if(ticketNumber == null || ticketNumber<=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(ticketNumber);
		if(t == null || t.getPrice() > cash) return -1;
		t.setStatus("PAYED");
		
		data.balanceOperations.put(data.balanceOperationIDs, new BalanceOperation(data.balanceOperationIDs++, LocalDate.now(), t.getPrice(), "SALE"));
		saveData();
		return cash-t.getPrice(); 
	}

	@Override
	public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard)
			throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if(/*credit card not valid*/ creditCard == null || creditCard.isBlank() ||  false/*check luh alg*/) throw new InvalidCreditCardException();
		if(ticketNumber == null || ticketNumber<=0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.SaleTransaction t = data.saleTransactions.get(ticketNumber);
		if(t == null || t.getPrice() > 10000 /*check amount on credit card*/) return false;
		t.setStatus("PAYED");
		data.balanceOperations.put(data.balanceOperationIDs, new BalanceOperation(data.balanceOperationIDs++, LocalDate.now(), t.getPrice(), "SALE"));
		saveData();
		return true;
	}

	@Override
	public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if(returnId == null || returnId <= 0) throw new InvalidTransactionIdException();
		it.polito.ezshop.model.ReturnSaleTransaction rt = data.returnSaleTransactions.get(returnId);
		if (rt == null || !rt.isCommitted()) return -1;
		rt.setStatus("PAYED");
		data.balanceOperations.put(data.balanceOperationIDs, new BalanceOperation(data.balanceOperationIDs++, LocalDate.now(), rt.getPrice(), "RETURN"));
		saveData();
		return rt.getPrice();
	}

	@Override
	public double returnCreditCardPayment(Integer returnId, String creditCard)
			throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if(returnId == null || returnId <= 0) throw new InvalidTransactionIdException();
		if(/*credit card not valid*/ creditCard == null || creditCard.isBlank() ||  false/*check luh alg*/) throw new InvalidCreditCardException();
		it.polito.ezshop.model.ReturnSaleTransaction rt = data.returnSaleTransactions.get(returnId);
		if (rt == null || !rt.isCommitted()) return -1;
		rt.setStatus("PAYED");
		data.balanceOperations.put(data.balanceOperationIDs, new BalanceOperation(data.balanceOperationIDs++, LocalDate.now(), rt.getPrice(), "RETURN"));
		saveData();
		return rt.getPrice();
	}

	@Override
	public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0				
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if(computeBalance()+toBeAdded < 0) return false;
		data.balanceOperations.put(data.balanceOperationIDs, new BalanceOperation(data.balanceOperationIDs++, LocalDate.now(), toBeAdded, toBeAdded >= 0 ? "CREDIT" : "DEBIT"));
		saveData();
		return true;
	}

	@Override
	public List<it.polito.ezshop.data.BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
			LinkedList<it.polito.ezshop.data.BalanceOperation> l = new LinkedList<>();
		
		if(data.balanceOperations.size() == 0) return l;
		if( from == null && to == null) {
			l.addAll(data.balanceOperations.values());
			return l;
		}
		if( from == null ) {
			data.balanceOperations.values().stream().filter( x -> x.getDate().compareTo(to)<=0).collect(Collectors.toList());
		}
		if( to == null) {
			data.balanceOperations.values().stream().filter( x -> x.getDate().compareTo(from)>=0).collect(Collectors.toList());

		}
		
		data.balanceOperations.values().stream().filter( x -> x.getDate().compareTo(to)<=0 && x.getDate().compareTo(from)>=0).collect(Collectors.toList());
		
		
		
		
		return null;
	}

	@Override
	public double computeBalance() throws UnauthorizedException {
		if (data.loggedInUser.getRole().compareTo("Administrator") != 0
				&& data.loggedInUser.getRole().compareTo("Cashier") != 0
				&& data.loggedInUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		data.balanceOperations.values().stream().mapToDouble( x -> x.getMoney()).sum();
		if(data.balanceOperations.size()!=0) {
		return data.balanceOperations.values().stream().mapToDouble( x -> x.getMoney()).sum();}
		return 0;
	}
}
