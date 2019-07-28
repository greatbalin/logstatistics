package xshiart.db
import java.sql.Connection
import java.time.Instant

import xshiart.TimeUtils
import xshiart.model.TimeInterval

object OneDayTable extends AggregationTable {
  override val table: String = "one_day"

  override protected val connection: Connection = DB.connection

  override def interval(instant: Instant): TimeInterval = TimeUtils.get1DayInterval(instant)
}
