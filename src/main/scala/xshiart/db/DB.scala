package xshiart.db

import java.sql.{ Connection, DriverManager }

import com.typesafe.scalalogging.StrictLogging
import xshiart.model.LogRecord
import xshiart.params.AppConfig

object DB extends StrictLogging {

  val connection: Connection = DriverManager.getConnection(
    s"jdbc:mysql://${AppConfig.dbHost}:${AppConfig.dbPort}/${AppConfig.dbName}?user=${AppConfig.dbUsername}&password=${AppConfig.dbPassword}&useSSL=false")

  def initSchema(): Unit = {
    HistoryTable.initSchema()
    FiveSecondsTable.initSchema()
    OneMinuteTable.initSchema()
    FiveMinutesTable.initSchema()
    ThirtyMinutesTable.initSchema()
    OneHourTable.initSchema()
    OneDayTable.initSchema()
    logger.info("DB schema initialized")
  }

  def addRecord(record: LogRecord): Unit = {
    if (HistoryTable.insertNew(record) > 0) {
      FiveSecondsTable.addRecord(record)
      OneMinuteTable.addRecord(record)
      FiveMinutesTable.addRecord(record)
      ThirtyMinutesTable.addRecord(record)
      OneHourTable.addRecord(record)
      OneDayTable.addRecord(record)
      logger.debug("Aggregated tables updated for record [{}]", record)
    }
    logger.debug("Record processed [{}]", record)
  }

}
