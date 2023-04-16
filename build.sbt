lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.10"
    )),
    name := "scalatest-example"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test


libraryDependencies += "org.typelevel" %% "cats-effect" % "3.4.8"

resolvers += Resolver.sonatypeRepo("snapshots")
val http4sVersion = "0.23.18"
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion, 
  "org.http4s" %% "http4s-ember-server" % http4sVersion, 
  "org.http4s" %% "http4s-ember-client" % http4sVersion, 

  "org.http4s" %% "http4s-circe" % http4sVersion, 
  "io.circe" %% "circe-generic" % "0.14.5",
  "io.circe" %% "circe-literal" % "0.14.5",

  "co.fs2" %% "fs2-core" % "3.6.1"
)

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
//addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)