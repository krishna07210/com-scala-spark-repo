package com.spark.devproj.sparkRDD.dataAnalysis.examples1

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

/**
 * Created by krish on 12-04-2020.
 * Compute the average number of friends by age
 */
object  FriendsByAge {

  def parseLine(line: String) = {
    val fields = line.split(",")
    val age = fields(2).toInt
    val numFriends = fields(3).toInt
    (age, numFriends)
  }

  def main(args: Array[String]): Unit = {
    val sc: SparkContext = SparkConfigs.localConfig("local", "FriendsByAge");
    val lines = sc.textFile(CommonUtils.getInputFilePath("fakefriends.csv"))
    val rawRDD = lines.map(parseLine)
    val totalsByAge = rawRDD.mapValues(x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
    totalsByAge.foreach(println)
    val averageByAge = totalsByAge.mapValues(x => x._1 / x._2)
    averageByAge.foreach(println)
    val result = averageByAge.collect()
    result.sorted.foreach(println)
    sc.stop()
  }
}
