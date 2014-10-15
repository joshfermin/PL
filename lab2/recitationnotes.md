# Recitation Notes
ftp://ftp.cs.cmu.edu/usr/ftp/usr/rwh/www/home/courses/ppl06/notes/conc.pdf

3 main sets

Digits (dig)
Numbers (num)
Expressions (exp)
	correlated with Aobjects and Vobjects
	A is D symbol, Aobjects is dig set.

Digits D ::= 0 | 1 | · · · | 9
Numbers N ::= D | N D
Expressions E ::= N | E+E | E*E
```
------- axiom1 ------- axiom2      ------- axiom10 
0 ∈ dig 	   1 ∈ dig        .... 9 ∈ dig
```

name the axioms i.e. axiom1, etc.



n number of rules, write n number of judgment forms:
RULE 1
N::= D => x ∈ dig 
		  ------- 
		  x ∈ num

RULE 2
N::= ND =>  x ∈ dig  y ∈ dig 
			----------------
				xy ∈ num


E::= N | E+E | E*E
RULE 3
```scala
x ∈ num
-------
x ∈ exp
```
RULE 4
```scala
e1 ∈ exp e2 ∈ exp
-----------------
   e1+e2 ∈ exp  
```
RULE 5


PROVE 23+243 ∈ exp (start from bottom to top)
```scala
		 3 ∈ dig a4
		 -------
2 ∈ dig  3 ∈ num
----------------	  
2 ∈ dig  3 ∈ num      2 ∈ dig 43∈ num
	--------			 ---------
	23 ∈ num    		 243 ∈ num
	-------- 			 --------- r4
	23 ∈ exp 			 243 ∈ exp
-------------------------------
   		  23+243 ∈ exp
```

2B

2+(3*5)
(2+3)*5

2+2*2+2 - create something like this that wont implicate associativity.





