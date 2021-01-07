package com.spark.devproj.sparkDataframeAPIs.aggregations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import com.spark.devproj.sparkDataframeAPIs.aggregations.AggregationDemo.getClass
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object WindowingDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("SparkSchemaDemo"))
      .getOrCreate()

    val summaryDF = spark.read
      .format("parquet")
      .load(CommonUtils.getInputFilePath("summary.parquet"))

    val runningTotalWindow = Window.partitionBy("Country")
      .orderBy("WeekNumber")
      .rowsBetween(Window.unboundedPreceding, Window.currentRow)
    //      .rowsBetween(-2, Window.currentRow)

    summaryDF.withColumn("RunningTotal",
      sum("InvoiceValue").over(runningTotalWindow)).show()

    spark.stop()
  }
}
