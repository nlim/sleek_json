name := "sleek_json"

version := "0.0.1"

scalaVersion := "2.10.1"

parallelExecution in Test := false

libraryDependencies ++= Seq(
    "com.fasterxml.jackson.core" % "jackson-core" % "2.1.1",
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.1",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.1",
    "org.scalatest" %% "scalatest" % "1.9.1" % "test"
  )
