% PEP: 227 - Statically Nested Scopes
% Louis Bouddhou, Alex Campbell, Josh Fermin

What is the problem?
  
Nested functions (including lambdas) can reference variables defined in the surrouding namespace.

Static scoping is already implemented between functions, but not within nested functions.

Example - Without Statically Nested Scopes
========

Why this wouldn't work


```python
 def bank_account(initial_balance):
      balance = [initial_balance]
      def deposit(amount):
          balance[0] = balance[0] + amount
          return balance
      def withdraw(amount):
          balance[0] = balance[0] - amount
          return balance
      return deposit, withdraw
``` 

# Introduced changes in this PEP

Gives nested functions the scope of parent functions.

This allows for variables within the parent function to be inherited by the nested function.



Namespaces
==========

Whenever you run a simple Python 2.1 script, the interpreter treats it as a module called __main__, which gets its own namespace. However, in order to avoid ambiguity, Python stores names in the context they're supposed to live in. Those contexts are called namespaces and there are three of them. A local namespace, a Global namespace and a Builtin namespace.

Local Namespace
===============

The local namespace for a function is created when the function is called, and deleted (or forgotten) when the function returns or raises an exception that is not handled by a function.

Global Namespace
================

The global namespace for a module is created as soon as the interpreter starts. Obviously we want to make sure that any nested subroutine in the function can see names living outside of ther local namespace.e

Builtin Namespace
=================


Problems this PEP addresses: Utility
====================================

* Because we do not have access to outer scope variables (pre-pep), lambdas and other nested functions have to either set variables to the global scope, or redefine them in their new function.

Example
=======
* This is cumbersome because we need to explicitly pass any name used in the body of the lambda must be explicitly passed as a default argument to the lambda.  With nested static scoping,  'root' will be available to 'Button', therefore making it much more useful.

Problems this PEP addresses: Non-lexical
=========================================

* Most developers are used to  nested static scoping (lexical scoping) and introducing this pep will lower the barriers to entry to the python language.

Example
=======
* If we come back to our previous example, we can see 

Resolving Free Variables
========================

* Free variables are variables used in a function that are not local variables nor parameters of that function.  With this pep, variables in outer scopes are not free, they are bound to the current functions scope.

















Discussion
==========
* The PEP  allow names defined in a function to be referenced in any nested function defined with that function, these rules apply except for the following 3:
  1. Name in class scope is not accessible
  2. Global statement short-circuits the normal rules
  3. Varibles are not declared.


Discussion - Name in Class Scope
================================
* For classes, variable names will be resolved in the innermost nested function
  * this is to prevent odd interactions between class attributes and local variable access. 

```
http://stackoverflow.com/questions/12941748/python-variable-scope-and-classes
```

Discussion - Short Circuit
==========================
* The rule that Python chooses is: any assignment within a block establishes a new local binding, unless a global statement for the name appears in the block, in which case the name always refers to a binding in the module-global environment instead.

```python
myvariable = 5
def func():
    global myvariable
    myvariable = 6   #changes global scope
    print myvariable #prints 6

func()
print myvariable  #prints 6
```
http://stackoverflow.com/questions/13881395/in-python-what-is-a-global-statement
https://www.farside.org.uk/201307/understanding_python_scope

Discussion - Variables Not Declared
===================================
* Name bindings are local to the function that they are bound in.
* The only declaration of a variable is the global declaration
* This makes it so that assignment operations can only happen in current scope or global scope

Problems - Backwards Compatibility
=======================
* Two kinds of compatibility problems caused:
  1. Code behavior
  2. Syntax errors

Example - Code Behavior
=======
```python
    x = 1
    def f1():
        x = 2
        def inner():
            print x
        inner()
```
* before this PEP it would print  because the inner scope would not get the scope of the f1 function.
* after this PEP it prints 2, therefore changing the behavior of code.


Example - Syntax Errors
=======
``` python
y = 1
def f():
    exec "y = 'gotcha'" # or from module import *
    def g():
        return y
```











