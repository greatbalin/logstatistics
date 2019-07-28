package xshiart

import xshiart.db.DB

import scala.io.{ Codec, Source }

object Main extends App {

  DB.initSchema()

  val input = Source.fromInputStream(System.in)(Codec.UTF8)

  input.getLines().foreach(line => Parser.parseRecord(line).foreach(DB.addRecord))

  input.close()

  DB.connection.close()
}