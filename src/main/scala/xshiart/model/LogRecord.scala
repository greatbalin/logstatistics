package xshiart.model

import java.security.MessageDigest
import java.time.Instant

import javax.xml.bind.DatatypeConverter

case class LogRecord(
  clientIpAddress: String, // should be an ip address, but may also be the hostname if hostname-lookups are enabled
  clientIdentity: Option[String] = None, // typically `-`
  remoteUser: Option[String] = None, // can be `-`
  dateTime: Instant, // log record timestamp
  request: String, // `GET / POST ...`
  httpStatusCode: Int, // 200, 404, etc.
  bytesSent: Option[Long] = None, // may be `-`
  referer: Option[String] = None, // where the visitor came from
  userAgent: Option[String] = None // long string to represent the browser and OS
) {

  lazy val recordIdentity: String = {
    // MD5 hashing is used to normilaze/align identity string.
    // It will always be 32 characters.
    val md: MessageDigest = MessageDigest.getInstance("MD5")
    // if user is not set then use IP and userAgent fields as unique identity of a visitor
    val fields = remoteUser.getOrElse(s"$clientIpAddress:${userAgent.getOrElse("")}")
    md.update(fields.getBytes("UTF-8"))
    val digest = md.digest()
    DatatypeConverter.printHexBinary(digest).toUpperCase
  }
}
