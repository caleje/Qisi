
object ShapelessScratch {
  import shapeless._
  import HList._
  import shapeless.syntax.singleton

  // def singleton[T](t: T): Set[T] = Set(t)
  // List(1,2,3) map singleton
  // List("foo", "bar", "baz") map singleton
  23 :: "foo" :: false :: HNil // map singleton

}