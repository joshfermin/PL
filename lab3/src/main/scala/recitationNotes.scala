// BIG STEP

// evalneg - convert inference rule to code
  // eval(env,e1)
  // eToVal(e1)
  // toNum(eval(env,e1))
  // -toNum(eval(env,e1))

//evalPlusNumber
  // eval(e1)
  // eval(e2)
        // case (S(s1), v2) => S(s1 + toStr(v2)) - EvalPlusString1
        // case (v1, S(s2)) => S(toStr(v1) + s2) - EvalPlusString2
        // case (v1, v2) => N(toNumber(v1) + toNumber(v2)) - EvalPlusNumber

// Need to write two more cases for EvalCall
  // e1 eval to v1 which is a function, e' is function body
  // function(x) {x + 5} (2+8) - javascripty syntax for calling
    // function(x) {x + 5} - e1
        // { x + 5 } - e
        // x is x
    // (2+8) - e2

  // call eval(e1) == Function(_,_,_) - if not true throw type error (needs to be function)
  // cal eval(e1, v1, e2) - N(10)
  // call eval(extend(env, x, v2), e')


// and EvalCallRec
  // function f(x) {x + 5} (2+8) - javascripty syntax for calling
    // x2 = x
    // x1 = x
    // e1 - evaluates to v1
    // extend environment with two values x1 - f x2 for v2
      // eval(extend(extend(env,x1,v1), x2, v2), e1)
      // eval(extend(extend(env,x2,v2), x1, v1), e1)
      // dosent matter since mapping will be the same

// environment is a map from strings to values
// Need type error equality, for these two arguments if one isnt a function throw type error


// Small Step 
  // e1 + e2
    // first evaluate e1 to a point where it is a final (terminal) value (1st step)
    // then evaluate e2 to some other final value (2nd step)
    // then add two values into final 1 step (v1 + v2)

    // SEARCH RULE (dont have value yet, keep stepping)
    // e1 -> e1'
    // --------------
    // e1+e2-> e1'+e2

    // DO RULE 1 (have one value keep stepping for other value)
    // e2 -> e2'  v1 = value
    // --------------
    // v1+e2-> v1+e2'

    // DO RULE 2 (have both values, just do the computation)
    // cant step on e1 anymore since it is a value
    // then take a small step on e2,

    // n' = toNum(v1) + toNum(v2)
    // -------------------------
    // v1+v2 -> n'

    // bottom is javascripty, top is scala 

  // DO RULEs are corner cases i.e. base cases, then write search rules.

  // Examples:
  // Do NEG
    def step(e:Expr) e match => {
      case Unary(uop, e1) if isValue(e1) => // if e1 is a value apply do rule, if not apply search rule
    }








    