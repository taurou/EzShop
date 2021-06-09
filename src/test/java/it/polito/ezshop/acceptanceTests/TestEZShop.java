package it.polito.ezshop.acceptanceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import it.polito.ezshop.integrationTests.AddProductToSaleIntegrationTesting;
import it.polito.ezshop.integrationTests.ApplyDiscountRateToProductIntegrationTest;
import it.polito.ezshop.integrationTests.ApplyDiscountRateToSaleIntegrationTest;
import it.polito.ezshop.integrationTests.AttachCardToCustomerIntegrationTest;
import it.polito.ezshop.integrationTests.CheckIfAdminCashManTest;
import it.polito.ezshop.integrationTests.ComputeBalanceIntegrationTest;
import it.polito.ezshop.integrationTests.ComputePointsForSaleIntegrationTest;
import it.polito.ezshop.integrationTests.CreateCardIntegrationTest;
import it.polito.ezshop.integrationTests.CreateUserIntegrationTest;
import it.polito.ezshop.integrationTests.DefineCustomerIntegrationTest;
import it.polito.ezshop.integrationTests.DeleteCustomerIntegrationTest;
import it.polito.ezshop.integrationTests.DeleteProductFromSaleIntegrationTest;
import it.polito.ezshop.integrationTests.DeleteSaleTransactionIntegrationTest;
import it.polito.ezshop.integrationTests.DeleteUserIntegrationTest;
import it.polito.ezshop.integrationTests.EndSaleTransactionIntegrationTest;
import it.polito.ezshop.integrationTests.EndReturnTransactionIntegrationTest;
import it.polito.ezshop.integrationTests.GetAllCustomersIntegrationTest;
import it.polito.ezshop.integrationTests.GetCreditsAndDebitsIntegrationTest;
import it.polito.ezshop.integrationTests.GetCustomerIntegrationTest;
import it.polito.ezshop.integrationTests.GetSaleTransactionIntegrationTest;
import it.polito.ezshop.integrationTests.GetUserIntegrationTest;
import it.polito.ezshop.integrationTests.LoginIntegrationTest;
import it.polito.ezshop.integrationTests.ModifyCustomerIntegrationTest;
import it.polito.ezshop.integrationTests.ModifyPointsOnCardIntegrationTest;
import it.polito.ezshop.integrationTests.PayOrderForIntegrationTest;
import it.polito.ezshop.integrationTests.PayOrderIntegrationTest;
import it.polito.ezshop.integrationTests.ReceiveCashPaymentIntegrationTest;
import it.polito.ezshop.integrationTests.ReceiveCreditCardPaymentIntegrationTest;
import it.polito.ezshop.integrationTests.RecordBalanceUpdateIntegrationTest;
import it.polito.ezshop.integrationTests.RecordOrderArrivalIntegrationTest;
import it.polito.ezshop.integrationTests.ResetIntegrationTest;
import it.polito.ezshop.integrationTests.ReturnCashPaymentIntegrationTest;
import it.polito.ezshop.integrationTests.ReturnCreditCardPaymentIntegrationTest;
import it.polito.ezshop.integrationTests.ReturnProductIntegrationTest;
import it.polito.ezshop.integrationTests.SaleTransactionIntegrationTest;
import it.polito.ezshop.integrationTests.StartReturnTransactionIntegrationTest;
import it.polito.ezshop.integrationTests.StartSaleTransactionIntegrationTest;
import it.polito.ezshop.integrationTests.createProductTypeIntegrationTest;
import it.polito.ezshop.integrationTests.deleteProductTypeIntegrationTest;
import it.polito.ezshop.integrationTests.getAllOrdersIntegrationTest;
import it.polito.ezshop.integrationTests.getAllProductTypesIntegrationTest;
import it.polito.ezshop.integrationTests.getAllUsersIntegrationTest;
import it.polito.ezshop.integrationTests.getProductTypeByBarCodeIntegrationTest;
import it.polito.ezshop.integrationTests.getProductTypeByDescriptionIntegrationTest;
import it.polito.ezshop.integrationTests.issueOrderIntegrationTest;
import it.polito.ezshop.integrationTests.logoutIntegrationTest;
import it.polito.ezshop.integrationTests.updatePositionIntegrationTest;
import it.polito.ezshop.integrationTests.updateProductIntegrationTest;
import it.polito.ezshop.integrationTests.updateQuantityIntegrationTest;
import it.polito.ezshop.integrationTests.updateUserRightIntegrationTest;
import it.polito.ezshop.unitTests.SaleTransactionUnitTest;
import it.polito.ezshop.unitTests.SaleTransactionUnitTest2;
import it.polito.ezshop.unitTests.TestBalanceOperation;
import it.polito.ezshop.unitTests.TestCard;
import it.polito.ezshop.unitTests.TestCheckBarCode;
import it.polito.ezshop.unitTests.TestCheckLuhn;
import it.polito.ezshop.unitTests.TestCustomer;
import it.polito.ezshop.unitTests.TestOrder;
import it.polito.ezshop.unitTests.TestOrderPositionCard;
import it.polito.ezshop.unitTests.TestPosition;
import it.polito.ezshop.unitTests.TestProductType;
import it.polito.ezshop.unitTests.TestReturnSaleTransaction;
import it.polito.ezshop.unitTests.TestTicketEntry;
import it.polito.ezshop.unitTests.TestUser;
import it.polito.ezshop.unitTests.testcheckPosition;
import it.polito.ezshop.integrationTests.IsRFIDvalidIntegrationTest;
import it.polito.ezshop.integrationTests.deleteProductFromSaleRFIDIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({AddProductToSaleIntegrationTesting.class, 
	ApplyDiscountRateToProductIntegrationTest.class, 
	ApplyDiscountRateToSaleIntegrationTest.class, 
	AttachCardToCustomerIntegrationTest.class, 
	CheckIfAdminCashManTest.class, 
	ComputeBalanceIntegrationTest.class, 
	ComputePointsForSaleIntegrationTest.class, 
	CreateCardIntegrationTest.class, 
	createProductTypeIntegrationTest.class, 
	CreateUserIntegrationTest.class, 
	DefineCustomerIntegrationTest.class, 
	DeleteCustomerIntegrationTest.class, 
	DeleteProductFromSaleIntegrationTest.class, 
	deleteProductTypeIntegrationTest.class, 
	DeleteSaleTransactionIntegrationTest.class, 
	DeleteUserIntegrationTest.class, 
	EndReturnTransactionIntegrationTest.class, 
	EndSaleTransactionIntegrationTest.class, 
	GetAllCustomersIntegrationTest.class, 
	getAllOrdersIntegrationTest.class, 
	getAllProductTypesIntegrationTest.class, 
	getAllUsersIntegrationTest.class, 
	GetCreditsAndDebitsIntegrationTest.class, 
	GetCustomerIntegrationTest.class, 
	getProductTypeByBarCodeIntegrationTest.class, 
	getProductTypeByDescriptionIntegrationTest.class, 
	GetSaleTransactionIntegrationTest.class, 
	GetUserIntegrationTest.class, 
	issueOrderIntegrationTest.class, 
	LoginIntegrationTest.class, 
	logoutIntegrationTest.class, 
	ModifyCustomerIntegrationTest.class, 
	ModifyPointsOnCardIntegrationTest.class, 
	PayOrderForIntegrationTest.class, 
	PayOrderIntegrationTest.class, 
	ReceiveCashPaymentIntegrationTest.class, 
	ReceiveCreditCardPaymentIntegrationTest.class, 
	RecordBalanceUpdateIntegrationTest.class, 
	RecordOrderArrivalIntegrationTest.class, 
	ResetIntegrationTest.class, 
	ReturnCashPaymentIntegrationTest.class, 
	ReturnCreditCardPaymentIntegrationTest.class, 
	ReturnProductIntegrationTest.class, 
	SaleTransactionIntegrationTest.class, 
	StartReturnTransactionIntegrationTest.class, 
	StartSaleTransactionIntegrationTest.class, 
	updatePositionIntegrationTest.class, 
	updateProductIntegrationTest.class, 
	updateQuantityIntegrationTest.class, 
	updateUserRightIntegrationTest.class,
	SaleTransactionUnitTest.class, 
	SaleTransactionUnitTest2.class, 
	TestBalanceOperation.class, 
	TestCard.class, 
	TestCheckBarCode.class, 
	TestCheckLuhn.class, 
	testcheckPosition.class, 
	TestCustomer.class, 
	TestOrder.class, 
	TestOrderPositionCard.class, 
	TestPosition.class, 
	TestProductType.class, 
	TestReturnSaleTransaction.class, 
	TestTicketEntry.class, 
	TestUser.class,
	deleteProductFromSaleRFIDIntegrationTest.class,
	IsRFIDvalidIntegrationTest.class
 })
public class TestEZShop {
    
    
}
