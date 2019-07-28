# Log Statistics Aggregator

This application is used to calculate number of unique visitor of an Apache server.
It consumes logs in apache combined format from standard input (can be piped) and store an aggregated data in MySQL.
Statistic is calculated for 5 seconds, 1 minute, 5 minutes, 30 minutes, 1 hour and 1 day time intervals.
Counters are stored in separate tables `five_seconds`, `one_minute`, `five_minutes`, `thirty_minutes`, `one_hour`, `one_day` respectively.

## Getting Started

Clone repo and run `sbt assembly`. You will get an assembled jar which you can run like `java -jar ...`

### Prerequisites

MySQL version 5.7.x must be used.
You will have to create an empty database first.
With which the application will work.

### Configuration

Application uses standard Typesafe config.
Here is the default one:
```hocon
db {
  host = "localhost"
  port = 3306
  name = "logstatistics"
  username = "root"
  password = "1234qwer"
}
```

### Installing

No special installation needed.
Just `sbt assembly`.

## Running the tests

`sbt test`

## Deployment

Application consumes logs from standart input stream so it can be "piped" with any log printer.
Also it can consume single log file like `cat logfile.log | java -jar ...`

## Built With

Sbt

## Versioning

[SemVer](http://semver.org/) is used for versioning. For the versions available, see the [tags on this repository](https://github.com/greatbalin/logstatistics/tags). 

## Authors

* **Artem Shilin**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
