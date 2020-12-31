package com.spark.devproj.sparkPairRDD.aggregations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

object WordCount {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("AirportsUppercaseProblem"))

    val lines = sparkContext
      .textFile(CommonUtils.getInputFilePath("word_count.text"))
    val wordRdd = lines.flatMap(line => line.split(" "))
    val wordPairRdd = wordRdd.map(word => (word.toLowerCase(), 1))
    val wordCounts = wordPairRdd.reduceByKey((x, y) => (x + y))
    for ((word, count) <- wordCounts.collect()) println(word + " : " + count)
    sparkContext.stop()
  }
}
