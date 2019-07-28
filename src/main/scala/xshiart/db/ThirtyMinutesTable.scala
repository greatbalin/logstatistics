package xshiart.db
import java.sql.Connection
import java.time.Instant

import xshiart.TimeUtils
import xshiart.model.TimeInterval

object ThirtyMinutesTable extends AggregationTable {
  override val table: String = "thirty_minutes"

  override protected val connection: Connection = DB.connection

  override def interval(instant: Instant): TimeInterval = TimeUtils.get30MinutesInterval(instant)
}
