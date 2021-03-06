package com.spark.devproj.sparkSQL.miscellaneous

import com.spark.devproj.config.CommonUtils
import com.spark.devproj.sparkDataframeAPIs.aggregations.AggregationDemo.getClass
import org.apache.log4j.Logger
import org.apache.spark.sql.{Dataset, Row, SparkSession}

object HelloSparkSQL extends Serializable {

  @transient lazy val logger: Logger = Logger.getLogger(getClass.getName);
  def main(args: Array[String]): Unit = {
    logger.info("Starting Hello Spark")
    val spark = SparkSession.builder()
      .appName("HelloSparkSQL")
      .master("local[3]")
      .getOrCreate();


    val surveyDF: Dataset[Row] = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(CommonUtils.getInputFilePath("survey_sample.csv"))

    surveyDF.createOrReplaceTempView("survey_tb1")
    spark.sql("select Country, count(1) as count from survey_tb1 where Age<40 group by Country")
    spark.stop()
  }
}
