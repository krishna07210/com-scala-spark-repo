package com.spark.devproj.sparkDataframe.miscellaneous

import java.util.Properties

import com.spark.devproj.config.CommonUtils.inputFile
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.io.Source

/**
 * Input file Name :  survey_sample.csv
 */

object HelloSparkDataframe extends Serializable {
  @transient lazy val logger: Logger = Logger.getLogger(HelloSparkDataframe.getClass.getName);

  def main(args: Array[String]): Unit = {
    logger.info("Starting Hello Spark")
    val spark = SparkSession.builder()
      .config(getSparkAppConf)
      .getOrCreate();
    logger.info("spark.config = " + spark.conf.getAll.toString())

    val surveyDF: DataFrame = loadSurveyDF(spark, inputFile("survey_sample.csv"))
    val partitionedSurveyDF = surveyDF.repartition(2)
    val countDF = countByCountry(partitionedSurveyDF)
    countDF.collect().foreach(r=>
    logger.info("Country : "+r.getString(0) + " Count : "+r.getLong(1))
    )

    logger.info(countDF.collect().mkString("->"))
    scala.io.StdIn.readLine() //This is to hold the spark execution for local debugging http://localhost:4040/jobs/
    logger.info("Finished the Spark Execution")
    spark.stop();
  }

  def getSparkAppConf: SparkConf = {
    val sparkAppConf = new SparkConf
    val props = new Properties
    props.load(Source.fromFile("spark.conf").bufferedReader())
    props.forEach((k, v) => sparkAppConf.set(k.toString, v.toString))
    sparkAppConf
  }

  def countByCountry(surveyDF: DataFrame): DataFrame = {
    surveyDF.where("Age < 40")
      .select("Age", "Gender", "Country", "state")
      .groupBy("Country")
      .count()
  }

  def loadSurveyDF(spark: SparkSession, fileName: String): DataFrame = {
    spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(inputFile("survey_sample.csv"))
  }
}
