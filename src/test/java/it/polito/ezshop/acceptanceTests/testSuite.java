package it.polito.ezshop.acceptanceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestOrder.class, TestPosition.class, TestCard.class, TestTicketEntry.class, testcheckPosition.class})
public class testSuite {
	
}