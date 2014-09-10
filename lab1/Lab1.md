# Lab 1 - Writeup

1. Scala Basics: Binding and Scope 
	
	a. The use of pi at line 4  is bound on line 3, while the use of pi at line 7 is bound at line 1. This is because a bound variable is a variable that is used and defined inside an expression (http://docs.scala-lang.org/glossary/)
	
	b. The use of x at line 3 is bound at line 2, the use of x at line 6 is also bound at line 5, the use of x at line 10 is bound at 5, and the use of x at line 13 is bound at line 1.

2. Scala Basics: Typing

	The return of g is a tuple as follows: ((Int,Int),Int). We determined this type because on line 2 we can see that b is a tuple of Ints because: as seen on line 1, x is declared as an Int. And 3 is an Int.
	Also the expression a is given the value 1, which is an Int, on line 2.

	Body expression:
		
		a:Int because 
			1:Int
		b:(Int, Int) because
			x:Int
			3:Int
		if (x == 0) (b, 1) else (b, a + 2):((Int, Int), Int) because
			x ==0: Boolean because 
				x:Int
				0:Int
			(b, 1): ((Int, Int), Int) because
				b:(Int, Int) 
				1:Int
			(b, a+2): ((Int, Int), Int) because
				b:(Int, Int)
				a + 2: Int because
					a:Int
					2:Int



	





