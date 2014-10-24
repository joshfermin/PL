### CSCI 3155: Lab Assignment 3
Yu Zhou and Josh Fermin

1. JavaScripty Interpreter: Tag Testing, Recursive Functions, and Dynamic Scoping.

	a. First, write some JAVASCRIPTY programs and execute them as JavaScript programs. This step
	will inform how you will implement your interpreter and will serve as tests for your
	interpreter.
	Below is my code:

	``` javascript
	const x=14;
	const f = function(x) {
		const x=13
		return x
	}
	x+f(x)
	```

	The Scala interpreter handles the code under static scoping, producing the result of 27, as
	x=14 is defined outside of the function, while the function(x) returns 13.
	The dynamic scoping way will return 26, as the const x=13 is able to change the original
	value of x outside of the function.




2. JavaScripty Interpreter: Substitution and Evaluation Order.

	c. The evaluation order is deterministic. The evaluation order is e1 then e2. The “Do rules” require both e1 and e2 to be values. If either of them is not a value the search rule is applied to do recursive calls to make e1 a value v1. After e1 is a value, search rule is called on e2 recursively to make e2 a value v2. When e1 and e2 are finally both values we can start to do the operators.


3. Evaluation Order.

	Until e1 and e2 are both values, we do the search rule to reduce them into values.
	SEARCHBINARY1 is used to ensure that e1 is a value. If not, do it recursively until it is a value.

	```
	e1 → e1′
	------------------------
	e1 bop e2 → e1 ′ bop e2
	```

	SEARCHBINARYARITH2 is used to do the reduction of e2, when e1 is a value v1 but e2 is not:

	```
	e2 → e2 ′ bop +
	-----------------------
	v1 bop e2 → v1 bop e2′
	```

	To obtain the opposite evaluation order we would write the next two rules:
	```
	e2  → e2 ′ 
	-----------------------
	e2 bop e1 → e2′ bop e1


	e1 → e1 ′  bop +
	-----------------------
	e1 bop v2 → e1′ bop v2
	```

	

4. Short-Circuit Evaluation

	a. An example would be: in java or c++ when we need to write a for loop to find if there is an element with the value of 100 in an integer array of size n.
	
	If we write the for loop as follows:
  	``` java
	for (int i=0;(array[i]!=100)&&(i<n);i++)
	```
	It may run into a segmentation fault if the array does not contain any element of value 100.

	However if we revise the code as:

	``` java
	for (int i=0;(i<n)&&(array[i]!=100); i++)
	```

	The for loop will be fine, because if we run into the condition where i==n, the for loop is not again executed as the compiler does not judge the right part of “&&” as long as the “i<n” is no longer true.

	b. Yes. In the bop(And, e1, e2) if the value of e1 is true, it still needs to check the value of e2. But if value of e1, v1 is false, e2 is never checked/evaluated. Same idea applies to bop(Or, e1, e2) and If(e1,e2,e3). The compiler checks the value of e1, and skips certain parts according to the value of e1.


