package ohnosequences.cosas.tests

import ohnosequences.cosas._, types._, typeSets._, properties._, altRecords._

object altRecordTestsContext {

  case object id extends Property[Integer]("id")
  case object name extends Property[String]("name")
  case object notProperty

  case object simpleUser extends RecordType(id :@: name :@: EmptyRecord)

  // more properties:
  case object email extends Property[String]("email")
  case object color extends Property[String]("color")

  case object normalUser extends RecordType(id :@: name :@: email :@: color :@: EmptyRecord)

  val vProps = email :@: color :@: EmptyRecord
  // nothing works with this
  val vRecord = new RecordType(vProps)

  val vEmail = "oh@buh.com"

  val vRecordEntry = vRecord(
    (email(vEmail)) :~:
    (color("blue")) :~:
    ∅
  )
  // creating a record instance is easy and neat:
  val simpleUserEntry = simpleUser (
    (id(123)) :~:
    (name("foo")) :~:
    ∅
  )

  // TODO add reorder
  // this way the order of properties does not matter
  val normalUserEntry = normalUser (
    (id(123))               :~:
    (name("foo"))           :~:
    (email("foo@bar.qux"))  :~:
    (color("orange"))       :~:
    ∅
  )
}


class AltRecordTests extends org.scalatest.FunSuite {

  import altRecordTestsContext._

  test("should fail when some properties are missing") {
    // you have to set _all_ properties
    assertTypeError("""
    val wrongAttrSet = simpleUser(id(123) :~: ∅)
    """)

    // but you still have to present all properties:
    assertTypeError("""
    val wrongAttrSet = normalUser(
      id(123) :~:
      name("foo") :~:
      ∅
    )
    """)
  }

  test("can access fields") {

    assert { (simpleUserEntry get id)   === id(123)     }
    assert { (simpleUserEntry get name) === name("foo") }
  }

  test("can access fields from vals and volatile vals") {

    assert{ (vRecordEntry get email) === email("oh@buh.com") }
  }

  test("can update fields") {

    assert {

      ( normalUserEntry update color("albero") ) === normalUser(
        (normalUserEntry get id)    :~:
        (normalUserEntry get name)  :~:
        (normalUserEntry get email) :~:
        color("albero")             :~:
        ∅
      )
    }

    assert {

      ( normalUserEntry update name("bar") :~: id(321) :~: ∅ ) === normalUser(
          id(321)               :~:
          name("bar")           :~:
          email("foo@bar.qux")  :~:
          color("orange")       :~:
          ∅
        )
    }
  }

  test("can see a record entry as another") {

    assert {

      normalUserEntry === ( simpleUserEntry as (normalUser, email("foo@bar.qux") :~: color("orange") :~: ∅) )
    }
  }

  test("can provide properties in different order") {

    // the declared property order
    implicitly [
      simpleUser.Raw =:= (ValueOf[id.type] :~: ValueOf[name.type] :~: ∅)
    ]

    // they get reordered
    val simpleUserV: ValueOf[simpleUser.type] = simpleUser {
      name("Antonio") :~:
      id(29681)       :~: ∅
    }

    val sameSimpleUserV: ValueOf[simpleUser.type] = simpleUser {
      id(29681)       :~:
      name("Antonio") :~: ∅
    }

    assert { simpleUserV == sameSimpleUserV }
  }
}