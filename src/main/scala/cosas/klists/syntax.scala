package ohnosequences.cosas.klists

import ohnosequences.cosas._, fns._

case object syntax {

  case class KListSyntax[L <: AnyKList](val l: L) extends AnyVal {

    def head[H <: L#Bound, T <: KList.Of[L#Bound]](implicit c: IsKCons[L,H,T])
    : H =
      c.h(l)

    def tail[H <: L#Bound, T <: KList.Of[L#Bound]](implicit c: IsKCons[L,H,T])
    : T =
      c.t(l)

    def at[N <: AnyNat, Z <: L#Bound](n: N)(implicit a: App1[L at N, L, Z])
    : Z =
      a(l)

    def take[N <: AnyNat, Z <: KList.Of[L#Bound]](n: N)(implicit take: AnyApp1At[take[N], L] {type Y = Z })
    : Z =
      take(l)

    def drop[N <: AnyNat, Z <: KList.Of[L#Bound]](n: N)(implicit drop: AnyApp1At[drop[N], L] { type Y = Z })
    : Z =
      drop(l)

    def span[A <: AnyNat, B <: AnyNat, Z <: KList.Of[L#Bound]](start: A, end: B)(implicit
      sp: AnyApp1At[span[A,B], L] { type Y = Z }
    )
    : Z =
      sp(l)


    def ::[E <: L#Bound](e: E)
    : E :: L =
      KCons[E,L](e, l)

    def find[A <: L#Bound](implicit findIn: App1[A findIn L, L, A])
    : A =
      findIn(l)

    def pick[E <: L#Bound, O <: KList.Of[L#Bound]](w: Witness[E])(implicit
      p: AnyApp1At[pick[E], L] { type Y = (E,O) }
    )
    : (E,O) =
      p(l)

    def pickSubtype[E, X <: E, O <: AnyKList](w: Witness[E])(implicit
      p: AnyApp1At[PickSubtype[X,E], L] { type Y = (X,O)}
    )
    : (X,O) =
      p(l)

    def takeFirst[Q <: KList.Of[L#Bound]](w: Witness[Q])(implicit
      takeFirst: AnyApp1At[takeFirst[Q], L] { type Y = Q }
    )
    : Q =
      takeFirst(l)

    def replaceFirst[S <: AnyKList { type Bound = L#Bound }](s: S)(implicit
      replaceFirst: AnyApp2At[replace[L], L, S] { type Y = L }
    )
    : L =
      replaceFirst(l,s)

    def toList(implicit conv: App1[toList[L], L, List[L#Bound]])
    : List[L#Bound] =
      conv(l)

    def toListOf[B >: L#Bound](implicit conv: App1[toListOf[L,B], L, List[B]])
    : List[B] =
      conv(l)

    def ++[
      S   <: AnyKList { type Bound = L#Bound },
      LS  <: AnyKList { type Bound = L#Bound }
    ](s: S)(implicit concatenate: App2[concatenate[L], L, S, LS])
    : LS =
      concatenate(l,s)

    def foldLeft[
      F <: AnyDepFn2,
      Z <: F#Out,
      O <: F#Out
    ](f: F)(z: Z)(implicit
      foldl: AnyApp3At[FoldLeft[L,F,Z],L,Z,F] { type Y = O }
    )
    : O =
      foldl(l,z,f)
  }
}
