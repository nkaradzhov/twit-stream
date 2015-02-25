name := """minimal-akka-scala-seed"""

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.9" withSources() withJavadoc(),
  "com.typesafe.akka" %% "akka-testkit" % "2.3.9" % "test" withSources() withJavadoc(),
  "org.scalatest" %% "scalatest" % "2.2.4" % "test" withSources() withJavadoc(),
  "io.spray" %% "spray-can" % "1.3.2" withSources() withJavadoc(),
  "io.spray" %% "spray-routing" % "1.3.2" withSources() withJavadoc(),
  "io.spray" %% "spray-json" % "1.3.1" withSources() withJavadoc()
  )
  
EclipseKeys.withSource := true
