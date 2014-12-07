% PEP: 227 - Statically Nested Scopes
% Louis Bouddhou, Alex Campbell, Josh Fermin

What is the problem?
===================  
* Cannot reference a variable in a higher order function (nested).
* Static scoping does not work within nested functions.

Example - Without Statically Nested Scopes
==========================================
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

Introduced changes in this PEP
==============================
* Gives nested functions the scope of parent functions.
* This allows for variables within the parent function to be inherited by the nested function.

Example - With Statically Nested Scopes
=======================================
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

Namespaces
========== 

* Local
* Global
* Builtin

Local Namespace
===============

```python
def function(num):
    foo = num * num
    return var

function(2)
```

Global Namespace
================

```python
bar = 10
def function(num)
    foo = bar * num
    return foo
function(2)
```

Builtin Namespace
=================













Problems this PEP addresses: Utility
=====================================
* Limited utility of nested functions.

Example
=======
```python
from Tkinter import *
root = Tk()
Button(root, text="Click here",
       command=lambda root=root: root.test.configure(text="..."))
```

Problems this PEP addresses: Non-lexical
=========================================
* Confusion among new users who are used to lexical scoping.

Example
=======
```python

def make_adder(base):
  def adder(x):
    return base + x
  return adder
add5 = make_adder(5)
add5(6)

``` 
11


Resolving Free Variables
========================
* Nested functions have access to variables in other scopes.

Anonymous Functions
===================






















Discussion
==========
* The PEP works under all circumstances except for the following cases:
  1. Name in class scope is not accessible
  2. Global statement short-circuits the normal rules

Discussion - Name in Class Scope
================================
* Names in a class scope:
* Resolved in the innermost (nested) function

Discussion - Short Circuit
==========================
* Global statement is unaffected by change

```python
myvariable = 5
def func():
    global myvariable
    myvariable = 6   #changes global var
    print myvariable #prints 6

func()
print myvariable  #prints 6
```

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

Example - Syntax Errors
=======
``` python
y = 1
def f():
    exec "y = 'gotcha'" # or from module import *
    def g():
        return y
```

Conclusion
==========
* FILL THIS IN PLS