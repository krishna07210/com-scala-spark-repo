package com.spark.devproj.sparkRDD.dataAnalysis.examples1

import com.spark.devproj.config.CommonUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by krish on 05-04-2020.
 */
object RatingsCounter {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkConfig = new SparkConf().setAppName("local").setMaster("RatingsCounter")
    val sc = new SparkContext(sparkConfig)
    val lines = sc.textFile(CommonUtils.inputFile("udata.txt"))
    //    lines.foreach(println)
    val ratings = lines.map(x => x.toString().split("\t")(2))
    //    ratings.foreach(println)
    val results = ratings.countByValue()
    results.foreach(println)
    val sortedResults = results.toSeq.sortBy(_._1)
    sortedResults.foreach(println)
    sc.stop()
  }
}
