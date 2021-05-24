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

@startuml
class SaleTransaction
class TicketEntry
class ProductType
class EZShop
class BalanceOperation
class Card
class Customer
class EZShopData
class Order
class ReturnSaleTransaction
class User
SaleTransaction --> ProductType
SaleTransaction --> TicketEntry
ReturnSaleTransaction --> TicketEntry
EZShop --> BalanceOperation
EZShop --> Card
EZShop --> Order
EZShop --> Customer
EZShop --> EZShopData
EZShop --> User
EZShop --> ReturnSaleTransaction
EZShop --> SaleTransaction
EZShop --> TicketEntry
EZShop --> ProductType
@enduml


@startuml
'left to right direction
top to bottom direction
class "createUser()"
class "deleteUser()"
class "getAllUsers()"
class "getAllOrders()"
class "defineCustomer()"
class "recordOrderArrival()"
class "createProductType()" 
class "getCustomer()"
class "getAllCustomers()"
class "createCard()"
class "startSaleTransaction()"
class "addProductToSale()"
class "getSaleTransaction()"
class "receiveCashPayment()"
class "receiveCreditCardPayment()"
class "returnCashPayment()"
class "recordBalanceUpdate()" 
class "getCreditsAndDebits()"
class "computeBalance()" 


"deleteUser()" --> "createUser()"
"getAllUsers()" --> "createUser()"
"getUser()" --> "createUser()"
"updateUserRights()" -->  "createUser()"
"login()" --> "createUser()"
"logout()" --> "createUser()"
"updateProduct" --> "createProductType()" 
"deleteProductType()" --> "createProductType()"
"getAllProductTypes()" --> "createProductType()"
"getProductTypeByBarCode()" --> "createProductType()"
"getProductTypesByDescription()"  --> "createProductType()"
"updateQuantity()" --> "createProductType()"
" updatePosition()" -->  "createProductType()"
" issueOrder()" --> "createProductType()"
"payOrderFor()" --> "createProductType()"
"payOrderFor()" -->  "computeBalance()"
"payOrder()" --> "createProductType()"
"modifyCustomer" --> "defineCustomer()"
"deleteCustomer" --> "defineCustomer()"
"attachCardToCustomer()" --> "createCard()"
"modifyPointsOnCard()" --> "createCard()"
"addProductToSale()" --> "createProductType()"
"addProductToSale()" --> "startSaleTransaction()"
"deleteProductToSale()" --> "createProductType()"
"deleteProductToSale()" --> "startSaleTransaction()"
"applyDiscountRateToProduct()" --> "createProductType()"
"applyDiscountRateToSale()" --> "startSaleTransaction()"
"computePointsForSale()" --> "startSaleTransaction()"
"endSaleTransaction()" --> "startSaleTransaction()"
"endSaleTransaction()" -->"startSaleTransaction()"
"deleteSaleTransaction()" --> "startSaleTransaction()"
"startReturnTransaction()" --> "startSaleTransaction()"
"returnProduct()" --> "startReturnTransaction()" 
"returnProduct()" --> "createProductType()"
"endReturnTransaction()" --> "startReturnTransaction()" 
"deleteReturnTransaction()" --> "startReturnTransaction()"
"returnCashPayment()" -->  "startReturnTransaction()" 
"returnCreditCardPayment()" --> "startReturnTransaction()" 
"receiveCreditCardPayment()" --> "startSaleTransaction()"
"receiveCashPayment()" --> "startSaleTransaction()"
@enduml
     
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
|||





# Scenarios




# Coverage of Scenarios and FR


<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR. 
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >




| Scenario ID | Functional Requirements covered | JUnit  Test(s) | 
| ----------- | ------------------------------- | ----------- | 
|  1.1       | FR3.1                              |    createProductTypeIntegrationTest.java |                      
|  1.2         | FR4.2          FR3.4               |    updatePositionIntegrationTest.java         |        
|     1.3        | FR 3.4 FR3.1 |      updateProductIntegrationTest.java        |    
| 2.1         |          FR1.1                    |    CreateUserIntegrationTest.java       |       
|    2.2           |   FR1.4 FR1.1  | DeleteUserIntegrationTest.java | 
| 2.3        |           FR1.4 FR1.2                      |     ModifyCustomerIntegrationTest.java       |            
|  9.1        |           FR8.3                       |   GetCreditsAndDebitsIntegrationTest.java          |             
| ...         |                                 |             |             



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|       NFR5                     |  TestCheckLuhn.java         |
|NFR4|TestCheckBarCode.java|
|NFR6|CreateUserIntegrationTest.java|


