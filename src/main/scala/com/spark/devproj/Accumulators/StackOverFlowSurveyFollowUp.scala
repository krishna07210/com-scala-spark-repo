package com.spark.devproj.Accumulators

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object StackOverFlowSurveyFollowUp {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("StackOverFlowSurveyFollowUp"))

    val total = sparkContext.longAccumulator
    val missingSalMidPoint = sparkContext.longAccumulator
    val processedBytes = sparkContext.longAccumulator

    val responseRDD  = sparkContext
      .textFile(CommonUtils.getInputFilePath("2016-stack-overflow-survey-responses.csv"))
    val responseFromCanada = responseRDD.filter(response => {
      processedBytes.add(response.getBytes().length)
      val splits = response.split(CommonUtils.COMMA_DELIMITER,-1)
      total.add(1)
      if(splits(14).isEmpty){
        missingSalMidPoint.add(1)
      }
      splits(2)=="Canada"
    })

    println("Count of responses from Canada: " + responseFromCanada.count())
    println("Total count of responses: " + total.value)
    println("Number of bytes processed: " + processedBytes.value)
    println("Count of responses missing salary middle point: " + missingSalMidPoint.value)
  }
}
