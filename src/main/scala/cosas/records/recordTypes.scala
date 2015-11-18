package ohnosequences.cosas.records

import ohnosequences.cosas._, fns._, types._, klists._

sealed trait AnyRecordType extends AnyType {

  type Keys <: AnyProductType
  val  keys: Keys

  // should be provided implicitly:
  val noDuplicates: noDuplicates isTrueOn Keys#Types

  type Raw = Keys#Raw

  lazy val label: String = toString
}

class RecordType[Ks <: AnyProductType](
  val keys: Ks
)(implicit
  val noDuplicates: noDuplicates isTrueOn Ks#Types
) extends AnyRecordType {

  type Keys = Ks
  // type Raw = Ks#Raw
}

case object AnyRecordType {

  type withKeys[Ks <: AnyProductType] = AnyRecordType { type Keys = Ks }

  implicit def recordTypeSyntax[RT <: AnyRecordType](rt: RT)
  : syntax.RecordTypeSyntax[RT] =
    syntax.RecordTypeSyntax(rt)

  implicit def recordReorderSyntax[Vs <: AnyKList.withBound[AnyDenotation]](vs: Vs)
  : syntax.RecordReorderSyntax[Vs] =
    syntax.RecordReorderSyntax(vs)

  implicit def recordDenotationSyntax[RT <: AnyRecordType, Vs <: RT#Raw](rv: RT := Vs)
  : syntax.RecordTypeDenotationSyntax[RT, Vs] =
    syntax.RecordTypeDenotationSyntax(rv.value)
}