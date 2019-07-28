import sbt._

object Dependencies {
  object Version {
    val specs2                          = "4.6.0"
    val logback                         = "1.2.3"
    val scalaLogging                    = "3.9.2"
    val config                          = "1.3.4"
    val mysql                           = "5.1.47"
    val ficus                           = "1.4.3"
  }

  object Compile {
    val logbackClassic      = "ch.qos.logback"              % "logback-classic"      % Version.logback
    val scalaLogging        = "com.typesafe.scala-logging" %% "scala-logging"        % Version.scalaLogging
    val config              = "com.typesafe"                % "config"               % Version.config
    val mysql               = "mysql"                       % "mysql-connector-java" % Version.mysql
    val ficus               = "com.iheart"                 %% "ficus"                % Version.ficus
  }

  object Test {
    val specs2        = "org.specs2"    %%  "specs2-core"           % Version.specs2      % "test"
    val specs2Mock    = "org.specs2"    %%  "specs2-mock"           % Version.specs2      % "test"
  }

  val testing = Seq(Test.specs2, Test.specs2Mock)

  val core = Seq(Compile.logbackClassic, Compile.config, Compile.scalaLogging, Compile.mysql, Compile.ficus)

  val all = core ++ testing
}