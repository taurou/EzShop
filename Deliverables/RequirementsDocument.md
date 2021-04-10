# Requirements Document 

Authors:

Date:

Version:

# Contents

- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
    	+ [Relevant scenarios](#relevant-scenarios)
- [Glossary](#glossary)
- [System design](#system-design)
- [Deployment diagram](#deployment-diagram)

# Essential description

Small shops require a simple application to support the owner or manager. A small shop (ex a food shop) occupies 50-200 square meters, sells 500-2000 different item types, has one or a few cash registers 
EZShop is a software application to:
* manage sales
* manage inventory
* manage customers
* support accounting


# Stakeholders


| Stakeholder name  | Description | 
| ----------------- |:-----------:|
| Shop owner(s)     |   Person(s) owning the shop          |
| Shop manager(s)| Person(s) managing the shop |
| Shop clerks | People working at cash registers |
| Logistic employees | People working in the logistic department |
| IT employee(s) | Person(s) in charge of installing/maintaining IT infrastructure  |
| Electronic Payment system(s)  | IT system(s) (credit card and/or meal vouchers) to process various electronic payments|
| Customer | Whoever buys something from the market |
| Cash register | Cash register hardware integration  |
| Software developer(s)/mainter(s) | Whoever is involved in the development of the app |
| Fidelity program circuit | The fidelity program that the market uses to retain customers(may be common to many markets) | 

# Context Diagram and interfaces

## Context Diagram
\<Define here Context diagram using UML use case diagram>

\<actors are a subset of stakeholders>
Shop manager
Shop clerks
Logistic employees
IT employee(s)
Electronic Payment system(s)
Cash register
Fidelity program system
Customer
## Interfaces
\<describe here each interface in the context diagram>

\<GUIs will be described graphically in a separate document>

| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
| Shop manager    | GUI  | Screen, keyboard, mouse |
| Shop clerks | GUI | Touchscreen display, cash register |
| Logistic employees | GUI |Screen, keyboard, mouse |
| IT employee(s) | GUI | Screen, keyboard, mouse |
| Electronic Payment system(s) | API | Internet cable and POS hardware | 
| Cash register | API | Connectors |
| Fidelity Program system | API | Internet cable |
| Customer | GUI | Mini-display/Bar code scanner (inside market) |

# Stories and personas
\<A Persona is a realistic impersonation of an actor. Define here a few personas and describe in plain text how a persona interacts with the system>

--Customer

A customer enters the supermarket to buy some products. While walking inside te market to take what he needs, he/she can check the price of a product using the bar code scanner(s) placed somewhere (in case the price is not written clearly below the product). After taking all the products he/she wants, the customer goes to the cashier in order to initiate the check-out process. While the clerk scans all the products, the customer can check the price of each single scanned product in the mini-display put on the cash desk. Then, the customer must pay with cash, credit card or food voucher. After payment the check-out is complete.


--Cashier 

A clerk, when working at the cash desk, must login in the application in order to use it. If it is the first login of the day, a cash-opening procedure must be performed (the amounts of money in the cash register is reported in the application to allow automatic counting). Then, he/she waits for customers to show up. When a customer shows up, the clerk starts scanning all products. In front of him, a touchscreen display (connect to a computer) is available, in order to manage the check out process. Various options are present, such as remove items, apply discounts and so on. While scanning products (at the end, in between or at the start) the customer can show the fidelity card and the cashier can scan it in order to link the transaction to the customer. Some additional info are present if the the fidelity card is scanned (such as overall points and not sensible data). After all products have been scanned, the cashier can conclude the checkout and select the payment the customer desires to pay with. After the payment completes, a receipt is printed and the check-out ends. At the of the day or when he/she needs, the clerk must logout from the system. At the end of the day, the cashier must perform a cash-closing procedure. It consists in counting all money inside the cash register and check if the amount is the same the system has calculated throughout the day. In case of inconsistency the shop manager must deal with it.

--Logistic employee




\<Persona is-an-instance-of actor>

\<stories will be formalized later as scenarios in use cases>


# Functional and non functional requirements

## Functional Requirements

\<In the form DO SOMETHING, or VERB NOUN, describe high level capabilities of the system>

\<they match to high level use cases>

| ID        | Description  |
| ------------- |:-------------:| 
| FR1 | Authorize and authenticate cashier |
| | Log in |
| | Log out |
| | Perfrom cash-opening procedure|
| | Perform cash-closing procedure |
| | Update amount of cash in the cash register| 
| FR2 | Check-out of a customer |
| | Start transaction |
| | Scan a product/fidelity card | 
| | Remove scanned product |
| | Apply discount on product |
| | Cancel check-out transaction | 
| | Put transaction on hold |
| | Keep track of scanned products|
| | Compute total cost |
| | Select payment method | 
| | Insert number of voucher |
| | Show fidelity card info (points, name...)|
| | Print receipt |
| | Update inventory (done automatically)*|
| | Update fidelity card points (done automatically) |
| FR3 | Manage return of a product |
| | Delete product from a already carried-out transaction |
| | Update inventory (done automatically)*|
| FR4 | Manage inventory |
| | Add new product |
| | Delete product |
| | Update Product |
| | Add Product quantity |
| | Show list of suppliers | 
| | Show product supplied by supplier |
| | Show list of items (and related info)|
| | Show value (in money) of inventory |
| FR5 | Manage customer (in fidelity program) | 
| | Add new customer |
| | Delete customer |
| | Update customer | 
| | Show points of a customer|
| | Convert points to prizes |




## Non Functional Requirements

\<Describe constraints on functional requirements>

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     | Efficiency  | Allow to close check-out and select payment in less than 10 sec.  | FR2|
|  NFR2     | Usability| An average clerk must be able to fully use the program after 1 hour explanation  | FR1-FR2 |
|  NFR3     | Efficiency | The clerk module of the application must be able to run on low-end computers | FR1  |
|  NFR4     |  Reliability | The application must be able to function without internet/internal connection for long periods of time   | FR2-FR1*-FR4-FR5| 
| NFR6 | Correctness | The application must be able to synch with the inventory without conflicts | |
| NFR7 | Maintanainability | The deployment of the application must be done in no more than 15 working hours | |
| NFR6 | Maintanainbility | All CRUD operations must be possible to be carried out within 1 minute | |
| NFR7 | Security | Personnel must be able to enter just the portion of program it is authorized to|  |
| NFR8 | Security | Sensible data (such as name, surname, birth) must be shown to personell only when those date are useful and needed during the process|  |
| NFR9 | Reliability | The application must perform backups of everything it is important and store it in a secure location (maybe cloud) | |
| NFR10 | Reliability | The application must work even when an inventory update is being carried out and must not create conflicts after update | |
# Use case diagram and use cases


## Use case diagram
\<define here UML Use case diagram UCD summarizing all use cases, and their relationships>

USE CASES:
- Clerk performs login operations
- Clerk performs cash-opening/closing procedure
- Clerk performs checkout for a customer
- Customer checks price ad bar code scanner inside market
- Clerk manages return of a product  
- Logistic employee CRUD product
- Check points of fidelity card at bar code scanner
- Convert points into prizes at information box
- Insert customer into fidelity program
-
-












2) Automatic reorder of product
   -Logistic employee sets an automatic reorder when product is below a threshold
   -Program orders the product
   -The product is not available
   -The payment of an order is not possible due to various cases
3) Manager or logistic clerk want to have an overview of products in the market
   -An overview is displayed
   -Employee clicks on specific product to know details
   -Employee orders some quantities of a product
   -Employee deletes orders
   -Employee adds products
   -Employee adds suppliers
   -Employee adds automatic order for a product
   -Employee updates prices
   -Employee checks pending order
   -Employee checks pending payments
   -Employee wants some data on group of products
4) Employee wants to check fidelity card of a customer
   -Wants to delete it
   -Wants to add new one
   -Wants to check accumulated points
   -Wants to convert points into discount
   -Wants to update customer data
   -Wants to perform data analysis on groups of customers
5) Manager or accountant wants to gather some fiscal informations
   -...Manager wants to...
