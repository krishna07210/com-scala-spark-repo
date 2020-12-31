package com.spark.devproj.com.spark.devproj.spark.examples1

import com.spark.devproj.config.CommonUtils.inputFile
import com.spark.devproj.spark.examples1.HelloSpark.{countByCountry, loadSurveyDF}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}

import scala.collection.mutable

class HelloSparkTest extends FunSuite with BeforeAndAfterAll {

  @transient var spark: SparkSession = _

  override def beforeAll(): Unit = {
    spark = SparkSession.builder()
      .appName("HelloSparkTest")
      .master("local[3]")
      .getOrCreate()
  }

  override def afterAll(): Unit = {
    spark.stop()
  }

  test("Data file Loading") {
    val surveyDF: DataFrame = loadSurveyDF(spark, inputFile("survey_sample.csv"))
    val rCount: Long = surveyDF.count()
    assert(rCount == 9, "record count should be 9")
  }

  test("Count by Country"){
    val surveyDF: DataFrame = loadSurveyDF(spark, inputFile("survey_sample.csv"))
    val countDF: DataFrame = countByCountry(surveyDF)
    val countryMap = new mutable.HashMap[String,Long]
    countDF.collect().foreach(rec => countryMap.put(rec.getString(0),rec.getLong(1)))
    assert(countryMap("United States") == 4, ":- Count for Unites States should be 6")
    assert(countryMap("Canada") == 2, ":- Count for Canada should be 2")
    assert(countryMap("United Kingdom") == 1, ":- Count for Unites Kingdom should be 1")
  }


}
