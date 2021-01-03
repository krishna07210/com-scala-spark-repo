package com.spark.devproj.sparkPairRDD.sorting

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import com.spark.devproj.sparkPairRDD.dataAnalysis.AvgCount
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object AverageHousePriceSolution {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("AverageHousePriceSolution"))

    val lines = sparkContext
      .textFile(CommonUtils.getInputFilePath("RealEstate.csv"))

    val cleanedLines = lines.filter(line => !line.contains("Bedrooms"))
    val housePricePairRdd = cleanedLines.map(
      line => (line.split(",")(3).toInt, AvgCount(1, line.split(",")(2).toDouble)))

    val housePriceTotal = housePricePairRdd.reduceByKey((x, y) => AvgCount(x.count + y.count, x.total + y.total))

    val housePriceAvg = housePriceTotal.mapValues(avgCount => avgCount.total / avgCount.count)

    val sortedHousePriceAvg = housePriceAvg.sortByKey()

    for ((bedrooms, avgPrice) <- sortedHousePriceAvg.collect()) println(bedrooms + " : " + avgPrice)
  }
}
