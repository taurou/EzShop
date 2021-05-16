# Unit Testing Documentation template

Authors:

Date:

Version:

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)

# Black Box Unit Tests

```
<Define here criteria, predicates and the combination of predicates for each function of each class.
Define test cases to cover all equivalence classes and boundary conditions.
In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
class and method name that contains the test case>
<JUnit test classes must be in src/test/java/it/polito/ezshop   You find here, and you can use,  class TestEzShops.java that is executed  
to start tests
>
```



### Class *it.polito.ezshop.data.EZShop* - method *checkLuhn*



**Criteria for method *checkLuhn*:**
	

- Validity of the String parameter

- Length of the String

- Validity of cardNo 

  

**Predicates for method *checkLuhn*:**

| Criterion                        | Predicate                 |
| -------------------------------- | ------------------------- |
| Validity of the String parameter | Valid                     |
|                                  | NULL                      |
|                                  | Not Valid, not all digits |
| Length of the String             | [0, 7]                    |
|                                  | [8, 19]                   |
|                                  | [20, maxint)              |
| Validity of cardNo               | true                      |
|                                  | False                     |

**Boundaries for method *checkLuhn***:

| Criterion | Boundary values |
| --------- | --------------- |
|           | 0, 7, 8, 19, 20 |



 **Combination of predicates for method *checkLuhn***

| Validity of cardNo | Length of the String | Validity of the String parameter | Valid/Invalid | Description of the test case                                 | JUnit test case        |
| ------------------ | -------------------- | -------------------------------- | ------------- | ------------------------------------------------------------ | ---------------------- |
| -                  | -                    | NULL                             | Invalid       | T1(NULL) -> error                                            | stringIsNull()         |
| -                  | <8                   | Valid                            | Invalid       | T2("421", false)<br />T2b("", false)<br />T2b2(*7 digits*, false) | cardLenghtLessThan8()  |
| -                  | >19                  | "                                | Invalid       | T3 -> false<br />T3b(*20 digits*, false)                     | cardLenghtMoreThan18() |
| true               | [8,18]               | "                                | Valid         | T4("017235888619510", true)<br />T4b1(*8 digits*, true)<br />T4b2(*19 digits*, true) | cardValid()            |
| false              | "                    | "                                | Invalid       | T5("3316538977", false)<br />T5b1(*8 digits*, false)<br />T5b2(*19 digits*, false) | cardNotValid()         |
| -                  | *                    | not all digits                   | Invalid       | T6("33165f8977b", false)                                     | cardNotDigits()        |

<br></br>


<br></br>

### Class *it.polito.ezshop.model.ProductType* - method *AddQuantity*

**Criteria for method *AddQuantity*:**
	

- Validity of Integer quantity
- Sign of quantity


**Predicates for method *AddQuantity*:**

| Criterion                    | Predicate   |
| ---------------------------- | ----------- |
| Validity of Integer quantity | Valid       |
|                              | NULL        |
| Sign of  quantity            | (0, maxint) |
|                              | {0}         |
|                              | (minint, 0) |



**Boundaries for method *AddQuantity***:

| Criterion | Boundary values |
| --------- | --------------- |
|           | 1, -1           |

 **Combination of predicates for method *AddQuantity***

| Sign of  quantity | Validity of Integer quantity | Valid/Invalid | Description of the test case | JUnit test case     |
| ----------------- | ---------------------------- | ------------- | ---------------------------- | ------------------- |
| -                 | NULL                         | Not Valid     | T1(null; 0)                  | addQuantityNULLTest |
| {0}               | Valid                        | Not Valid     | T2(0; 0)                     | addQuantityZeroTest |
| (0, maxint)       | "                            | Valid         | T3(8, 8)<br />T3b(1; 9)      | addQuantityPosTest  |
| (minint, 0)       | "                            | Valid         | T4(-8, -8)<br />T4b(-1, -9)  | addQuantityNegTest  |



# White Box Unit Tests

### Test cases definition

    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
| --------- | --------------- |
|           |                 |
|           |                 |
|           |                 |

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

| Unit name | Loop rows | Number of iterations | JUnit test case |
| --------- | --------- | -------------------- | --------------- |
|           |           |                      |                 |
|           |           |                      |                 |
|           |           |                      |                 |
