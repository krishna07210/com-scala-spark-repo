package com.spark.devproj.sparkPairRDD.dataAnalysis

import org.apache.log4j.{Level, Logger}
import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

/**
 * Create a Spark program to read the house data from in/RealEstate.csv,
 * output the average price for houses with different number of bedrooms.
 *
 * The houses dataset contains a collection of recent real estate listings in San Luis Obispo county 
 * and around it. 
 *
 * The dataset contains the following fields:
 *     1. MLS: Multiple listing service number for the house (unique ID).
 *     2. Location: city/town where the house is located. Most locations are in San Luis Obispo county and
 * northern Santa Barbara county (Santa Maria­Orcutt, Lompoc, Guadelupe, Los Alamos), but there
 * some out of area locations as well.
 *     3. Price: the most recent listing price of the house (in dollars).
 *     4. Bedrooms: number of bedrooms.
 *     5. Bathrooms: number of bathrooms.
 *     6. Size: size of the house in square feet.
 *     7. Price/SQ.ft: price of the house per square foot.
 *     8. Status: type of sale. Thee types are represented in the dataset: Short Sale, Foreclosure and Regular.
 *
 * Each field is comma separated.
 *
 * Sample output:
 *
 * (3, 325000)
 * (1, 266356)
 * (2, 325000)
 * ...
 *
 * 3, 1 and 2 mean the number of bedrooms. 325000 means the average price of houses with 3 bedrooms
 * is 325000.
 */

case class AvgCount(count: Int, total: Double)

object AverageHousePriceProblem {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("AverageHousePriceProblem"))

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
