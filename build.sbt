name := """play-slick-crud"""
organization := "com.n2tech.mtrakr"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

  val slickVersion = "3.0.1"
  val silhouetteVersion = "5.0.0"
  val postgres = "42.1.4"
  val ficus = "1.4.2"
  val playVersion = "2.6.3"

  val devDependencies = Seq(
    "com.iheart" %% "ficus" % ficus,
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "com.typesafe.play" %% "play-json" % playVersion
  )

  val securityDependencies = Seq(
    "com.mohiva" %% "play-silhouette" % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-persistence" % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-password-bcrypt" % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-crypto-jca" % silhouetteVersion
  )

  val databaseDependencies = Seq(
    "org.postgresql" % "postgresql" % postgres,
    "com.typesafe.play" %% "play-slick" % slickVersion,
    "com.typesafe.play" %% "play-slick-evolutions" % slickVersion
  )

  val testDependencies = Seq(
    "com.mohiva" %% "play-silhouette-testkit" % silhouetteVersion % "test"
  )


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies ++= devDependencies
libraryDependencies ++= securityDependencies
libraryDependencies ++= databaseDependencies
libraryDependencies ++= testDependencies
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.n2tech.mtrakr.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.n2tech.mtrakr.binders._"
