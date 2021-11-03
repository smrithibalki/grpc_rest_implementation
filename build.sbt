name := "CS_homework3"

version := "0.1"

scalaVersion := "2.13.6"


val akkaVersion = "2.5.26"

val akkaHttpVersion = "10.1.11"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}


libraryDependencies ++=Seq(
  "com.typesafe" % "config" % "1.3.2",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.gnieh" % "logback-config" % "0.3.1",
  "org.scalactic" %% "scalactic" % "3.2.9",
  "org.scalatest" %% "scalatest" % "3.2.9" % "test",
  "org.scalatest" %% "scalatest-featurespec" % "3.2.9" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.1",
  "com.amazonaws" % "aws-lambda-java-events" % "3.10.0",
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  //"com.thesamet.scalapb" %% "scalapb-json4s" % "0.7.0",
  // akka streams
  "com.twitter" %% "finagle-http" % "21.10.0",

  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
// akka http
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion

)
Compile / PB.targets := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)




