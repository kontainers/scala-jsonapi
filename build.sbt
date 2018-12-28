organization := "io.kontainers"

name := "scala-jsonapi"

scalaVersion := "2.12.8"

crossScalaVersions := Seq("2.11.12", scalaVersion.value, "2.13.0-M5")

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

libraryDependencies ++= {

  Seq(
    "io.spray"          %% "spray-json"     % "1.3.5"         % "provided",
    "com.typesafe.play" %% "play-json"      % "2.7.0-RC2"     % "provided",
    "org.scalatest"     %% "scalatest"      % "3.0.6-SNAP5"   % Test
  )
}

lazy val root = (project in file("."))
  .enablePlugins(ScoverageSbtPlugin)

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))

coverageMinimum := 80

coverageFailOnMinimum := true

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

releasePublishArtifactsAction := PgpKeys.publishSigned.value

pomExtra := (
  <url>https://github.com/kontainers/scala-jsonapi</url>
  <licenses>
    <license>
      <name>MIT</name>
      <url>http://opensource.org/licenses/MIT</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:kontainers/scala-jsonapi.git</url>
    <connection>scm:git:git@github.com:kontainers/scala-jsonapi.git</connection>
  </scm>
  <developers>
    <developer>
      <id>zmeda</id>
      <name>Boris Malensek</name>
      <url>https://github.com/zmeda</url>
    </developer>
    <developer>
      <id>pjfanning</id>
      <name>PJ Fanning</name>
      <url>https://github.com/pjfanning</url>
    </developer>
  </developers>)
