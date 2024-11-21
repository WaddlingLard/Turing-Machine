# Project 3: Turing-Machine

* Author: Brian Wu, Max Ma
* Class: CS361 Section 1
* Semester: Fall 2024 

## Overview

This Java application implements a Turing Machine simulator that can process input strings according to transition rules defined in text files. The program reads machine configurations from.


## Reflection

- What worked well and what was a struggle? 

  The things that worked well is understanding what the P3 Doc is asking for and have clear vision to implement what's needed. It was relatively straightforward to produce what was asked for like reading the test files and creating several TM related classes. A struggle would be speed up our program even faster than it is already is. There was several iterations of DS's we used when trying to optimize speed. Firstly, we used a LinkedList, and while it worked, it was incredibly slow. This was because our execute() function used purely indexing to grab elements which is a big nono for LinkedLists. Then the idea came in to use an ArrayList. It's the closest thing to an array, which is the most ideal solution except an array's size isn't dynamic. Needless to say, using an ArrayList is stupid fast. As it turns out, using a DS that has O(1) indexing for a method that using nothing but indexing to grab data would turn out well. We were also curious to attempt using a HashMap, but it turns out it is slower than an ArrayList.  

- What concepts still aren't quite clear?

Understanding which part of code that require modification to speed up futher for the program. There are many things that can be tuned to achieve a faster speed, like even the small things. These can be like using a .equals() over a '==' and type conversion that slowly accumlates milliseconds. There were several instances where we had to adjust the code to reduce comparisons and conversions to optimize speed. The program could have more to optimize but it feels like it is nearing peak speeds.
  
- What techniques did you use to make your code easy to debug and modify?

Making tracing output string in toString to check if TMSimulator is tracing correctly from each test file that outputting correct specification from the .txt already exist. We also created an output string in the TMState.java class to see the transitions are properly instantiated. 
  
- What would you change about your design process?

One of ways that's helping the program speed up running is to incorperate arrayList to track starting start and sigma since we can index those directly. One alteration could be understanding the different pros and cons of DS to see what is the best choice for the tape.

- If you could go back in time, what would you tell yourself about doing this project?

  I would tell myself to start this project not overthinking anything, and apply the TM notes what's covered in the class and P3 document. That and also use use only arrays and an ArrayList. It will get rid of a lot of headaches.

## Compiling and Using
To run test files, make sure to put them inside the `/Test` folder

To compile, execute the following command in the main project directory:
```
# Navigate to parent directory (above tm/)
cd ..

# Compile
javac tm/*.java

# Run file0.txt for example (without .java extension) 
java tm.TMSimulator file0.txt

java tm.TMSimulator <testfile (in Test)>

```

## Sources used

- https://docs.oracle.com/javase/8/docs/api/java/ 
