package com.spark.devproj.sparkDataframeAPIs.udfs

import com.spark.devproj.sparkDataframeAPIs.examples.DataSinkDemo
import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object UDFDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(UDFDemo.getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("UDFDemo"))
      .getOrCreate()

    val surveyDF = spark.read
      .format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(CommonUtils.getInputFilePath("survey.csv"))
    surveyDF.show(10, truncate = false)

    import org.apache.spark.sql.functions._
    val parseGenderUDF = udf(parseGender(_: String): String)
    spark.catalog.listFunctions().filter(r => r.name == "parseGenderUDF").show()
    val surveyDF2 = surveyDF.withColumn("Gender", parseGenderUDF(col("Gender")))
    surveyDF2.show(10, truncate = false)

    spark.udf.register("parseGenderUDF", parseGender(_: String): String)
    spark.catalog.listFunctions().filter(r => r.name == "parseGenderUDF").show()
    val surveyDF3 = surveyDF.withColumn("Gender", expr("parseGenderUDF(Gender)"))
    surveyDF2.show(10, truncate = false)
  }

  def parseGender(s: String): String = {
    val femalePattern = "^f$|f.m|w.m".r
    val malePattern = "^m$|ma|m.l".r
    if (femalePattern.findFirstIn(s.toLowerCase).nonEmpty) "Female"
    else if (malePattern.findFirstIn(s.toLowerCase).nonEmpty) "Male"
    else "Unknown"
  }
}
