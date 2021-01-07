package com.spark.devproj.sparkDataframeAPIs.joins

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import com.spark.devproj.sparkDataframeAPIs.joins.BroadcastJoinDemoApp.getClass
import org.apache.log4j.Logger
import org.apache.spark.sql.{SaveMode, SparkSession}

object BucketJoinDemoApp {
  def main(args: Array[String]): Unit = {
    //    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("BroadcastJoinDemoApp"))
      .getOrCreate()

    val df1 = spark.read.json(CommonUtils.getInputFilePath("joins/d1/"))
    val df2 = spark.read.json(CommonUtils.getInputFilePath("joins/d2/"))
    //    df1.show()
    //    df2.show()

    import spark.sql
    sql("CREATE DATABASE IF NOT EXISTS MY_DB")
    sql("USE MY_DB")

    df1.coalesce(1).write
      .bucketBy(3, "id")
      .mode(SaveMode.Overwrite)
      .saveAsTable("MY_DB.flight_data1")

    df2.coalesce(1).write
      .bucketBy(3, "id")
      .mode(SaveMode.Overwrite)
      .saveAsTable("MY_DB.flight_data2")

    val df3 = spark.read.table("MY_DB.flight_data1")
    val df4 = spark.read.table("MY_DB.flight_data2")

    val joinExpr = df3.col("id") === df4.col("id")

    /** Spark will automatically do the Broadcast join if the one of the Dataset is small,
     * so we manually disable it for testing.
     */
    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)
    val joinDF = df3.join(df4, joinExpr, "inner")
    joinDF.foreach(_ => ())
    scala.io.StdIn.readLine()


  }
}
