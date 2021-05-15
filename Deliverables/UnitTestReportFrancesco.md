# Unit Testing Documentation

Authors:

Date:

Version:

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezshop   You find here, and you can use,  class TestEzShops.java that is executed  
    to start tests
    >

 ### **Class *it.polito.ezshop.data.EZShop* - method *checkBarCodeValidity***

<br></br>

**Criteria for method *checkBarCodeValidity*:**
	

 - barcode is null
 - barcode isBlank()
 - barcode is made of all digits
 - barcode length is correct (12,13 or 14)
 - barcode respect GS1. i.e. Last digit of the barcode is equal to the difference between the nearest (from the sum) equal or higher multiple of ten and the sum itself (sum of all the digits except of the last one) 

<br></br>


**Predicates for method *checkBarCodeValidity*:**

| Criteria | Predicate |
| -------- | --------- |
|   barcode validity  | null |
|| blank |
|| valid , all digits |
|| invalid, not all digits |
|   barcode length is correct (12,13 or 14)       |   yes   |
||no|
|   barcode respect GS1      |     yes      |
||no|
 

<br></br>


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| | |
<br></br>


**Combination of predicates**:


| is null | isBlank() | is made of all digits | length is correct | respect GS1 | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|-------|-------|
|true   |       |       |       |       |valid  |T1(null)--> true   |       |       
|       |true   |       |       |       |valid  |T2("  ")-->true   |       |
|       |       |false  |       |       |valid  |T3("a1234")-->true   |       |
|       |       |true   |false  |       |valid  |T4("1234")-->true   |       |
|       |       |true   |true   |false  |valid  |T5("6291041500214")-->true   |       |
|       |       |true   |true   |true   |invalid|T6("6291041500213")-->false  |       |

<br></br>


<br></br>

# White Box Unit Tests

### Test cases definition
    
    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|--|--|
|||
|||
||||

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---|---|
|||||
|||||
||||||



