# Design assessment


```
<The goal of this document is to analyse the structure of your project, compare it with the design delivered
on April 30, discuss whether the design could be improved>
```

# Levelized structure map
```
<Applying Structure 101 to your project, version to be delivered on june 4, produce the Levelized structure map,
with all elements explosed, all dependencies, NO tangles; and report it here as a picture>
```

![LSM](../Images/DesignAssessmentImgs/LSM.png)

# Structural over complexity chart

```
<Applying Structure 101 to your project, version to be delivered on june 4, produce the structural over complexity chart; and report it here as a picture>
```

![structural-over-complexity](../Images/DesignAssessmentImgs/structural-over-complexity.png)

# Size metrics

```
<Report here the metrics about the size of your project, collected using Structure 101>
```



| Metric                                    | Measure |
| ----------------------------------------- | ------- |
| Packages                                  | 6       |
| Classes (outer)                           | 40      |
| Classes (all)                             | 40      |
| NI (number of bytecode instructions)      | 5088    |
| LOC (non comment non blank lines of code) | 2188    |



# Items with XS

```
<Report here information about code tangles and fat packages>
```

| Item                                | Tangled | Fat  | Size | XS   |
| ----------------------------------- | ------- | ---- | ---- | ---- |
| ezshop.it.polito.ezshop.data.EZShop |         | 142  | 3650 | 565  |
| ezshop.it.polito.ezshop             | 2.50%   |      | 5088 | 127  |



# Package level tangles

```
<Report screen captures of the package-level tangles by opening the items in the "composition perspective" 
(double click on the tangle from the Views->Complexity page)>
```

ezshop.it.polito.ezshop

![ezshop.it.polito.ezshop](/Users/tauro/git/ezshop/Images/DesignAssessmentImgs/ezshop.it.polito.ezshop.png)

ezshop.it.polito.ezshop.data

![ezshop.it.polito.ezshop.data](/Users/tauro/git/ezshop/Images/DesignAssessmentImgs/ezshop.it.polito.ezshop.data.png)

ezshop.it.polito.ezshop.exceptions

![ezshop.it.polito.ezshop.exceptions](/Users/tauro/git/ezshop/Images/DesignAssessmentImgs/ezshop.it.polito.ezshop.exceptions.png)

ezshop.it.polito.ezshop.model

![ezshop.it.polito.ezshop.model](/Users/tauro/git/ezshop/Images/DesignAssessmentImgs/ezshop.it.polito.ezshop.model.png)

# Summary analysis

```
<Discuss here main differences of the current structure of your project vs the design delivered on April 30>
<Discuss if the current structure shows weaknesses that should be fixed>
```
