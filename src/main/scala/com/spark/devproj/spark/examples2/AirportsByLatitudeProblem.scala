package com.spark.devproj.spark.examples2

import com.spark.devproj.config.CommonUtils
import org.apache.spark.sql.SparkSession

/**
 * Create a Spark program to read the airport data from in/airports.text,
 * find all the airports whose latitude are bigger than 40.
 * Then output the airport's name and the airport's latitude to out/airports_by_latitude.text.
 *
 * Each row of the input file contains the following columns:
 * Airport ID, Name of airport, Main city served by airport, Country where airport is located,
 * IATA/FAA code, * ICAO Code, Latitude, Longitude, Altitude, Timezone, DST, Timezone in Olson format
 *
 * Sample output:
 * "St Anthony", 51.391944
 * "Tofino", 49.082222
 * ...
 */
object AirportsByLatitudeProblem {
  def main(args: Array[String]): Unit = {
    val sparkSession: SparkSession = SparkSession.builder()
      .appName("AirportsByLatitudeProblem")
      .master("local[3]")
      .getOrCreate()

    val airportsInUsa = sparkSession
      .sparkContext
      .textFile(CommonUtils.getExamples2InputFile("airports.text"))
      .filter(line => line.split(CommonUtils.COMMA_DELIMITER)(6)
        .replaceAll("\"", "")
        .toFloat > 40 & line.split(CommonUtils.COMMA_DELIMITER)(3).contains("\"United States\""))
      .map(line => {
        val splits = line.split(CommonUtils.COMMA_DELIMITER)
        splits(1) + "," + splits(6)
      })
      .saveAsTextFile(CommonUtils.getExamples2OutputFile("airports-in-usa-latitude"))
    sparkSession.stop()
  }
}
