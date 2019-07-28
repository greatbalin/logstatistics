package xshiart.db
import java.sql.Connection
import java.time.Instant

import xshiart.TimeUtils
import xshiart.model.TimeInterval

object FiveMinutesTable extends AggregationTable {
  val table = "five_minutes"

  override protected val connection: Connection = DB.connection

  override def interval(instant: Instant): TimeInterval = TimeUtils.get5MinutesInterval(instant)
}
