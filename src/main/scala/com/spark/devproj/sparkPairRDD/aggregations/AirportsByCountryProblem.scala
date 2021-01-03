package com.spark.devproj.sparkPairRDD.aggregations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
 * Create a Spark program to read the airport data from in/airports.text,
 * output the the list of the names of the airports located in each country.
 *
 * Each row of the input file contains the following columns:
 * Airport ID, Name of airport, Main city served by airport, Country where airport is located,
 * IATA/FAA code,ICAO Code, Latitude, Longitude, Altitude, Timezone, DST, Timezone in Olson format
 *
 * Sample output:
 *
 * "Canada", List("Bagotville", "Montreal", "Coronation", ...)
 * "Norway" : List("Vigra", "Andenes", "Alta", "Bomoen", "Bronnoy",..)
 * "Papua New Guinea",  List("Goroka", "Madang", ...)
 */
object AirportsByCountryProblem {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("AirportsByCountryProblem"))

    val lines = sparkContext
      .textFile(CommonUtils.getInputFilePath("airports.text"))

    val countryAndAirportNameAndPair = lines.map(airport => (
      airport.split(CommonUtils.COMMA_DELIMITER)(3),
      airport.split(CommonUtils.COMMA_DELIMITER)(1)))
    val airportsByCountry = countryAndAirportNameAndPair.groupByKey()
    for ((country, airportName) <- airportsByCountry.collectAsMap())
      println(country + " : " + airportName.toList)
  }
}
