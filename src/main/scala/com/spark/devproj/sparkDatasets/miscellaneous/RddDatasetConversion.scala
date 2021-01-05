package com.spark.devproj.sparkDatasets.miscellaneous

import java.util.Optional

import com.spark.devproj.CaseClasses.Response
import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object RddDatasetConversion {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val conf = new SparkConf().setAppName("RddDatasetConversion").setMaster("local[1]")
    val sparkSession = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()
    val sparkContext = new SparkContext(conf)

    val lines = sparkContext.textFile(CommonUtils.getInputFilePath("2016-stack-overflow-survey-responses.csv"))
    val responseRDD = lines
      .filter(line => !line.split(CommonUtils.COMMA_DELIMITER, -1)(2).equals("country"))
      .map(line => {
        val splits = line.split(CommonUtils.COMMA_DELIMITER, -1)
        Response(splits(2), toInt(splits(6)), splits(9), toInt(splits(14)))
      })
    import sparkSession.implicits._
    val responseDataset = responseRDD.toDS()
    System.out.println("=== Print out schema ===")
    responseDataset.printSchema()

    System.out.println("=== Print 20 records of responses table ===")
    responseDataset.show(20)

    for (response <- responseDataset.rdd.collect()) println(response)

    sparkContext.stop()
    sparkSession.stop()
  }

  def toInt(split: String): Option[Double] = {
    if (split.isEmpty) None else Some(split.toDouble)
  }
}
