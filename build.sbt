import com.sksamuel.scapegoat.sbt.ScapegoatSbtPlugin.autoImport._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import com.typesafe.sbt.SbtScalariform.autoImport._

import scalariform.formatter.preferences.AlignSingleLineCaseStatements
import scalariform.formatter.preferences.SpacesAroundMultiImports

scalaVersion in Global := "2.11.7"

organization in Global := "pl.jozwik.demo"

name := "sbt-simple-multiProject"

version in Global := "1.0.0"


scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code",
  "-language:reflectiveCalls",
  "-Ybackend:GenBCode",
  "-Ydelambdafy:method"
)

val readPlayVersion = {
  val lineIterator = scala.io.Source.fromFile(new java.io.File("project", "play.sbt")).getLines()
  val line = lineIterator.find(line => line.contains("playVersion")).getOrElse( """val playVersion = "2.4.6" """)
  val versionString = line.split("=")(1).trim
  versionString.replace("\"", "")
}

val `org.scalatest_scalatest` = "org.scalatest" %% "scalatest" % "2.2.6" % "test"

val `org.scalacheck_scalacheck` = "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"

val `com.typesafe.scala-logging_scala-logging` = "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"

val `ch.qos.logback_logback-classic` = "ch.qos.logback" % "logback-classic" % "1.1.3"

val `org.scalatestplus_play` = "org.scalatestplus" %% "play" % "1.4.0" % "test"

val `net.codingwell_scala-guice` = "net.codingwell" %% "scala-guice" % "4.0.1"

val `com.typesafe.play_play-json` = "com.typesafe.play" %% "play-json" % readPlayVersion

lazy val services = projectName("services", file("domain/services")).settings(
  libraryDependencies ++= Seq(
    `org.scalatest_scalatest`,
    `org.scalacheck_scalacheck`,
    `com.typesafe.scala-logging_scala-logging`,
    `ch.qos.logback_logback-classic`,
    `com.typesafe.play_play-json`
  )
)

lazy val storage = projectName("storage", file("infrastructure/storage")).settings(
  libraryDependencies ++= Seq(
    `net.codingwell_scala-guice`
  )
).dependsOn(services)


lazy val view = projectName("view", file("presentation/view")).settings(
  libraryDependencies ++= Seq(
    cache,
    filters,
    `org.scalatestplus_play`
  ),
  packAutoSettings,
  packMain := Map("view" -> "play.core.server.ProdServerStart")
).dependsOn(storage).enablePlugins(SbtWeb)
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)


def projectName(name: String, file: File): Project = Project(name, file).settings(
  SbtScalariform.scalariformSettings,
  publishArtifact in(Compile, packageDoc) := false,
  sources in(Compile, doc) := Seq.empty,
  scalariformSettings,
  scapegoatVersion := "1.1.1",
  scapegoatIgnoredFiles := Seq(".*/target/.*"),
  ScalariformKeys.preferences := ScalariformKeys.preferences.value.
    setPreference(AlignSingleLineCaseStatements, true).
    setPreference(SpacesAroundMultiImports, false)
)
