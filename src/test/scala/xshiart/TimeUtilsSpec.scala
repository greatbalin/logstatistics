package xshiart

import java.time.Instant

import org.specs2.matcher.DataTables
import org.specs2.mutable.Specification
import xshiart.model.TimeInterval

class TimeUtilsSpec extends Specification with DataTables {

  "TimeUtils should correctly calculate time intervals" >> {
    "for 5 seconds" >> {
      "instant" || "left" || "right" |
        Instant.parse("2019-01-01T17:21:05.00Z") !! Instant.parse("2019-01-01T17:21:05.00Z") !! Instant.parse("2019-01-01T17:21:10.00Z") |
        Instant.parse("2019-01-01T17:21:06.00Z") !! Instant.parse("2019-01-01T17:21:05.00Z") !! Instant.parse("2019-01-01T17:21:10.00Z") |
        Instant.parse("2019-01-01T17:21:10.00Z") !! Instant.parse("2019-01-01T17:21:10.00Z") !! Instant.parse("2019-01-01T17:21:15.00Z") |> {
          (instant, left, right) =>
            {
              TimeUtils.get5SecondsInterval(instant) must_== TimeInterval(left, right)
            }
        }
    }

    "for 1 minute" >> {
      "instant" || "left" || "right" |
        Instant.parse("2019-01-01T17:21:00.00Z") !! Instant.parse("2019-01-01T17:21:00.00Z") !! Instant.parse("2019-01-01T17:22:00.00Z") |
        Instant.parse("2019-01-01T17:21:55.00Z") !! Instant.parse("2019-01-01T17:21:00.00Z") !! Instant.parse("2019-01-01T17:22:00.00Z") |
        Instant.parse("2019-01-01T17:22:00.00Z") !! Instant.parse("2019-01-01T17:22:00.00Z") !! Instant.parse("2019-01-01T17:23:00.00Z") |> {
          (instant, left, right) =>
            {
              TimeUtils.get1MinuteInterval(instant) must_== TimeInterval(left, right)
            }
        }
    }

    "for 5 minutes" >> {
      "instant" || "left" || "right" |
        Instant.parse("2019-01-01T17:20:00.00Z") !! Instant.parse("2019-01-01T17:20:00.00Z") !! Instant.parse("2019-01-01T17:25:00.00Z") |
        Instant.parse("2019-01-01T17:21:06.00Z") !! Instant.parse("2019-01-01T17:20:00.00Z") !! Instant.parse("2019-01-01T17:25:00.00Z") |
        Instant.parse("2019-01-01T17:25:00.00Z") !! Instant.parse("2019-01-01T17:25:00.00Z") !! Instant.parse("2019-01-01T17:30:00.00Z") |> {
          (instant, left, right) =>
            {
              TimeUtils.get5MinutesInterval(instant) must_== TimeInterval(left, right)
            }
        }
    }

    "for 30 seconds" >> {
      "instant" || "left" || "right" |
        Instant.parse("2019-01-01T17:00:00.00Z") !! Instant.parse("2019-01-01T17:00:00.00Z") !! Instant.parse("2019-01-01T17:30:00.00Z") |
        Instant.parse("2019-01-01T17:21:06.00Z") !! Instant.parse("2019-01-01T17:00:00.00Z") !! Instant.parse("2019-01-01T17:30:00.00Z") |
        Instant.parse("2019-01-01T17:30:00.00Z") !! Instant.parse("2019-01-01T17:30:00.00Z") !! Instant.parse("2019-01-01T18:00:00.00Z") |> {
          (instant, left, right) =>
            {
              TimeUtils.get30MinutesInterval(instant) must_== TimeInterval(left, right)
            }
        }
    }

    "for 1 hour" >> {
      "instant" || "left" || "right" |
        Instant.parse("2019-01-01T17:00:00.00Z") !! Instant.parse("2019-01-01T17:00:00.00Z") !! Instant.parse("2019-01-01T18:00:00.00Z") |
        Instant.parse("2019-01-01T17:21:06.00Z") !! Instant.parse("2019-01-01T17:00:00.00Z") !! Instant.parse("2019-01-01T18:00:00.00Z") |
        Instant.parse("2019-01-01T18:00:00.00Z") !! Instant.parse("2019-01-01T18:00:00.00Z") !! Instant.parse("2019-01-01T19:00:00.00Z") |> {
          (instant, left, right) =>
            {
              TimeUtils.get1HourInterval(instant) must_== TimeInterval(left, right)
            }
        }
    }

    "for 1 day" >> {
      "instant" || "left" || "right" |
        Instant.parse("2019-01-01T00:00:00.00Z") !! Instant.parse("2019-01-01T00:00:00.00Z") !! Instant.parse("2019-01-02T00:00:00.00Z") |
        Instant.parse("2019-01-01T17:21:06.00Z") !! Instant.parse("2019-01-01T00:00:00.00Z") !! Instant.parse("2019-01-02T00:00:00.00Z") |
        Instant.parse("2019-01-02T00:00:00.00Z") !! Instant.parse("2019-01-02T00:00:00.00Z") !! Instant.parse("2019-01-03T00:00:00.00Z") |> {
          (instant, left, right) =>
            {
              TimeUtils.get1DayInterval(instant) must_== TimeInterval(left, right)
            }
        }
    }
  }
}
