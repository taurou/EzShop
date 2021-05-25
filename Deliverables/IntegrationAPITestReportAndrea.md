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




## Step 1 (?????)
| Classes  | JUnit test classes |
| ---------| --------------------|
| User | TestUser|
| TicketEntry|TestTicketEntry|
| ProductType | TestProductType |
| Position |TestPosition|
| Order |TestOrder|
|Customer |TestCustomer|
|Card |TestCard|
| BalanceOperation|TestBalanceOperation|
| SaleTransaction | SaleTransactionUnitTest |
| EZShop : checkPosition | testCheckPosition |
| EZShop : CheckLuhn |TestCheckLuhn|
| EZShop : checkBarcodeValidity | TestCheckBarcode |

## Step 2
| Classes  | JUnit test classes |
| -------- | -------------------- |
| payOrder                   | PayOrderIntegrationTest                   |
| recordOrderArrival         | RecordOrderArrivalIntegrationTest         |
| getAllOrders               | GetAllOrdersIntegrationTest               |
| defineCustomer             | DefineCustomerIntegrationTest             |
| modifyCustomer             | ModifyCustomerIntegrationTest             |
| deleteCustomer             | DeleteCustomerIntegrationTest             |
| getCustomer                | GetCustomerIntegrationTest                |
| getAllCustomers            | GetAllCustomersIntegrationTest            |
| createCard                 | CreateCardIntegrationTest                 |
| attachCardToCustomer       | AttachCardToCustomerIntegrationTest       |
| modifyPointsOnCard         | ModifyPointsOnCardIntegrationTest         |
| startSaleTransaction       | StartSaleTransactionIntegrationTest       |
| addProductToSale           | AddProductToSaleIntegrationTest           |
| deleteProductFromSale      | DeleteProductFromSaleIntegrationTest      |
| applyDiscountRateToProduct | ApplyDiscountRateToProductIntegrationTest |
| applyDiscountRateToSale    | ApplyDiscountRateToSaleIntegrationTest    |
| computePointsForSale       | ComputePointsForSaleIntegrationTest       |
| endSaleTransaction         | EndSaleTransactionIntegrationTest         |



# Scenarios




# Coverage of Scenarios and FR

<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR. 
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >




| Scenario ID | Functional Requirements covered | JUnit  Test(s) |
| ----------- | ------------------------------- | ----------- |
| 3.1 | FR4.4 | IssueOrderIntegrationTest |
| 3.2 | FR4.4 | PayOrderIntegrationTest |
| 3.3 | FR4.6, FR4.1 | RecordOrderArrivalIntegrationTest |
| 4.1 | FR5.1 | DefineCustomerIntegrationTest |
| 4.2 | FR5.6 | AttachCardToCustomerIntegrationTest |
| 4.3 | FR5.1 | ModifyCustomerIntegrationTest |
| 4.4 | FR5.1 | ModifyCustomerIntegrationTest |



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|       NFR5                     |  TestCheckLuhn.java         |
|NFR4|TestCheckBarCode.java|
|NFR6|CreateUserIntegrationTest.java|

