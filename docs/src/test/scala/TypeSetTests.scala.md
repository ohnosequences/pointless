
```scala
package ohnosequences.typesets.tests

import shapeless._
import shapeless.test.illTyped
import ohnosequences.typesets._

class TypeSetTests extends org.scalatest.FunSuite {

  test("empty set") {
    implicitly[in[?]#isnot[Any]]
    // or with nicer syntax:
    implicitly[Any ? ?]

    assert(set('a') === ('a' :~: ?))
  }

  test("bounding") {
    implicitly[boundedBy[Nothing]#is[?]]

    trait foo
    case object boo extends foo
    case object buh extends foo

    val foos = boo :~: buh :~: ?
    implicitly[boundedBy[foo]#is[foos.type]]

    val vals = 1 :~: 'a' :~: true :~: ?
    implicitly[boundedBy[AnyVal]#is[vals.type]]
  }

  test("subset") {
    val s = set(1)
    implicitly[? ? ?]
    implicitly[? ? s.type]
    implicitly[s.type ? s.type]

    val a = 100500 :~: 'a' :~: ?
    val b = 'b' :~: 1 :~: true :~: ?
    implicitly[a.type ? b.type]

    implicitly[(Int :~: Char :~: ?) ? (Char :~: Int :~: ?)]
    implicitly[(Int :~: Char :~: ?) ? (Char :~: Int :~: ?)]
    implicitly[(Int :~: Char :~: ?) ~ (Char :~: Int :~: ?)]
  }

  test("contains/lookup") {
    val s = 1 :~: 'a' :~: "foo" :~: ?
    type st = s.type

    implicitly[Int ? st]
    assert(s.lookup[Int] === 1)

    implicitly[Char ? st]
    assert(s.lookup[Char] === 'a')

    implicitly[String ? st]
    assert(s.lookup[String] === "foo")

    trait truth;
    trait happiness;
    implicitly[    truth ? st]
    implicitly[happiness ? st]

    // Neither of these two things work:
    // implicitly[Nothing ? st]
    // implicitly[Nothing ? st]
  }

  test("subtraction") {
    val s = 1 :~: 'a' :~: "foo" :~: ?

    assert(? \ ? === ?)
    assert(? \ s === ?)
    assert(s \ ? === s)
    assert(s \ s === ?)

    case object bar
    val q = bar :~: true :~: 2 :~: bar.toString :~: ?

    assert(s \ q === set('a'))
    assert(q \ s === bar :~: true :~: ?)
  }

  test("union") {
    val s = 1 :~: 'a' :~: "foo" :~: ?

    case object bar
    val q = bar :~: true :~: 2 :~: bar.toString :~: ?

    assert((? U ?) === ?)
    assert((? U q) === q)
    assert((s U ?) === s)

    val sq = s U q
    val qs = q U s
    implicitly[sq.type ~ qs.type]
    assert(sq === 'a' :~: bar :~: true :~: 2 :~: "bar" :~: ?)
    assert(qs === bar :~: 'a' :~: true :~: 2 :~: "bar" :~: ?)
  }

  test("hlist ops") {
    assert(?.toHList === HNil)
    assert((1 :~: 'a' :~: "foo" :~: ?).toHList === (1 :: 'a' :: "foo" :: HNil))
  }

  test("to list") {
    assert(?.toList === Nil)
    assert((1 :~: 'a' :~: "foo" :~: ?).toListWith[Any] === List[Any](1, 'a', "foo"))

    trait foo
    case object boo extends foo
    case object buh extends foo

    val s = boo :~: buh :~: ?
    assert(s.toList === List[foo](boo, buh))
  }

  test("mapper") {
    import poly._

    object id extends Poly1 { implicit def default[T] = at[T](t => t) }
    object toStr extends (Any -> String)(_.toString)
    object rev extends Poly1 { 
      implicit val str = at[String](t => t.reverse)
      implicit def list[T] = at[List[T]](t => t.reverse)
      implicit def default[T] = at[T](t => t)
    }

    assert(?.map(toStr) === ?)

    val s = 1 :~: 'a' :~: "foo" :~: List(1,2,3) :~: ?
    assert(s.map(id) === s)
    assert(s.map(rev) === 1 :~: 'a' :~: "oof" :~: List(3,2,1) :~: ?)

    // This case should fail, because toStr in not "type-injective"
    illTyped("implicitly[SetMapper[toStr.type, s.type]]")
    illTyped("s.map(toStr)")

    assert(s.mapHList(toStr) === "1" :: "a" :: "foo" :: "List(1, 2, 3)" :: HNil)
    assert(s.mapList(toStr) === List("1", "a", "foo", "List(1, 2, 3)"))
    // assert(s.mapHList(toStr).toList === s.mapList(toStr))
  }

}

```


------

### Index

+ src
  + main
    + scala
      + [HListOps.scala][main/scala/HListOps.scala]
      + [LookupInSet.scala][main/scala/LookupInSet.scala]
      + [MapFoldSets.scala][main/scala/MapFoldSets.scala]
      + [package.scala][main/scala/package.scala]
      + [SetMapper.scala][main/scala/SetMapper.scala]
      + [SubtractSets.scala][main/scala/SubtractSets.scala]
      + [TypeSet.scala][main/scala/TypeSet.scala]
      + [TypeUnion.scala][main/scala/TypeUnion.scala]
      + [UnionSets.scala][main/scala/UnionSets.scala]
  + test
    + scala
      + [TypeSetTests.scala][test/scala/TypeSetTests.scala]

[main/scala/HListOps.scala]: ../../main/scala/HListOps.scala.md
[main/scala/LookupInSet.scala]: ../../main/scala/LookupInSet.scala.md
[main/scala/MapFoldSets.scala]: ../../main/scala/MapFoldSets.scala.md
[main/scala/package.scala]: ../../main/scala/package.scala.md
[main/scala/SetMapper.scala]: ../../main/scala/SetMapper.scala.md
[main/scala/SubtractSets.scala]: ../../main/scala/SubtractSets.scala.md
[main/scala/TypeSet.scala]: ../../main/scala/TypeSet.scala.md
[main/scala/TypeUnion.scala]: ../../main/scala/TypeUnion.scala.md
[main/scala/UnionSets.scala]: ../../main/scala/UnionSets.scala.md
[test/scala/TypeSetTests.scala]: TypeSetTests.scala.md