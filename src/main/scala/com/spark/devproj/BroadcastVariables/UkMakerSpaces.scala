package com.spark.devproj.BroadcastVariables

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

import scala.io.Source

/**
 * How are those maker spaces distributed across different regions in the UK?
 *
 * Solution:
 * 1. load the postcode dataset and broadcast it across the cluster.
 * 2. load the maker space dataset and call map operation on the maker space RDD to look up
 * the region using the postcode of the maker space.
 *
 * UK Postcode:
 * • The postcode in the maker space RDD is the full postcode,
 * – W1T 3AC
 * • The postcode in the postcode dataset is only the prefix of the postcode.
 * – W1T
 * • Join condition:
 * – If the postcode column in the maker space data source starts with the postcode column in
 * the postcode data source.
 */
object UkMakerSpaces {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("UkMakerSpaces"))

    val makerSpaceRdd = sparkContext
      .textFile(CommonUtils.getInputFilePath("uk-makerspaces-identifiable-data.csv"))

    val postCodeMap = sparkContext.broadcast(loadPostCodeMap())

    val regions = makerSpaceRdd
      .filter(line => line.split(CommonUtils.COMMA_DELIMITER, -1)(0) != "Timestamp")
      .filter(line => getPostPrefix(line).isDefined)
      .map(line => postCodeMap.value.getOrElse(getPostPrefix(line).get, "Unknown"))
    for ((region, count) <- regions.countByValue()) println(region + " : " + count)
  }

  def loadPostCodeMap(): Map[String, String] = {
    Source.fromFile(CommonUtils.getInputFilePath("uk-postcode.csv"))
      .getLines().map(line => {
      val splits = line.split(CommonUtils.COMMA_DELIMITER, -1)
      splits(0) -> splits(7)
    }).toMap
  }

  def getPostPrefix(line: String): Option[String] = {
    val splits = line.split(CommonUtils.COMMA_DELIMITER, -1)
    val postCode = splits(4)
    if (postCode.isEmpty) None else Some(postCode.split(" ")(0))
  }

}
