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
	}

	runMe();
	print(addMe);
	print(globalVar);
 ```
 If we define the above as a statically typed language, then the last two prints would be 10 and 0. The variable globalVar would be 0 because