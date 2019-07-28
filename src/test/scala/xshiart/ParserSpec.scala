package xshiart

import org.specs2.mutable.Specification

import scala.io.{ Codec, Source }

class ParserSpec extends Specification {

  "Parser should work correctly" >> {
    val source = Source.fromResource("apache.log")(Codec.UTF8)
    val records = source.getLines().map(Parser.parseRecord).toList
    source.close()

    records must haveSize(1000)
    val parsed = records.flatten
    parsed must haveSize(1000)
    val (withUser, withoutUser) = parsed.partition(_.remoteUser.isDefined)
    withUser must haveSize(495)
    withoutUser must haveSize(505)
  }
}
