name := """EntregaPlay_Recetas"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies += guice

enablePlugins(PlayEbean)
libraryDependencies += evolutions
libraryDependencies += jdbc
