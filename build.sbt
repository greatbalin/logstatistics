
scalaVersion := "2.12.8"

name := "logstatistics"
organization := "xshiart"
version := "1.0.0"

libraryDependencies ++= Dependencies.all

assemblyMergeStrategy in assembly := {
  case "logback.xml" => MergeStrategy.first
  case "CHANGELOG.asciidoc" | "CHANGELOG.html" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
assemblyJarName in assembly := s"${name.value}-${version.value}-assembly.jar"

addArtifact(artifact in (Compile, assembly), assembly)