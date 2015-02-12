scalaVersion := "2.11.5"

organization := "com.fortysevendeg"

organizationName := "47 Degrees"

organizationHomepage := Some(new URL("http://47deg.com"))

name := "annotate-your-case-classes"

version := "1.0"

lazy val root = (project in file(".")).aggregate(macros, examples)

lazy val macros = project.in(file("modules/macros"))

lazy val examples = project.in(file("modules/examples")).dependsOn(macros)