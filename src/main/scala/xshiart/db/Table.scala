package xshiart.db

import java.sql.Connection

trait Table {
  val table: String

  protected val connection: Connection

  def initSchema(): Unit
}
