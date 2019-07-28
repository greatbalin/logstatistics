package xshiart

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.{ Matcher, Pattern }

import com.typesafe.scalalogging.StrictLogging
import xshiart.model.LogRecord

import scala.util.{ Failure, Success, Try }

object Parser extends StrictLogging {

  val dateFormat = new SimpleDateFormat("[dd/MM/yyyy:HH:mm:ss Z]", Locale.ENGLISH)

  private val ddd = "\\d{1,3}" // at least 1 but not more than 3 times (possessive)
  private val ip = s"($ddd\\.$ddd\\.$ddd\\.$ddd)?" // like `123.456.7.89`
  private val client = "(\\S+)" // '\S' is 'non-whitespace character'
  private val user = "(\\S+)"
  private val dateTime = "(\\[.+?\\])" // like `[21/07/2009:02:48:13 -0700]`
  private val request = "\"(.*?)\"" // any number of any character, reluctant
  private val status = "(\\d{3})"
  private val bytes = "(\\S+)" // this can be a "-"
  private val referer = "\"(.*?)\""
  private val agent = "\"(.*?)\""
  private val regex = s"$ip $client $user $dateTime $request $status $bytes $referer $agent"

  private val fullPattern = Pattern.compile(regex)

  def parseRecord(record: String): Option[LogRecord] = {
    val matcher = fullPattern.matcher(record)
    if (matcher.find) {
      val rec = buildAccessLogRecord(matcher)
      rec match {
        case Success(value) =>
          Some(value)
        case Failure(exception) =>
          logger.warn(s"Unable to parse log record [$record]", exception)
          None
      }
    } else {
      logger.warn("Log record doesn't match expected pattern! [{}]", record)
      None
    }
  }

  private def buildAccessLogRecord(matcher: Matcher) = {
    for {
      dt <- Try(dateFormat.parse(matcher.group(4)))
      statusCode <- Try(matcher.group(6).toInt)
    } yield {
      LogRecord(
        clientIpAddress = matcher.group(1),
        clientIdentity = Option(matcher.group(2)).filterNot(_ == "-"),
        remoteUser = Option(matcher.group(3)).filterNot(_ == "-"),
        dateTime = dt.toInstant,
        request = matcher.group(5),
        httpStatusCode = statusCode,
        bytesSent = Option(matcher.group(7)).filterNot(_ == "-").map(_.toLong),
        referer = Option(matcher.group(8)).filter(_.nonEmpty),
        userAgent = Option(matcher.group(9)).filter(_.nonEmpty))
    }
  }
}
