import com.sksamuel.scapegoat.sbt.ScapegoatSbtPlugin.autoImport._
import scalariform.formatter.preferences._

val `scala_2.13` = "2.13.0"

val `scala_2.12` = "2.12.8"

crossScalaVersions := Seq(`scala_2.13`, `scala_2.12`)

scalaVersion in ThisBuild := `scala_2.12`

organization in ThisBuild := "pl.jozwik.demo"

name := "sbt-simple-multiProject"

scapegoatVersion in ThisBuild := "1.3.9"

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
  "-Ydelambdafy:method"
)

val readPlayVersion = {
  val lineIterator = scala.io.Source.fromFile(new java.io.File("project", "play.sbt")).getLines()
  val line = lineIterator.find(line => line.contains("playVersion")).getOrElse( """val playVersion = "2.7.3" """)
  val versionString = line.split("=")(1).trim
  versionString.replace("\"", "")
}

val circeVersion = "0.11.1"

val `org.scalatest_scalatest` = "org.scalatest" %% "scalatest" % "3.0.8"

val `org.scalacheck_scalacheck` = "org.scalacheck" %% "scalacheck" % "1.14.0"

val `com.typesafe.scala-logging_scala-logging` = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

val `ch.qos.logback_logback-classic` = "ch.qos.logback" % "logback-classic" % "1.2.3"

val `org.scalatestplus_play` = "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"

val `net.codingwell_scala-guice` = "net.codingwell" %% "scala-guice" % "4.2.5"

val `com.typesafe.play_play-json` = "com.typesafe.play" %% "play-json" % "2.7.4"

val `io.circe_circe-java8` = "io.circe" %% "circe-java8" % circeVersion

val `play-circe_play-circe` = "com.dripower" %% "play-circe" % "2711.0"


lazy val services = projectName("services", file("domain/services")).settings(
  libraryDependencies ++= Seq(
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
    cacheApi,
    filters,
    guice,
    `io.circe_circe-java8`,
    `play-circe_play-circe`,
    `org.scalatestplus_play` % Test
  ),
  packMain := Map("view" -> "play.core.server.ProdServerStart"),
  Revolver.settings,
  mainClass in reStart := Option("play.core.server.ProdServerStart")
).dependsOn(storage).enablePlugins(SbtWeb)
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .enablePlugins(PackPlugin)


def projectName(name: String, file: File): Project = Project(name, file).settings(
  libraryDependencies ++= Seq(
    `org.scalatest_scalatest`  % Test,
    `org.scalacheck_scalacheck`  % Test),
  publishArtifact in(Compile, packageDoc) := false,
  sources in(Compile, doc) := Seq.empty,
  scalariformPreferences := scalariformPreferences.value
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DanglingCloseParenthesis, Preserve)
)
