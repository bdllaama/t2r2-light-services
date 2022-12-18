organization := "com.llaama.t2r2"

scalaVersion := "2.13.8"
name := "t2r2-services"

enablePlugins(KalixPlugin, JavaAppPackaging, DockerPlugin)

dockerBaseImage := "docker.io/library/adoptopenjdk:11-jre-hotspot"

dockerUpdateLatest := true

dockerBuildCommand := {
  if (sys.props("os.arch") != "amd64") {
    // use buildx with platform to build supported amd64 images on other CPU architectures
    // this may require that you have first run 'docker buildx create' to set docker buildx up
    dockerExecCommand.value ++ Seq("buildx", "build", "--platform=linux/amd64", "--load") ++ dockerBuildOptions.value :+ "."
  } else dockerBuildCommand.value
}

ThisBuild / dynverSeparator := "-"
run / fork := true
run / envVars += ("HOST", "0.0.0.0")

Compile / scalacOptions ++= Seq(
  "-target:11",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlog-reflective-calls",
  "-Xlint")

Compile / javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-parameters" // for Jackson
)

Test / parallelExecution := false
Test / testOptions += Tests.Argument("-oDF")
Test / logBuffered := false

Compile / run := {
  // needed for the proxy to access the user function on all platforms
  sys.props += "kalix.user-function-interface" -> "0.0.0.0"
  (Compile / run).evaluated
}

//run / fork := false
Global / cancelable := false // ctrl-c

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.7" % Test,
  "org.scalamock" %% "scalamock" % "5.2.0" % Test
)

dockerRepository := Some("docker.io")

// dockerAlias := DockerAlias(dockerRepository.value, Some("palaamon"), packageName.value, Some(version.value))
dockerAlias := DockerAlias(dockerRepository.value, Some("llaamasas"), "t2r2-services", Some(version.value))

