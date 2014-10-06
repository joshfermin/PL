object Lab2 extends jsy.util.JsyApplication {
  import jsy.lab2.Parser
  import jsy.lab2.ast._
  
  /*
   * CSCI 3155: Lab 2
   * http://math.chapman.edu/~jipsen/js/
   */

  /*
   * Replace the 'throw new UnsupportedOperationException' expression with
   * your code in each function.
   * 
   * Do not make other modifications to this template, such as
   * - adding "extends App" or "extends Application" to your Lab object,
   * - adding a "main" method, and
   * - leaving any failing asserts.
   * 
   * Your lab will not be graded if it does not compile.
   * 
   * This template compiles without error. Before you submit comment out any
   * code that does not compile or causes a failing assert.  Simply put in a
   * 'throws new UnsupportedOperationException' as needed to get something
   * that compiles without error.
   */
  
  /* We represent a variable environment is as a map from a string of the
   * variable name to the value to which it is bound.
   * 
   * You may use the following provided helper functions to manipulate
   * environments, which are just thin wrappers around the Map type
   * in the Scala standard library.  You can use the Scala standard
   * library directly, but these are the only interfaces that you
   * need.
   */
  
  type Env = Map[String, Expr]
  val emp: Env = Map()
  def get(env: Env, x: String): Expr = env(x)
  def extend(env: Env, x: String, v: Expr): Env = {
    require(isValue(v)) // input parameter must be a value and not an expression
    env + (x -> v)
  }
  
  /* Some useful Scala methods for working with Scala values include:
   * - Double.NaN
   * - s.toDouble (for s: String)
   * - n.isNaN (for n: Double)
   * - n.isWhole (for n: Double)
   * - s (for n: Double)
   * - s format n (for s: String [a format string like for printf], n: Double)
   */

  def toNumber(v: Expr): Double = {
    require(isValue(v))
    (v: @unchecked) match {
      case N(n) => n
      case null => 0.0
      case B(false) => 0.0
      case B(true) => 1.0
      case Undefined => Double.NaN
      case S(s) => try s.toDouble catch { case _: Throwable => Double.NaN }// try to see if string can be a double if not return NaN
      case _ => throw new UnsupportedOperationException
    }
  }
  
  def toBoolean(v: Expr): Boolean = {
    require(isValue(v))
    (v: @unchecked) match {
      case B(b) => b
      case S("") => false //if empty string false
      case S(_) => true //if not, true
      case N(n) if n == 0.0 || n == -0.0 || n.isNaN => false // compare if n is 0.0/-0.0/NaN, return fasle 
      case N(_) => true
      case Undefined => false
    }
  }
  
  def toStr(v: Expr): String = {
    require(isValue(v))
    (v: @unchecked) match {
      case S(s) => s
      case B(true) => "true"
      case B(false) => "false"
      case N(n) => if(n.isWhole) "%.0f" format n else n.toString // %.0f is the format string for a float, with 0 decimal places.
      case Undefined => "undefined"
      case _ => throw new UnsupportedOperationException
    }
  }
  
  def eval(env: Env, e: Expr): Expr = {
    /* Some helper functions for convenience. */
    def eToVal(e: Expr): Expr = eval(env, e)
    e match {
      /* Base Cases */
      case _ if (isValue(e)) => e
      /* Inductive Cases */
      case Undefined => Undefined
      case Print(e1) => println(pretty(eToVal(e1))); Undefined
      // if statement lets us know if first expression is true or false, if true return eval of second expression
      case Binary(And, e1, e2) => 
        if (toBoolean(eval(env, e1))) eval(env, e2) else eval(env, e1) 
      case Binary(Or, e1, e2) => 
        if (toBoolean(eval(env, e1))) eval(env, e1) else eval(env, e2)
      case Binary(Plus, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (S(s1), v2) => S(s1 + toStr(v2))
        case (v1, S(s2)) => S(toStr(v1) + s2)
        case (v1, v2) => N(toNumber(v1) + toNumber(v2))
      }
      case Binary(Minus, e1, e2) => N(toNumber(eval(env,e1)) - toNumber(eval(env,e2)))
      case Binary(Times, e1, e2) => N(toNumber(eval(env,e1)) * toNumber(eval(env,e2)))
      case Binary(Div, e1, e2) => N(toNumber(eval(env,e1)) / toNumber(eval(env,e2)))
      case Binary(Eq, e1, e2) => B(eval(env,e1) == eval(env, e2))
      case Binary(Ne, e1, e2) => (eToVal(e1), eToVal(e2)) match {
        case (S(s1), S(s2)) => B(s1 != e2)
        case (N(n1), N(n2)) => B(n1 != n2)
        case (B(b1), B(b2)) => B(b1 != b2)
        case (_, _) => B(true)
      }         
      case Binary(Lt,e1,e2) => (eToVal(e1), eToVal(e2)) match {
        case (S(s1), S(s2)) => B(s1 < s2)
        case (N(n1), N(n2)) => B(n1 < n2)
        case (B(b1), B(b2)) => B(b1 < b2)
        case (_, _) => B(false)
      }         
      case Binary(Le, e1, e2) => B(toNumber(eval(env, e1)) <= toNumber(eval(env, e2)))
      case Binary(Gt, e1, e2) => B(toNumber(eval(env, e1)) > toNumber(eval(env, e2)))
      case Binary(Ge, e1, e2) => B(toNumber(eval(env, e1)) >= toNumber(eval(env, e2)))

      case If(e1, e2, e3) => if (toBoolean(eval(env, e1))) eval(env, e2) else eval(env, e3)
      case Binary(Seq, e1, e2) => eval(env, e1); eval(env, e2)

      case Unary(Neg, e1) => N(-toNumber(e1))
      case Unary(Not, e1) => B(!toBoolean(e1))

      case ConstDecl(x, e1, e2) => {
        // myvar is new environment variable "x"
        // map to e1. use this map to eval e2
        // dynamically typing x to type of e1
        val myvar = extend(env, x, eval(env,e1)) 
        eval(myvar, e2) 
      }

      case Var(x) => get(env,x)

      case _ => throw new UnsupportedOperationException
    }
  }
    
  // Interface to run your interpreter starting from an empty environment.
  def eval(e: Expr): Expr = eval(emp, e)

  // Interface to run your interpreter from a string.  This is convenient
  // for unit testing.
  def eval(s: String): Expr = eval(Parser.parse(s))

 /* Interface to run your interpreter from the command-line.  You can ignore what's below. */ 
 def processFile(file: java.io.File) {
    if (debug) { println("Parsing ...") }
    
    val expr = Parser.parseFile(file)
    
    if (debug) {
      println("\nExpression AST:\n  " + expr)
      println("------------------------------------------------------------")
    }
    
    if (debug) { println("Evaluating ...") }
    
    val v = eval(expr)
    
    println(pretty(v))
  }
}