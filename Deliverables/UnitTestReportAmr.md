# Unit Testing Documentation

Authors: Alashram, Amr

Date: 16/5/2021

Version: v1

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezshop   You find here, and you can use,  class TestEzShops.java that is executed  
    to start tests
    >

 ### **Class *it.polito.ezshop.data.EZShop* - method *checkPosition***

<br></br>

**Criteria for method *checkPosition*:**
	

 - Position is null
 - Position is not string
 - Position length is correct
 - Pattern is correct


<br></br>


**Predicates for method *checkPosition*:**

| Criteria | Predicate |
| -------- | --------- |
|   Position is null  | null |
|| String |
| Position is string | True |
|| False |
|   Position length is correct      |   < 3 Digits  |
||> 3 Digits|
|| = 3 Digits|
| Pattern correctness | Correct |
|| Incorrect |
|||
 

<br></br>

**Combination of predicates**:


| is null | is string | length is correct | Pattern is correct | Validity | Description of the test case | JUnit test case |
|---------|-----------|-------------------|--------------------|----------|------------------------------|-----------------|
|null     | *         | *                 |  *                 | Invalid  |  T1(null) --> False          |    TC1    |       
| *       | False     | *                 |  *                 | Invalid  |  T2(1-2-3) --> False         |    TC2    |
| *       | *         |   > 3 Digits      |  *                 | Invalid  |  T3("1-2-3-4") --> False     |    TC3    |
| *       | *         |   < 3 Digits      |  *                 | Invalid  |  T4("1-2") --> False         |    TC3    |
| String  | True      |   = 3 Digits      | true               |  Valid   |  T8("1-2-3") --> True        |    TC3    |
| *       | *         | *                 | Incorrect          | Invalid  | T5("-1-2-3")--> False <br /> T6("1-2-3-")--> False <br /> T7("12-3") --> False | TC4  |


<br></br>
