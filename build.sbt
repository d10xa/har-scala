ThisBuild / scalaVersion     := "2.13.3"
ThisBuild / version          := "0.1.1"
ThisBuild / organization     := "ru.d10xa"

lazy val har = project
  .in(file("."))
  .settings(
    skip in publish := true
  )
  .aggregate(
    `har-core`,
    `har-circe`
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

lazy val commonSettings = Seq(
  bintrayVcsUrl := Some("https://github.com/d10xa/har-scala.git"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)
