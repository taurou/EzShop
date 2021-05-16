package it.polito.ezshop.acceptanceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import it.polito.ezshop.integrationTests.SaleTransactionIntegrationTest;
import it.polito.ezshop.unitTests.SaleTransactionUnitTest;
import it.polito.ezshop.unitTests.TestCheckBarCode;
import it.polito.ezshop.unitTests.TestReturnSaleTransaction;
import it.polito.ezshop.unitTests.TestTicketEntry;
import it.polito.ezshop.unitTests.TestUser;


@RunWith(Suite.class)
@Suite.SuiteClasses({SaleTransactionUnitTest.class, TestCheckBarCode.class, TestReturnSaleTransaction.class, TestTicketEntry.class, TestUser.class, SaleTransactionIntegrationTest.class})
public class TestEZShop {
    
    
}
