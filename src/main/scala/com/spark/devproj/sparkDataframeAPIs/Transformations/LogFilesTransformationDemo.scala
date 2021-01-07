package com.spark.devproj.sparkDataframeAPIs.Transformations

import com.spark.devproj.CaseClasses.ApacheLogRecord
import com.spark.devproj.sparkDataframeAPIs.examples.DataSinkDemo
import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object LogFilesTransformationDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(LogFilesTransformationDemo.getClass.getName);
    val sparkSession = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("LogFilesTransformationDemo"))
      .getOrCreate()

    val logsDF = sparkSession
      .read
      .textFile(CommonUtils.getInputFilePath("apache_logs.txt")).toDF()

    val myRegex = """^(\S+) (\S+) (\S+) \[([\w:/]+\s[+\-]\d{4})\] "(\S+) (\S+) (\S+)" (\d{3}) (\S+) "(\S+)" "([^"]*)"""".r

    import sparkSession.implicits._
    val logDF = logsDF.map(row =>
      row.getString(0) match {
        case myRegex(ip, client, user, date, cmd, request, proto, status, bytes, referrer, userAgent) =>
          ApacheLogRecord(ip, date, request, referrer)
      }
    )

    import org.apache.spark.sql.functions._
    logDF
      .where("trim(referrer) != '-'")
      .withColumn("referrer", substring_index($"referrer", "/", 3))
      .groupBy("referrer").count().show(truncate = false)

    sparkSession.stop()


  }
}
