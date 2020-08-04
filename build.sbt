name := """scala-play-transaction"""
organization := "com.gaku3601"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  jdbc,
  evolutions,
  "org.postgresql" % "postgresql" % "42.2.14",
  "org.scalikejdbc" %% "scalikejdbc" % "3.4.2",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.4.2",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.4",
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.gaku3601.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.gaku3601.binders._"
