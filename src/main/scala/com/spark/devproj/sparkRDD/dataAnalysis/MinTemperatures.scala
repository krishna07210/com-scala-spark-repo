package com.spark.devproj.sparkRDD.dataAnalysis

import com.spark.devproj.config.{CommonUtils, SparkConfiguration}
import org.apache.spark.SparkContext
import scala.math.min

/**
 * Created by krish on 14-04-2020.
 * Find the minimum temperature by weather station
 */
object MinTemperatures {

  def parseLine(line: String) = {
    val fields = line.toString().split(",")
    val stationId = fields(0)
    val entryType = fields(2)
    val temperature = fields(3).toFloat * 0.1f * (9.0f / 5.0f) + 32.0f
    (stationId, entryType, temperature)
  }

  def main(args: Array[String]) {
    val sparkConfig = new SparkConfiguration();
    val sc: SparkContext = sparkConfig.localConfig("local", "MinTemperatures")
    val lines = sc.textFile(CommonUtils.inputFile("1800.csv"))
    // Convert to (stationID, entryType, temperature) tuples
    val parsedLines = lines.map(parseLine)
    // Filter out all but TMIN entries
    val minTemps = parsedLines.filter(x => x._2 == "TMIN")
    // Convert to (stationID, temperature)
    val stationTemps = minTemps.map(x => (x._1, x._3.toFloat))
    // Reduce by stationID retaining the minimum temperature found
    val minTempsByStation = stationTemps.reduceByKey((x, y) => min(x, y))
    // Collect, format, and print the results
    val results = minTempsByStation.collect()
    for (result <- results) {
      val station = result._1
      val temp = result._2
      val formatTemp = f"$temp%.2f F"
      println(s"$station minimum temparature : $formatTemp")
    }
    sc.stop()
  }
}
