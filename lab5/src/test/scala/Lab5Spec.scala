import org.scalatest._
import jsy.lab5.ast._
import Lab5._

class Lab5Spec extends FlatSpec {
  
  "mapFirstDoWith" should "map the first element where f returns Some" in {
     val l1 = List(1, 2, -3, 4, -5)
     val gold1 = List(1, 2, 3, 4, -5)
     def dowith[W]: DoWith[W,List[Int]] = mapFirstWith[W,Int] { (i: Int) => if (i < 0) Some(doreturn(-i)) else None } (l1)
     assertResult((true,gold1)) { dowith(true) }
     assertResult((42,gold1)) { dowith(42) }
  }


  "Do Neg" should "negate values" in {
  	val e = Unary(Neg, N(42))
  	val (mp:Mem, ep:Expr) = step(e){ Mem.empty } // expectation
  		// want to know that you went from empty memory -> empty memory
  		// <M , -n> -> <M , n'>
  	assertResult(N(-42)) { ep } // ep = eprime
  }	
  // Probably want to write some tests for castOk, typeInfer, substitute, and step.
}
