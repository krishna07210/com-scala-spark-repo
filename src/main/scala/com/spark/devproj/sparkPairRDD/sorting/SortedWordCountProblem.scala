package com.spark.devproj.sparkPairRDD.sorting

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
 * Create a Spark program to read the an article from in/word_count.text,
 * output the number of occurrence of each word in descending order.
 *
 * Sample output:
 *
 * apple : 200
 * shoes : 193
 * bag : 176
 */
object SortedWordCountProblem {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("SortedWordCountProblem"))

    val lines = sparkContext
      .textFile(CommonUtils.getInputFilePath("word_count.text"))

    val wordToCountPairs = lines.flatMap(line => line.split(" "))
      .map(word => (word.trim.toLowerCase(), 1))
      .reduceByKey((x, y) => (x + y))

//    wordToCountPairs.foreach(println)
    val countToWordPairs = wordToCountPairs.map(wordToCount => (wordToCount._2, wordToCount._1))
//    countToWordPairs.foreach(println)
    val sortedCountToWordParis = countToWordPairs.sortByKey(ascending = true)
//    sortedCountToWordParis.foreach(println)
    val sortedWordToCountPairs = sortedCountToWordParis.map(countToWord => (countToWord._2, countToWord._1))
    for ((word, count) <- sortedWordToCountPairs.collect()) println(word + " : " + count)

  }
}
