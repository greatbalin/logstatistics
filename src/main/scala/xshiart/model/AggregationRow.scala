package xshiart.model

import java.sql.Timestamp

case class AggregationRow(
  intervalStart: Timestamp,
  intervalEnd: Timestamp,
  value: Int = 0)