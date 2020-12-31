package com.spark.devproj.spark.examples2

import org.apache.spark.sql.SparkSession
import com.spark.devproj.config.CommonUtils
import org.apache.spark.rdd.RDD

/**
 * Create a Spark program to read the airport data from in/airports.text,
 * find all the airports which are located in United States
 * and output the airport's name and the city's name to out/airports_in_usa.text.
 *
 * Each row of the input file contains the following columns:
 * Airport ID, Name of airport, Main city served by airport,
 * Country where airport is located, IATA/FAA code,
 * ICAO Code, Latitude, Longitude, Altitude, Timezone, DST, Timezone in Olson format
 *
 * Sample output:
 * "Putnam County Airport", "Greencastle"
 * "Dowagiac Municipal Airport", "Dowagiac"
 * ...
 */

object AirportsInUsaProblem {
  def main(args: Array[String]): Unit = {

    val sparkSession: SparkSession = SparkSession.builder()
      .appName("AirportsInUsaProblem")
      .master("local[3]")
      .getOrCreate()

    val airportsInUsa = sparkSession
      .sparkContext
      .textFile(CommonUtils.getExamples2InputFile("airports.text"))
      .filter(line => line.split(CommonUtils.COMMA_DELIMITER)(3).contains("\"United States\""))

    val airpirtNamesAndCityName = airportsInUsa.map(line => {
      val splits = line.split(CommonUtils.COMMA_DELIMITER)
      splits(1) + "," + splits(2)
    })
    airpirtNamesAndCityName.saveAsTextFile(CommonUtils.getExamples2OutputFile("airports-in-usa"))
    sparkSession.stop()
  }
}
