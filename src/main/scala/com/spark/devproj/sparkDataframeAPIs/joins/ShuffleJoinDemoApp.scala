package com.spark.devproj.sparkDataframeAPIs.joins

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object ShuffleJoinDemoApp {
  def main(args: Array[String]): Unit = {
//    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("JoinInternalsDemoApp"))
      .getOrCreate()

    val flightTimeDF1 = spark.read.json(CommonUtils.getInputFilePath("joins/d1/"))
    val flightTimeDF2 = spark.read.json(CommonUtils.getInputFilePath("joins/d2/"))
    spark.conf.set("spark.sql.shuffle.partitions", 3)
    val joinExpr = flightTimeDF1.col("id") === flightTimeDF2.col("id")
    val joinDF = flightTimeDF1.join(flightTimeDF2, joinExpr, "inner")
    joinDF.foreach(_ => ()) // dummy action to execute the joins
    scala.io.StdIn.readLine()

  }
}
