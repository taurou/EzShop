package it.polito.ezshop.acceptanceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import it.polito.ezshop.integrationTests.CreateUserIntegrationTest;
import it.polito.ezshop.integrationTests.DeleteUserIntegrationTest;
import it.polito.ezshop.integrationTests.LoginIntegrationTest;
import it.polito.ezshop.integrationTests.SaleTransactionIntegrationTest;
import it.polito.ezshop.unitTests.SaleTransactionUnitTest;
import it.polito.ezshop.unitTests.TestBalanceOperation;
import it.polito.ezshop.unitTests.TestCard;
import it.polito.ezshop.unitTests.TestCheckBarCode;
import it.polito.ezshop.unitTests.TestCheckLuhn;
import it.polito.ezshop.unitTests.TestCustomer;
import it.polito.ezshop.unitTests.TestOrder;
import it.polito.ezshop.unitTests.TestPosition;
import it.polito.ezshop.unitTests.TestProductType;
import it.polito.ezshop.unitTests.TestReturnSaleTransaction;
import it.polito.ezshop.unitTests.TestTicketEntry;
import it.polito.ezshop.unitTests.TestUser;
import it.polito.ezshop.unitTests.testcheckPosition;
import it.polito.ezshop.unitTests.TestOrderPositionCard;

@RunWith(Suite.class)
@Suite.SuiteClasses({CreateUserIntegrationTest.class, DeleteUserIntegrationTest.class, LoginIntegrationTest.class, TestCheckLuhn.class, TestCustomer.class, TestBalanceOperation.class, TestProductType.class, TestOrder.class, TestPosition.class, TestCard.class, TestTicketEntry.class, testcheckPosition.class, SaleTransactionUnitTest.class, TestCheckBarCode.class, TestReturnSaleTransaction.class, TestTicketEntry.class, TestUser.class, SaleTransactionIntegrationTest.class, TestOrderPositionCard.class})

public class TestEZShop {
    
    
}
