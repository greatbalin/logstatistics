package xshiart.params

import com.typesafe.config.{ Config, ConfigFactory }
import net.ceedubs.ficus.Ficus._

object AppConfig {

  lazy val config: Config = ConfigFactory.load()

  lazy val dbHost: String = config.as[String]("db.host")
  lazy val dbPort: Int = config.as[Int]("db.port")
  lazy val dbName: String = config.as[String]("db.name")
  lazy val dbUsername: String = config.as[String]("db.username")
  lazy val dbPassword: String = config.as[String]("db.password")

}
