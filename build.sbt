name := "sleek-json"

version := "0.0.1"

scalaVersion := "2.10.1"

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.1",
  "org.scalatest" %% "scalatest" % "1.9.1" % "test"
  )
