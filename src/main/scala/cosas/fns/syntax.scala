package ohnosequences.cosas.fns

case object syntax {

  case class DepFn1Syntax[DF <: AnyDepFn1](val df: DF) extends AnyVal {

    final def at[I <: DF#In1, O <: DF#Out](f: I => O): App1[DF,I,O] =
      App1(f)
  }

  case class DepFn2Syntax[DF <: AnyDepFn2](val df: DF) extends AnyVal {

    final def at[X1 <: DF#In1, X2 <: DF#In2, Y <: DF#Out](f: (X1,X2) => Y): App2[DF,X1,X2,Y] =
      App2(f)
  }

  case class DepFn3Syntax[DF <: AnyDepFn3](val df: DF) extends AnyVal {

    final def at[
      X1 <: DF#In1, X2 <: DF#In2, X3 <: DF#In3,
      Y <: DF#Out
    ]
    (f: (X1,X2,X3) => Y): App3[DF,X1,X2,X3,Y] =
      App3(f)
  }

  case class DepFn1ApplySyntax[DF0 <: AnyDepFn1, I0 <: DF0#In1, O0 <: DF0#Out](val df: DF0) extends AnyVal {

    final def apply(x1: I0)(implicit app: App1[DF0,I0,O0]): O0 =
      app(x1)
  }

  case class DepFn2ApplySyntax[DF0 <: AnyDepFn2, X10 <: DF0#In1, X20 <: DF0#In2, O0 <: DF0#Out](val df: DF0) extends AnyVal {

    final def apply(x1: X10, x2: X20)(implicit app: App2[DF0,X10,X20,O0]): O0 =
      app(x1,x2)
  }

  case class DepFn3ApplySyntax[
    DF0 <: AnyDepFn3,
    X10 <: DF0#In1, X20 <: DF0#In2, X30 <: DF0#In3,
    O0 <: DF0#Out
  ]
  (val df: DF0) extends AnyVal {

    final def apply(x1: X10, x2: X20, x3: X30)(implicit
      app: App3[DF0,X10,X20,X30,O0]
    )
    : O0 =
      app(x1,x2,x3)
  }
}
