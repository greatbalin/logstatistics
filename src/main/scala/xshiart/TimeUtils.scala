package xshiart

import java.time.temporal.ChronoUnit
import java.time.{ Instant, ZoneOffset }

import xshiart.model.TimeInterval

object TimeUtils {

  def get5SecondsInterval(inst: Instant): TimeInterval = {
    val seconds: Int = inst.atZone(ZoneOffset.UTC).getSecond
    val lowerSeconds: Int = (seconds / 5) * 5
    val higherSeconds: Int = lowerSeconds + 5
    val minutes = inst.truncatedTo(ChronoUnit.MINUTES)

    TimeInterval(minutes.plusSeconds(lowerSeconds), minutes.plusSeconds(higherSeconds))
  }

  def get1MinuteInterval(inst: Instant): TimeInterval = {
    val minutes = inst.truncatedTo(ChronoUnit.MINUTES)

    TimeInterval(minutes, minutes.plus(1, ChronoUnit.MINUTES))
  }

  def get5MinutesInterval(inst: Instant): TimeInterval = {
    val minutes: Int = inst.atZone(ZoneOffset.UTC).getMinute
    val lowerMinutes: Int = (minutes / 5) * 5
    val higherMinutes: Int = lowerMinutes + 5
    val hours = inst.truncatedTo(ChronoUnit.HOURS)

    TimeInterval(hours.plus(lowerMinutes, ChronoUnit.MINUTES), hours.plus(higherMinutes, ChronoUnit.MINUTES))
  }

  def get30MinutesInterval(inst: Instant): TimeInterval = {
    val minutes: Int = inst.atZone(ZoneOffset.UTC).getMinute
    val lowerMinutes: Int = (minutes / 30) * 30
    val higherMinutes: Int = lowerMinutes + 30
    val hours = inst.truncatedTo(ChronoUnit.HOURS)

    TimeInterval(hours.plus(lowerMinutes, ChronoUnit.MINUTES), hours.plus(higherMinutes, ChronoUnit.MINUTES))
  }

  def get1HourInterval(inst: Instant): TimeInterval = {
    val hours = inst.truncatedTo(ChronoUnit.HOURS)

    TimeInterval(hours, hours.plus(1, ChronoUnit.HOURS))
  }

  def get1DayInterval(inst: Instant): TimeInterval = {
    val days = inst.truncatedTo(ChronoUnit.DAYS)

    TimeInterval(days, days.plus(1, ChronoUnit.DAYS))
  }
}
