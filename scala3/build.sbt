val scala3Version = "3.3.1"

ThisBuild / scalaVersion := scala3Version
ThisBuild / scalafmtOnCompile := true

lazy val y2023 = project
    .in(file("2023"))
    .settings(
      name := "AoC 2023",
      libraryDependencies ++= Seq(
        "com.lihaoyi" %% "fastparse" % "3.0.2",
        "org.scalameta" %% "munit" % "0.7.29" % Test
      )
    )

lazy val root = project
    .in(file("."))
    .aggregate(y2023)
