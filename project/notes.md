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
============

Whenever you run a simple Python 2.1 script, the interpreter treats it as a module called __main__, which gets its own namespace. However, in order o avoid anbiguity, Python will store names and objects in the context they are supposed to live in. Those contexts are called namespaces and there are three of them. A Local namespace, a Global namespace and a Builtin namespace.


Problems this PEP addresses: Utility
====================================

* This is cumbersome because we need to explicitly pass any name used in the body of the lambda must be explicitly passed as a default argument to the lambda.  With nested static scoping,  'root' will be available to 'Button', therefore making it much more useful.

Problems this PEP addresses: Non-lexical
=========================================

* Most developers are used to  nested


















Discussion
==========
* The PEP works under all circumstances except for the following three cases:
  1. Name in class scope is not accessible
  2. Global statement short-circuits the normal rules
  3. Varibles are not declared.


Discussion - Name in Class Scope
================================
* Names in a class scope:
* Resolved in the innermost (nested) f  ction
  * talk about why this is necessary

Discussion - Short Circuit
==========================
* Global statement is unaffected by change

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

Discussion - Variables Not Declared
===================================
* 

Problems - Backwards Compatibility
=======================
* Two kinds of compatibility problems   used:
  1. Code behav  r
  2. Syntax errors

Example
=======
```python
    x = 1
    def f1():
        x = 2
        def inner():
            print x
        inner()
```










