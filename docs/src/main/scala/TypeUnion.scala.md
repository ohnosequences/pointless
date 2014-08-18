
## Type union

After http://www.chuusai.com/2011/06/09/scala-union-types-curry-howard/#comment-179
Credits: Lars Hupel


```scala
package ohnosequences.typesets


trait TypeUnion {
  type or[S] <: TypeUnion
  type get
}

// need to add NotIn based on sum type bounds
trait OneOf[T] extends TypeUnion {
  type or[S] = OneOf[T with not[S]]  
  type get = not[T]
}
```

These aliases mean that some type is (or isn't) a member of the union

```scala
@annotation.implicitNotFound(msg = "Can't prove that ${X} IS one of the types in the union type ${U}")
sealed class :<:[ X : oneOf[U]#is,   U <: TypeUnion]

@annotation.implicitNotFound(msg = "Can't prove that ${X} is NOT one of the types in the union type  ${U}")
sealed class :<!:[X : oneOf[U]#isnot, U <: TypeUnion]

```


------

### Index

+ src
  + main
    + scala
      + items
        + [items.scala][main/scala/items/items.scala]
      + ops
        + [Choose.scala][main/scala/ops/Choose.scala]
        + [Lookup.scala][main/scala/ops/Lookup.scala]
        + [Map.scala][main/scala/ops/Map.scala]
        + [MapFold.scala][main/scala/ops/MapFold.scala]
        + [Pop.scala][main/scala/ops/Pop.scala]
        + [Reorder.scala][main/scala/ops/Reorder.scala]
        + [Replace.scala][main/scala/ops/Replace.scala]
        + [Subtract.scala][main/scala/ops/Subtract.scala]
        + [ToList.scala][main/scala/ops/ToList.scala]
        + [Union.scala][main/scala/ops/Union.scala]
      + [package.scala][main/scala/package.scala]
      + pointless
        + impl
      + [Property.scala][main/scala/Property.scala]
      + [Record.scala][main/scala/Record.scala]
      + [Representable.scala][main/scala/Representable.scala]
      + [TypeSet.scala][main/scala/TypeSet.scala]
      + [TypeUnion.scala][main/scala/TypeUnion.scala]
  + test
    + scala
      + items
        + [itemsTests.scala][test/scala/items/itemsTests.scala]
      + [RecordTests.scala][test/scala/RecordTests.scala]
      + [TypeSetTests.scala][test/scala/TypeSetTests.scala]

[main/scala/items/items.scala]: items/items.scala.md
[main/scala/ops/Choose.scala]: ops/Choose.scala.md
[main/scala/ops/Lookup.scala]: ops/Lookup.scala.md
[main/scala/ops/Map.scala]: ops/Map.scala.md
[main/scala/ops/MapFold.scala]: ops/MapFold.scala.md
[main/scala/ops/Pop.scala]: ops/Pop.scala.md
[main/scala/ops/Reorder.scala]: ops/Reorder.scala.md
[main/scala/ops/Replace.scala]: ops/Replace.scala.md
[main/scala/ops/Subtract.scala]: ops/Subtract.scala.md
[main/scala/ops/ToList.scala]: ops/ToList.scala.md
[main/scala/ops/Union.scala]: ops/Union.scala.md
[main/scala/package.scala]: package.scala.md
[main/scala/Property.scala]: Property.scala.md
[main/scala/Record.scala]: Record.scala.md
[main/scala/Representable.scala]: Representable.scala.md
[main/scala/TypeSet.scala]: TypeSet.scala.md
[main/scala/TypeUnion.scala]: TypeUnion.scala.md
[test/scala/items/itemsTests.scala]: ../../test/scala/items/itemsTests.scala.md
[test/scala/RecordTests.scala]: ../../test/scala/RecordTests.scala.md
[test/scala/TypeSetTests.scala]: ../../test/scala/TypeSetTests.scala.md