package xshiart.model

import java.sql.Timestamp

case class HistoryRow(
  logDatetime: Timestamp,
  visitorIdentity: String = "")