6) Manager wants to have an overview of sold products during last period
   -...
   -...



\<next describe here each use case in the UCD>
### Use case 1, UC1
| Actors Involved        |  |
| ------------- |:-------------:| 
|  Precondition     | \<Boolean expression, must evaluate to true before the UC can start> |  
|  Post condition     | \<Boolean expression, must evaluate to true after UC is finished> |
|  Nominal Scenario     | \<Textual description of actions executed by the UC> |
|  Variants     | \<other executions, ex in case of errors> |

##### Scenario 1.1 

\<describe here scenarios instances of UC1>

\<a scenario is a sequence of steps that corresponds to a particular execution of one use case>

\<a scenario is a more formal description of a story>

\<only relevant scenarios should be described>

| Scenario 1.1 | |
| ------------- |:-------------:| 
|  Precondition     | \<Boolean expression, must evaluate to true before the scenario can start> |
|  Post condition     | \<Boolean expression, must evaluate to true after scenario is finished> |
| Step#        | Description  |
|  1     |  |  
|  2     |  |
|  ...     |  |

##### Scenario 1.2

##### Scenario 1.x

### Use case 2, UC2
..

### Use case x, UCx
..



# Glossary

-Check-out
-Shop-clerk
-Shop manager
-Logistic employee
-Bar code scanner inside market
-cash-opening/closing procedure
-Fidelity card
-Fidelity points
-CRUD 
-Supplier

\<use UML class diagram to define important terms, or concepts in the domain of the system, and their relationships> 

\<concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design>

\<must be consistent with Context diagram>

# Deployment Diagram 

\<describe here deployment diagram >



----Add portable tablets to update prices ---
---Issue invoice instead of receipt---