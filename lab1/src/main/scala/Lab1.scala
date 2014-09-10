object Lab1 extends jsy.util.JsyApplication {
  import jsy.lab1.ast._
  import jsy.lab1.Parser
  
  /*
   * CSCI 3155: Lab 1
   * Josh Fermin
   * 
   * Partner: Louis Bouddhou
   * Collaborators: <Any Collaborators>
   */

  /*
   * Fill in the appropriate portions above by replacing things delimited
   * by '<'... '>'.
   * 
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
  
  /*
   * Example with a Unit Test
   * 
   * A convenient, quick-and-dirty way to experiment, especially with small code
   * fragments, is to use the interactive Scala interpreter.
   * 
   * To run a selection in the interpreter in Eclipse, highlight the code of interest
   * and type Ctrl+Shift+X (on Windows) or Cmd+Shift+X (on Mac).
   * 
   * Highlight the next few lines below to try it out.  The assertion passes, so
   * it appears that nothing happens.  You can uncomment the "bad test specification"
   * and see that a failed assert throws an exception.
   * 
   * You can try calling 'plus' with some arguments, for example, plus(1,2).  You
   * should get a result something like 'res0: Int = 3'.
   * 
   * As an alternative, the testPlus2 function takes an argument that has the form
   * of a plus function, so we can try it with different implementations.  For example,
   * uncomment the "testPlus2(badplus)" line, and you will see an assertion failure.
   * 
   * Our convention is that these "test" functions are testing code that are not part
   * of the "production" code.
   * 
   * While writing such testing snippets are convenient, it is not ideal.  For example,
   * the 'testPlus1()' call is run whenever this object is loaded, so in practice,
   * it should probably be deleted for "release".  A more robust way to maintain
   * unit tests is in a separate file.  For us, we use the convention of writing
   * tests in a file called LabXSpec.scala (i.e., Lab1Spec.scala for Lab 1).
   */
  
  def plus(x: Int, y: Int): Int = x + y
  def testPlus1() {
    assert(plus(1,1) == 2)
    //assert(plus(1,1) == 3) // bad test specification
  }
  testPlus1()

  def badplus(x: Int, y: Int): Int = x - y
  def testPlus2(plus: (Int, Int) => Int) {
    assert(plus(1,1) == 2)
  }
  //testPlus2(badplus)

  /* Exercises */

  def abs(n: Double): Double = if(n < 0) -n else n

  def xor(a: Boolean, b: Boolean): Boolean = if((a || b) && (a != b)) true else false

  def repeat(s: String, n: Int): String = { 
   require(n >= 0)
   if(n == 0) "" else s+repeat(s,n-1)
  }
  
  def sqrtStep(c: Double, xn: Double): Double = xn - (((xn*xn)-c)/(2*xn))

  def sqrtN(c: Double, x0: Double, n: Int): Double = {
    require(n>=0 && c>0)
    if(n == 0) {
      return x0 
    } 
    else sqrtN(c, sqrtStep(c, x0), n-1)  
  }
  
  def sqrtErr(c: Double, x0: Double, epsilon: Double): Double = {
    require(epsilon > 0)
    if(abs(x0*x0 - c) < epsilon) {
      return x0
    }
    else sqrtErr(c, sqrtStep(c, x0), epsilon)
  }

  def sqrt(c: Double): Double = {
    require(c >= 0)
    if (c == 0) 0 else sqrtErr(c, 1.0, 0.0001)
  }
  
  /* Search Tree */
  
  sealed abstract class SearchTree
  case object Empty extends SearchTree
  case class Node(l: SearchTree, d: Int, r: SearchTree) extends SearchTree
  
  def repOk(t: SearchTree): Boolean = {
    def check(t: SearchTree, min: Int, max: Int): Boolean = t match {
      case Empty => true
      case Node(l, d, r) => if(d >= min && d < max) check(l,min,d)&&check(r,d,max) else false
    }
    check(t, Int.MinValue, Int.MaxValue)
  }
  
  def insert(t: SearchTree, n: Int): SearchTree = t match {
    /*
      In the case the tree is empty, we create a new node that contains our Int n as 
      its data element and gives it empty children
    */
    case Empty => Node(Empty, n, Empty) 
    /*
      In the case that the tree already has a node (with a data value d in it) and 
      children:
    */
    case Node(l, d, r) => {
      /*
        If the data value in place equals our Int n
      */
      if(n == d)
        /*
          We just return the node as is with our Int n (= data value). 
          And check the next if statement.
        */
        Node(l, n, r)
      if(n < d) 
        /*
          If our Int n is smaller than the data value d, we create a new node
          by inserting a left Search sub tree (with a left empty child an dkeeping track of 
          our Int n), keeping the parent data value d intact, and keeping the right subtree
          the same.
        */
        Node(insert(l,n), d, r)  
      else 
        /*
          If our Int n is greater than or equal to the data value d, we create a new node
          by inserting a right Search sub tree (with a right empty child and keeping track of 
          our Int n), keeping the parent data value d intact, and keeping the left subtree
          the same.
        */
        Node(l, d, insert(r, n))
      }
  }
  
  def deleteMin(t: SearchTree): (SearchTree, Int) = {
    require(t != Empty)
    (t: @unchecked) match {
      /*
        In the case that the left sub tree is empty, we just return a tuple
        which contains the right subtree (as every data values in the right 
        subtree is greater than our current data value d) followed by that 
        same data value (which is effectively the smallest number being the parent)
      */
      case Node(Empty, d, r) => (r, d)
      /*
        Otherwise, recurse deleteMin until you have found the left-most child 
        and that value m in a tuple with the subtree it is in (this subtree should 
        contain its parent data value and the right subtree coming from it)
      */
      case Node(l, d, r) =>
        val (l1, m) = deleteMin(l)
        /*
          Then we only have to return a tuple which contains:
            - the new node that has 
          the rest of our tree l1, the parent's data value and the right subtree which
          now becomes a "left child" of d
            - the minimum value m that we just deleted
        */
        (Node(l1,d,r), m)
    }
  }
 
  def delete(t: SearchTree, n: Int): SearchTree = t match {
    /*
      If no children, then delete everything - return empty
    */
    case Empty => Empty
    case Node(Empty, d, Empty) => if(n == d) Empty else t
    /*
      If right child is not empty, check if number is in datavalue 
        if true : return a new tree without the specified data value (n)
        if false: recurse down to the right subtree and continue looking for the
          value
    */
    case Node(Empty, d, r) => if(n == d) r else Node(Empty,n,delete(r,n))
    /*
      If left child is not empty, check if number is in datavalue 
        if true : return a new tree without the specified data value (n)
        if false: recurse down to the left subtree and continue looking for the
          value
    */
    case Node(l, d, Empty) => if(n == d) l else Node(delete(l,n),n,Empty)
    case Node(l, d, r) =>  if (n == d) {
        /*
          when n == d, find the minimum value of the right subtree
          this node will be the one that replaces the node you want
          to delete. For that to happen, we need to delete that smallest
          value from the right subtree and make it ourselves (v). 

          We create a tuple that contains the last subtree minus its
          minimum value and the minimum value stored in v

          We use the minimum value to replace n which deletes the value we 
          want. This works because it is smaller than the right subtree
        */
          val (r1, v) = deleteMin(r)
          Node(l, v, r1)
        } else if (d > n) {
          Node(delete(l, n), d, r)
        }
        else {
          Node(l, d, delete(r, n))  
        }
  }
  
  /* JavaScripty */
  def eval(e: Expr): Double = e match {
    case N(n) => n
    case Unary(Neg, e1) => -1 * eval(e1)
    case Binary(Plus, e1, e2) => eval(e1) + eval(e2) 
    case Binary(Minus, e1, e2) => eval(e1) - eval(e2)
    case Binary(Times, e1, e2) => eval(e1) * eval(e2)
    case Binary(Div, e1, e2) => eval(e1) / eval(e2)
    case _ => throw new UnsupportedOperationException
  }
  
 // Interface to run your interpreter from a string.  This is convenient
 // for unit testing.
 def eval(s: String): Double = eval(Parser.parse(s))



 /* Interface to run your interpreter from the command-line.  You can ignore the code below. */ 
  
 def processFile(file: java.io.File) {
    if (debug) { println("Parsing ...") }
    
    val expr = Parser.parseFile(file)
    
    if (debug) {
      println("\nExpression AST:\n  " + expr)
      println("------------------------------------------------------------")
    }
    
    if (debug) { println("Evaluating ...") }
    
    val v = eval(expr)
    
    println(v)
  }

}
