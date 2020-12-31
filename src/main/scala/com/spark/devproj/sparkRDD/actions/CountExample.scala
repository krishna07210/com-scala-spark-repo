package com.spark.devproj.sparkRDD.actions

import com.spark.devproj.config.SparkConfigs
import org.apache.spark.SparkContext

object CountExample {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("CountExample"))

    val inputWords = List("spark", "hadoop", "spark", "hive", "pig", "cassandra", "hadoop")
    val wordsRDD = sparkContext.parallelize(inputWords)
    val words = wordsRDD.count()
    println("words :" ,words)
    val wordCountByValue = wordsRDD.countByValue()
    for ((word,count) <- wordCountByValue) println(word + " - " +count)
  }

}
