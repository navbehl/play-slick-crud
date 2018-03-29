name := """play-slick-crud"""
organization := "com.n2tech.mtrakr"

version := "1.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.5"

resolvers += Resolver.jcenterRepo

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

  val slickVersion = "3.0.3"
  val silhouetteVersion = "5.0.3"
  val postgres = "42.2.2"
  val ficus = "1.4.3"
  val playVersion = "2.6.7"
  val guiceVersion = "4.2.0"

  val devDependencies = Seq(
    "com.iheart" %% "ficus" % ficus,
    "net.codingwell" %% "scala-guice" % guiceVersion,
    "com.typesafe.play" %% "play-json" % playVersion
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
libraryDependencies ++= databaseDependencies
libraryDependencies ++= testDependencies
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.n2tech.mtrakr.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.n2tech.mtrakr.binders._"
