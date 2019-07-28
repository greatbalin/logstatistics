package xshiart.db

import java.sql.{ PreparedStatement, Timestamp }
import java.time.Instant

import com.typesafe.scalalogging.StrictLogging
import xshiart.model.{ AggregationRow, LogRecord, TimeInterval }

import scala.annotation.tailrec
import scala.util.Try

trait AggregationTable extends Table with StrictLogging {

  protected lazy val select: PreparedStatement = connection.prepareStatement(s"SELECT interval_start, interval_end, value FROM $table WHERE interval_start <= ? AND interval_end > ?")
  protected lazy val update: PreparedStatement = connection.prepareStatement(s"UPDATE $table SET value = ? WHERE interval_start = ? AND interval_end = ? AND value = ?")
  protected lazy val insert: PreparedStatement = connection.prepareStatement(s"INSERT INTO $table (interval_start, interval_end, value) VALUES (?,?,?)")

  override def initSchema(): Unit = {
    val stm = connection.createStatement()
    stm.executeUpdate(
      s"CREATE TABLE IF NOT EXISTS $table (interval_start TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, interval_end TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, value INT NOT NULL DEFAULT 0, PRIMARY KEY(interval_start, interval_end))")
    stm.close()
  }

  def interval(instant: Instant): TimeInterval

  @tailrec
  final def addRecord(record: LogRecord): Unit = {
    selectExisting(record) match {
      case Some(row) => if (updateExisting(record)(row) == 0) addRecord(record) else ()
      case None => if (insertNew(record) == 0) addRecord(record) else ()
    }
  }

  def selectExisting(record: LogRecord): Option[AggregationRow] = {
    val dateTime = Timestamp.from(record.dateTime)
    select.clearParameters()
    select.setTimestamp(1, dateTime)
    select.setTimestamp(2, dateTime)
    val rs = select.executeQuery()
    val aggregationInterval = Iterator.continually(rs)
      .takeWhile(_.next())
      .map(r => AggregationRow(
        intervalStart = r.getTimestamp(1),
        intervalEnd = r.getTimestamp(2),
        value = r.getInt(3)))
      .take(1)
      .toList

    aggregationInterval.headOption
  }

  def updateExisting(record: LogRecord)(row: AggregationRow): Int = {
    update.clearParameters()
    val newValue = row.value + 1
    update.setInt(1, newValue)
    update.setTimestamp(2, row.intervalStart)
    update.setTimestamp(3, row.intervalEnd)
    update.setInt(4, row.value)
    update.executeUpdate()
  }

  def insertNew(record: LogRecord): Int = {
    val timeInterval = interval(record.dateTime)
    val start = Timestamp.from(timeInterval.start)
    val end = Timestamp.from(timeInterval.end)
    insert.clearParameters()
    insert.setTimestamp(1, start)
    insert.setTimestamp(2, end)
    insert.setInt(3, 1)
    Try(insert.executeUpdate()).getOrElse(0)
  }

}
