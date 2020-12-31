package com.spark.devproj.sparkPairRDD.transformations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

/**
 * Create a Spark program to read the airport data from in/airports.text;
 * generate a pair RDD with airport name being the key and country name being the value.
 * Then remove all the airports which are located in United States and output the pair RDD to out/airports_not_in_usa_pair_rdd.text
 *
 * Each row of the input file contains the following columns:
 * Airport ID, Name of airport, Main city served by airport, Country where airport is located,
 * IATA/FAA code, ICAO Code, Latitude, Longitude, Altitude, Timezone, DST, Timezone in Olson format
 *
 * Sample output:
 *
 * ("Kamloops", "Canada")
 * ("Wewak Intl", "Papua New Guinea")
 */
object AirportsNotInUsaProblem {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("AirportsNotInUsaProblem"))

    val airportsRDD = sparkContext
      .textFile(CommonUtils.getInputFilePath("airports.text"))

    val airportsPairRDD = airportsRDD.map(line => (line.split(CommonUtils.COMMA_DELIMITER)(1),
      line.split(CommonUtils.COMMA_DELIMITER)(3)))
    val airportsNotInUsa = airportsPairRDD.filter(keyValue => !keyValue._2.equals("\"United States\""))
    sparkContext.stop()
  }
}
