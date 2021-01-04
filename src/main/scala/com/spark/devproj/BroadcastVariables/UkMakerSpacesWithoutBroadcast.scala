package com.spark.devproj.BroadcastVariables

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

import scala.io.Source

object UkMakerSpacesWithoutBroadcast {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("UkMakerSpaces"))
    val postCodeMap = loadPostCodeMap()
    val makerSpaceRdd = sparkContext
      .textFile(CommonUtils.getInputFilePath("uk-makerspaces-identifiable-data.csv"))
    val regions = makerSpaceRdd
      .filter(line => line.split(CommonUtils.COMMA_DELIMITER, -1)(0) != "Timestamp")
      .map(line => {
        getPostPrefixes(line).filter(prefix => postCodeMap.contains(prefix))
          .map(prefix => postCodeMap(prefix))
          .headOption.getOrElse("Unknow")
      })
    for ((region, count) <- regions.countByValue()) println(region + " : " + count)
  }

  def getPostPrefixes(line: String): List[String] = {
    val postcode = line.split(CommonUtils.COMMA_DELIMITER, -1)(4)
    val cleanedPostCode = postcode.replaceAll("\\s+", "")
    (1 to cleanedPostCode.length).map(i => cleanedPostCode.substring(0, i)).toList
  }

  def loadPostCodeMap(): Map[String, String] = {
    Source.fromFile(CommonUtils.getInputFilePath("uk-postcode.csv"))
      .getLines.map(line => {
      val splits = line.split(CommonUtils.COMMA_DELIMITER, -1)
      splits(0) -> splits(7)
    }).toMap
  }
}


