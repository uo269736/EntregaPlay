name := """EntregaPlay_Recetas"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies += guice

enablePlugins(PlayEbean)
libraryDependencies += evolutions
libraryDependencies += jdbc

libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.12"

// Para utilizar la cache:
libraryDependencies ++= Seq(
  ehcache
)

//libraryDependencies += "org.glassfish.jaxb" % "jaxb-core" % "2.3.0.1"
//libraryDependencies += "org.glassfish.jaxb" % "jaxb-runtime" % "2.3.2"

