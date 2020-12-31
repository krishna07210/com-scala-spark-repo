package com.spark.devproj.sparkPairRDD.dataAnalysis

import org.apache.log4j.{Level, Logger}
import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

case class AvgCount(count: Int, total: Double)

object AverageLocationWisePriceProblem {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("RealEstateDataAggregation"))

    val lines = sparkContext
      .textFile(CommonUtils.getInputFilePath("RealEstate.csv"))
    val cleanedLines = lines.filter(rec => isNotHeader(rec))
    val housePricePairRdd = cleanedLines.map(line => (line.split(",")(3).toString,
      (1, line.split(",")(2).toDouble)))
    val housePriceTotal = housePricePairRdd.reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))

    println("housePriceTotal: ")
    for ((bedroom, total) <- housePriceTotal.collect()) println(bedroom + " : " + total)
    val housePriceAvg = housePriceTotal.mapValues(avgCount => avgCount._2 / avgCount._1)
    println("housePriceAvg: ")
    for ((bedroom, avg) <- housePriceAvg.collect()) println(bedroom + " : " + avg)
    sparkContext.stop()
  }

  def isNotHeader(record: String): Boolean = {
    record.split(",")(3).toString != "Bedrooms" && record.split(",")(2) != "Price"
  }
}
