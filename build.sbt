ThisBuild / scalaVersion := "2.13.5"
ThisBuild / organization := "com.47deg"

lazy val macros = project
  .in(file("modules/macros"))
  .settings(
    name := "macros",
    resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo),
    libraryDependencies ++= Seq(
      scalaOrganization.value % "scala-compiler" % scalaVersion.value % Provided
    ),
    scalacOptions += "-Ymacro-annotations"
  )

lazy val examples = project
  .in(file("modules/examples"))
  .settings(
    name := "macros",
    resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo),
    libraryDependencies ++= Seq(
      scalaOrganization.value % "scala-compiler" % scalaVersion.value % Provided,
      "org.scalatest" %% "scalatest" % "3.2.5" % "test"
    ),
    scalacOptions += "-Ymacro-annotations"
  ).dependsOn(macros)