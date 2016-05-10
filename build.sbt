name := "qisi"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test",
  "org.typelevel" %% "cats" % "0.5.0",
  "com.madhukaraphatak" %% "java-sizeof" % "0.1"
)