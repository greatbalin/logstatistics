package xshiart.db
import java.sql.Connection
import java.time.Instant

import xshiart.TimeUtils
import xshiart.model.TimeInterval

object OneHourTable extends AggregationTable {
  override val table: String = "one_hour"

  override protected val connection: Connection = DB.connection

  override def interval(instant: Instant): TimeInterval = TimeUtils.get1HourInterval(instant)
}
