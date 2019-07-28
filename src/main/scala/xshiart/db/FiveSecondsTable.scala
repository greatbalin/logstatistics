package xshiart.db
import java.sql.Connection
import java.time.Instant

import xshiart.TimeUtils
import xshiart.model.TimeInterval

object FiveSecondsTable extends AggregationTable {
  override val table: String = "five_seconds"

  override protected val connection: Connection = DB.connection

  override def interval(instant: Instant): TimeInterval = TimeUtils.get5SecondsInterval(instant)
}
