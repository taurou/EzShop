package it.polito.ezshop.acceptanceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({SaleTransactionTest.class, TestCheckBarCode.class, TestReturnSaleTransaction.class, TestTicketEntry.class, TestUser.class})
public class TestEZShop {
    
    
}
