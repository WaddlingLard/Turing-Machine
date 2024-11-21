# Project 3: Turing-Machine

* Author: Brian Wu ,Max Ma
* Class: CS361 Section 1
* Semester: Fall 2024 

## Overview
This Java application implements a Turing Machine simulator that can process input strings according to transition rules defined in text files. The program reads machine configurations from.


## Reflection

- What worked well and what was a struggle? 
  things worked well is understanding what the P3 Doc is asking for and have clear vision to implement what's needed, struggle would be speed up our program even faster than it is already is.

- What concepts still aren't quite clear?
Understanding which part of code that require modification to speed up futher for the program.
  
- What techniques did you use to make your code easy to debug and modify?
Making tracing output string in toStirng to check if TMSimulator is tracing correctly from each test file that outputting correct specification from the .txt already exist.
  
- What would you change about your design process?
One of ways that's helping the program speed up running is to incorperate arrayList to track starting start and sigma since we can index those directly.

- If you could go back in time, what would you tell yourself about doing this project?
  I would tell myself to start this project not overthinking anything , and apply the TM notes what's covered in the class and P3 document.

## Compiling and Using

To compile, execute the following command in the main project directory:
```
# Navigate to parent directory (above tm/)
cd ..

# Compile
javac tm/*.java

# Run file0.txt for example (without .java extension) 
java tm.TMSimulator file0.txt

```

## Sources used

- https://docs.oracle.com/javase/8/docs/api/java/ 
