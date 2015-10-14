package ohnosequences.cosas.fns

/* Dependent functions aka dependent products */
trait AnyDepFn {
  type Out
}
trait AnyDepFn0 extends AnyDepFn
trait DepFn0[O] extends AnyDepFn0 {
  type Out = O
}

trait AnyDepFn1 extends AnyDepFn {
  type In1
}
case object AnyDepFn1 {

  implicit def depFn1Syntax[DF <: AnyDepFn1](df: DF): syntax.DepFn1Syntax[DF] =
    syntax.DepFn1Syntax(df)

  implicit def depFn1ApplySyntax[
    DF0 <: AnyDepFn1,
    X10 <: DF0#In1
  ](df: DF0): syntax.DepFn1ApplyAt[DF0,X10] =
    syntax.DepFn1ApplyAt(df)
}

trait DepFn1[I,O] extends AnyDepFn1 {
  type In1 = I
  type Out = O
}

trait AnyDepFn2 extends AnyDepFn {
  type In1
  type In2
}
case object AnyDepFn2 {

  implicit def depFn2Syntax[DF <: AnyDepFn2](df: DF): syntax.DepFn2Syntax[DF] =
    syntax.DepFn2Syntax(df)

  implicit def depFn2ApplySyntax[
    DF0 <: AnyDepFn2,
    A0 <: DF0#In1,
    B0 <: DF0#In2
  ](df: DF0): syntax.DepFn2ApplyAt[DF0,A0,B0] =
    syntax.DepFn2ApplyAt(df)
}
trait DepFn2[I1,I2,O] extends AnyDepFn2 {
  type In1 = I1
  type In2 = I2
  type Out = O
}

trait AnyDepFn3 extends AnyDepFn {
  type In1
  type In2
  type In3
}
case object AnyDepFn3 {

  implicit def depFn3Syntax[DF <: AnyDepFn3](df: DF): syntax.DepFn3Syntax[DF] =
    syntax.DepFn3Syntax(df)

  implicit def depFn3ApplySyntax[
    DF0 <: AnyDepFn3,
    A0 <: DF0#In1, B0 <: DF0#In2, C0 <: DF0#In3
  ](df: DF0): syntax.DepFn3ApplyAt[DF0,A0,B0,C0] =
    syntax.DepFn3ApplyAt(df)
}
trait DepFn3[I1, I2, I3, O] extends AnyDepFn3 {
  type In1 = I1
  type In2 = I2
  type In3 = I3
  type Out = O
}

trait AnyDepFn1Composition extends AnyDepFn1 {

  type First <: AnyDepFn1 { type Out <: Second#In1 }
  val first: First
  type Second <: AnyDepFn1
  val second: Second

  type In1 = First#In1
  type Out = Second#Out
}

class Composition[
  F <: AnyDepFn1 { type Out <: S#In1 },
  S <: AnyDepFn1
]
(val first: F, val second: S) extends AnyDepFn1Composition {

  type First = F
  type Second = S
}
case object Composition {

  implicit def appForComposition[
    F <: AnyDepFn1 { type Out >: M },
    S <: AnyDepFn1 { type In1 >: F#Out },
    X1 <: F#In1,
    M,
    O <: S#Out
  ](implicit
    appF: AnyApp1At[F,X1] { type Y = M },
    appS: AnyApp1At[S,M]
  ): App1[Composition[F,S],X1,appS.Y] = App1 { x1: X1 => appS(appF(x1)) }

}

/* dependent function application machinery. These are to be thought of as the building blocks for terms of a dependent function type. */
trait AnyApp extends Any {

  type DepFn <: AnyDepFn
  type Y <: DepFn#Out
}

trait AnyApp0 extends Any with AnyApp {

  type DepFn <: AnyDepFn0

  def apply: Y
}

trait AnyApp1 extends Any with AnyApp {

  type DepFn <: AnyDepFn1
  type X1 <: DepFn#In1

  def apply(in: X1): Y
}

trait AnyApp1At[DF <: AnyDepFn1,A0 <: DF#In1] extends Any with AnyApp1 {

  type DepFn = DF;
  type X1 = A0
}

trait AnyApp2 extends Any with AnyApp {

  type DepFn <: AnyDepFn2
  type X1 <: DepFn#In1; type X2 <: DepFn#In2

  def apply(in1: X1, in2: X2): Y
}

trait AnyApp2At[DF <: AnyDepFn2,A0 <: DF#In1,B0 <: DF#In2] extends Any with AnyApp2 {

  type DepFn = DF;
  type X1 = A0; type X2 = B0
}

trait AnyApp3 extends Any with AnyApp {

  type DepFn <: AnyDepFn3
  type X1 <: DepFn#In1; type X2 <: DepFn#In2; type X3 <: DepFn#In3

  def apply(in1: X1, in2: X2, in3: X3): Y
}

trait AnyApp3At[DF <: AnyDepFn3,A0 <: DF#In1,B0 <: DF#In2,C0 <: DF#In3] extends Any with AnyApp3 {

  type DepFn = DF;
  type X1 = A0; type X2 = B0; type X3 = C0
}

case class App1[
  DF <: AnyDepFn1,
  I <: DF#In1,
  O <: DF#Out
]
(val does: I => O) extends AnyVal with AnyApp1At[DF,I] {

  type Y = O

  final def apply(in: X1): Y =
    does(in)
}

case class App2[
  DF <: AnyDepFn2,
  I1 <: DF#In1, I2 <: DF#In2,
  O <: DF#Out
]
(val does: (I1,I2) => O) extends AnyVal with AnyApp2At[DF,I1,I2] {

  type Y = O

  final def apply(in1: X1, in2: X2): Y =
    does(in1,in2)
}

case class App3[
  DF <: AnyDepFn3,
  I1 <: DF#In1, I2 <: DF#In2, I3 <: DF#In3,
  O <: DF#Out
]
(val does: (I1,I2,I3) => O) extends AnyVal with AnyApp3At[DF,I1,I2,I3] {

  type Y = O

  final def apply(in1: X1, in2: X2, in3: X3): Y =
    does(in1,in2,in3)
}
