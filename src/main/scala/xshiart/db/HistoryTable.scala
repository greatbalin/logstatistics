package xshiart.db
import java.sql.{ Connection, PreparedStatement, Timestamp }

import com.typesafe.scalalogging.StrictLogging
import xshiart.TimeUtils
import xshiart.model.LogRecord

import scala.util.Try

object HistoryTable extends Table with StrictLogging {
  override val table: String = "log_history"

  override protected val connection: Connection = DB.connection

  private lazy val insert: PreparedStatement = connection.prepareStatement(s"INSERT INTO $table (log_datetime, visitor_identity) VALUES (?,?)")

  override def initSchema(): Unit = {
    val stm = connection.createStatement()
    stm.executeUpdate(
      s"CREATE TABLE IF NOT EXISTS $table (log_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, visitor_identity VARCHAR(32) NOT NULL, PRIMARY KEY(log_datetime, visitor_identity))")
    stm.close()
  }

  def insertNew(record: LogRecord): Int = {
    // Need to align log record issue time so all records from the same (5 seconds) interval will have the same time inside of this interval.
    // This is needed to avoid counting requests from the same user more than once for the same interval.
    // Uniqueness is preserved by the primary key constraint.
    val smallestInterval = TimeUtils.get5SecondsInterval(record.dateTime)
    val normalizedTime = smallestInterval.start.plusSeconds(1)
    val datetime = Timestamp.from(normalizedTime)
    insert.clearParameters()
    insert.setTimestamp(1, datetime)
    insert.setString(2, record.recordIdentity)
    Try(insert.executeUpdate()).getOrElse(0)
  }

}
