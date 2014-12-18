package ohnosequences.cosas.tests

import ohnosequences.cosas._, AnyType._

object WrapTestsContext {

  object Color extends Wrap[String]("Color")

  object User extends Type("User")

  object Friend extends Type("Friend")

  case class userInfo(id: String, name: String, age: Int)
}

class WrapTests extends org.scalatest.FunSuite {
  import WrapTestsContext._

  test("creating values") {

    val azul = Color denoteWith "blue"
    val verde = new ValueOf[Color.type]("green")
    val amarillo = Color denoteWith "yellow"

    assert{ azul.value == "blue" }
    assert{ verde.value == "green" }
    assert{ amarillo.value == "yellow" }
  }
}

class DenotationTests extends org.scalatest.FunSuite with ScalazEquality {
  import WrapTestsContext._

  test("create denotations") {
    import Denotes._

    /* the right-associative syntax */
    val uh: userInfo :%: User.type = userInfo(id = "adqwr32141", name = "Salustiano", age = 143) :%: User
    val z = User denoteWith 2423423
  }

  test("type-safe equals") {

    val paco = "Paco"
    val jose = "Jose"

    val u1 = paco :%: User
    val u1Again = paco :%: User

    val u2 = paco :%: Friend
    val v = jose :%: Friend

    assert { u1 === u1 }
    assert { u1 === u1Again }
    // assert { u2 =/= v } // not there in ScalaTest :-/
    // assert { u1 === u2 }
    assertTypeError("u1 === u2")
    assert{ !( u2 === v ) }
  }
}
