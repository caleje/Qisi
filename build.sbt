name := "qisi"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.scalatest" % "scalatest_2.12" % "3.0.3" % "test",
  "org.typelevel" %% "cats" % "0.9.0",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test
)