package com.spark.devproj.sparkRDD.actions

import com.spark.devproj.config.SparkConfigs
import org.apache.spark.SparkContext

object CollectExample {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("CollectExample"))

    val inputWords = List("spark", "hadoop", "spark", "hive", "pig", "cassandra", "hadoop")
    val wordsRDD = sparkContext.parallelize(inputWords)
    val words = wordsRDD.collect()
    for (word <- words) println(word)
  }

}
