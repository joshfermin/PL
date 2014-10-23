## Lab 3 - Writeup 
Josh Fermin and Yu Zhou

1. JavaScripty Interpreter: Tag Testing, Recursive Functions, and Dynamic Scopings

 a. An example in which dynamic scoping behaves differently than static scoping would be as follows: 

 Static Scoping: 
 ``` javascript
	globalVar=0;

	function runMe(){
	    var globalVar = 10;
	    callMe();
	}

	function callMe(){
	   addMe = globalVar+10;
	   print(addMe)
	}

	runMe();
 ```
 If we define the above as a statically typed language, then calling runMe will return and print out a 10. The variable globalVar would be 0 because in statically typed languages the function callMe does not inherit scope from runMe.

 If we define the above as a dynamically typed language, then calling runMe will return and print out a 20. This is because callMe inherits scope from runMe so that is why addMe is now 20.

