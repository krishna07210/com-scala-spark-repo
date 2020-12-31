package com.spark.devproj.sparkDatasets.miscellaneous

import java.util.Properties

import com.spark.devproj.config.CommonUtils
import com.spark.devproj.sparkDataframe.miscellaneous.HelloSparkDataframe.logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.{Dataset, Row, SparkSession}

import scala.io.Source


case class SurveyRecord(Age: Int, Gender: String, Country: String, state: String)


object HelloDataset {
  def main(args: Array[String]): Unit = {
    logger.info("Starting Hello Spark")
    val spark = SparkSession.builder()
      .config(getSparkAppConf)
      .getOrCreate();
    logger.info("spark.config = " + spark.conf.getAll.toString())

    val rawDF: Dataset[Row] = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(CommonUtils.getInputFilePath("survey_sample.csv"))

    import spark.implicits._
    //Here we are converting the Row object to specific Object format
    val surveyDS: Dataset[SurveyRecord] = rawDF.select("Age", "Gender", "Country", "state").as[SurveyRecord]

    // Type Safety
    val filteredDS = surveyDS.filter(row => row.Age < 40)
    // Generic type
    val filteredDF = surveyDS.filter("age < 40")

    //Type safe GroupBy
    val countDS = filteredDS.groupByKey(r => r.Country).count()
    //Runtime Group by
    val countDF = filteredDF.groupBy("Country").count()

  }

  def getSparkAppConf: SparkConf = {
    val sparkAppConf = new SparkConf
    val props = new Properties
    props.load(Source.fromFile("spark.conf").bufferedReader())
    props.forEach((k, v) => sparkAppConf.set(k.toString, v.toString))
    sparkAppConf
  }

}
