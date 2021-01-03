package com.spark.devproj.sparkPairRDD.aggregations

import com.spark.devproj.config.SparkConfigs
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object GroupByKeyVsReduceByKey {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("GroupByKeyVsReduceByKey"))
    val words = List("one", "two", "two", "three", "three", "three")
    val wordsPairRdd = sparkContext.parallelize(words)

    val wordCountsWithReduceByKey = wordsPairRdd.map(word => (word, 1))
      .reduceByKey((x, y) => (x + y))
      .collect()
    println("wordCountsWithReduceByKey: " + wordCountsWithReduceByKey.toList)

    val wordCountsWithGroupByKey = wordsPairRdd.map(word => (word, 1))
      .groupByKey().mapValues(intIterable => intIterable.size)
      .collect()
    println("wordCountsWithGroupByKey: " + wordCountsWithGroupByKey.toList)
  }
}
