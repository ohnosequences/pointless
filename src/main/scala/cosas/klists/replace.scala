package ohnosequences.cosas.klists

import ohnosequences.cosas._, fns._

class Replace[S <: AnyKList] extends DepFn2[S, AnyKList, S]

case object Replace extends ReplaceInTail {

  implicit def empty[S <: AnyKList { type Bound = X }, X]:
    AnyApp2At[Replace[S], S, *[X]] { type Y = S } =
    App2 { (s: S, q: *[S#Bound]) => s }

  implicit def replaceHead[
    H <: T#Bound, T <: AnyKList,
    Q <: AnyKList, QOut <: AnyKList
  ](implicit
    pickHead: AnyApp1At[pick[H], Q] { type Y = (H,QOut) },
    replace: AnyApp2At[Replace[T], T, QOut] { type Y = T }
  )
  : AnyApp2At[Replace[H :: T], H :: T, Q] { type Y = H :: T } =
    App2 { (s: H :: T, q: Q) => { val (h, qq) = pickHead(q); h :: replace(s.tail, qq) } }
}

trait ReplaceInTail {

  implicit def skipHead[
    H <: T#Bound, T <: AnyKList,
    Q <: AnyKList, QOut <: AnyKList
  ](implicit
    replace: AnyApp2At[Replace[T], T,Q] { type Y = T }
  )
  : AnyApp2At[Replace[H :: T], H :: T, Q] { type Y = H :: T } =
    App2 { (s: H :: T, q: Q) => s.head :: replace(s.tail, q) }
}