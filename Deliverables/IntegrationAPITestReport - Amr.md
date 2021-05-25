# Integration and API Test Documentation

Authors:

Date:

Version:

# Contents

- [Dependency graph](#dependency graph)

- [Integration approach](#integration)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph 



<img src="../Images/DependencyGraph.JPG" width="850" height="400">
# Integration approach

    <Write here the integration sequence you adopted, in general terms (top down, bottom up, mixed) and as sequence
    (ex: step1: class A, step 2: class A+B, step 3: class A+B+C, etc)> 
    <Some steps may  correspond to unit testing (ex step1 in ex above), presented in other document UnitTestReport.md>
    <One step will  correspond to API testing>
    
    step1: Class ReturnSaleTransaction + Class SaleTransaction
    step2: CreateUser
    step3: login
    step4: DeleteUser



#  Tests

   <define below a table for each integration step. For each integration step report the group of classes under test, and the names of
     JUnit test cases applied to them> JUnit test classes should be here src/test/java/it/polito/ezshop




## Step 1
| Classes  | JUnit test classes |
| ---------| --------------------|
| User | TestUser|
| TicketEntry|TestTicketEntry|
| ProductType | |
| Position ||
| Order ||
|Customer ||
|Card || 
| BalanceOperation||
| SaleTransaction | SaleTransactionUnitTest |
| EZShop : checkPosition | |
| EZShop : CheckLuhn ||
| EZShop : checkBarcodeValidity | |

## Step 2
| Classes  | JUnit test classes |
| -------- | -------------------- |
| SaleTransaction | SaleTransactionIntegrationTest|
|getAllUsers|getAllUsersIntegrationTest|
|getUser|getUserIntegrationTest|
|UpdateUserRights|UpdateUserRightsIntegrationTest|
|logout|logoutIntegrationTest|
|login|loginIntegrationTest|
|createProductType|createProductTypeIntegrationTest|
|updateProduct|updateProductIntegrationTest|
|deleteProductType|deleteProductTypeIntegrationTest|
|getAllProductType|getAllProductTypeIntegrationTest|
|getProductTypeByBarcode|getProductTypeByBarcodeIntegrationTest|
|getProductTypeByDescription|getProductTypeByDescriptionIntegrationTest|
|updateQuantity|updateQuantityIntegrationTest|
|updatePosition|updatePositionIntegrationTest|
|issueOrder|issueOrderIntegrationTest|
|payOrderFor|payOrderForIntegrationTest|
|getAllOrders|getAllOrdersIntegrationTest|


# Scenarios




# Coverage of Scenarios and FR


<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR. 
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >




| Scenario ID | Functional Requirements covered | JUnit  Test(s) | 
| ----------- | ------------------------------- | ----------- | 
|  1.1        | FR3.1            |    createProductTypeIntegrationTest.java      |                      
|  1.2        | FR4.2  FR3.4     |    updatePositionIntegrationTest.java         |        
|     1.3     | FR 3.4 FR3.1     |      updateProductIntegrationTest.java        |    
| 2.1         | FR1.1            |    CreateUserIntegrationTest.java             |       
|    2.2      | FR1.4  FR1.1     |      DeleteUserIntegrationTest.java           | 
| 2.3         | FR1.4  FR1.2     |     ModifyCustomerIntegrationTest.java        |  
|    7.1      |FR4, FR6, FR7     |    startSaleTransactionIntegrationTest.java , addProductToSaleIntegrationTest.java, endSaleTransactionIntegrationTest.java, receiveCashPaymentIntegrationTest.java, updateQuantityIntegrationTest.java       |
|   7.2       |FR4, FR5, FR6, FR7|    startSaleTransactionIntegrationTest.java , addProductToSaleIntegrationTest.java, endSaleTransactionIntegrationTest.java, receiveCreditCardPaymentIntegrationTest.java, computePointsForSaleIntegrationTest.java, modifyPointsOnCardIntegrationTest.java, updateQuantityIntegrationTest.java       |
|  8.1        | FR5              |        getCustomerIntegrationTest.java        |
|  9.1        | FR8.3            |   GetCreditsAndDebitsIntegrationTest.java     |             
|  10.1       | FR5              |     defineCustomerIntegrationTest.java, createCardIntegrationTest.java,attachCardToCustomerIntegratiionTest.java         |
|  10.2       | FR5              |     defineCustomerIntegrationTest.java        |
|             |                  |                                               |             



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|       NFR5                     |  TestCheckLuhn.java         |
|NFR4|TestCheckBarCode.java|
|NFR6|CreateUserIntegrationTest.java|


