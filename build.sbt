ThisBuild / scalaVersion := "2.13.3"
ThisBuild / organization := "ru.d10xa"

enablePlugins(GitVersioning)

lazy val har = project
  .in(file("."))
  .settings(
    skip in publish := true
  )
  .aggregate(
    `har-core`,
    `har-circe`,
    `har-html`,
    `har-codegen-http4s`
  )
  .dependsOn(
    `har-core`,
    `har-circe`,
    `har-html`
  )

lazy val `har-core` = (project in file("modules/core"))
  .settings(commonSettings: _*)
  .settings(
    name := "har-core"
  )

lazy val `har-circe` = (project in file("modules/circe"))
  .dependsOn(`har-core`)
  .settings(commonSettings: _*)
  .settings(
    name := "har-circe",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-parser" % "0.13.0",
      "io.circe" %% "circe-generic" % "0.13.0"
    )
  )

lazy val `har-html` = (project in file("modules/html"))
  .dependsOn(`har-core`)
  .settings(commonSettings: _*)
  .settings(
    name := "har-html",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "scalatags" % "0.9.3"
    )
  )

lazy val `har-codegen-http4s` = (project in file("modules/har-codegen-http4s"))
  .dependsOn(`har-core`)
  .settings(commonSettings: _*)
  .settings(
    name := "har-codegen-http4s",
    libraryDependencies ++= Seq(
      "org.scalameta" %% "scalameta" % "4.4.10",
      "org.scalameta" %% "scalafmt-core" % "2.7.5"
    )
  )

lazy val commonSettings = Seq(
  bintrayVcsUrl := Some("https://github.com/d10xa/har-scala.git"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)
