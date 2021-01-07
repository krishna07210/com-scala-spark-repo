package com.spark.devproj.sparkDataframeAPIs.aggregations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._


object RankingDemoApp {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("SparkSchemaDemo"))
      .getOrCreate()

    val summaryDF = spark.read
      .format("parquet")
      .load(CommonUtils.getInputFilePath("summary.parquet"))
    summaryDF.sort("Country", "WeekNumber").show()

    val rankWindow = Window.partitionBy("Country")
      .orderBy(col("InvoiceValue").desc)
      .rowsBetween(Window.unboundedPreceding, Window.currentRow)

    summaryDF.withColumn("Rank", dense_rank().over(rankWindow))
      .where(col("Rank") === 1)
      .sort("Country", "WeekNumber")
      .show()

    spark.stop()
  }
}
