package com.spark.devproj.sparkPairRDD.joins

import com.spark.devproj.config.SparkConfigs
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object JoinOperations {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("JoinOperations"))

    val ages = sparkContext.parallelize(List(("Tom", 29), ("John", 22)))
    val address = sparkContext.parallelize(List(("James", "USA"), ("John", "UK")))

    println("********** Join ************")
    val join = ages.join(address).foreach(println)

    println("********** LeftOuterJoin ************")
    val leftOuterJoin = ages.leftOuterJoin(address).foreach(println)

    println("********** RightOuterJoin ************")
    val rightOuterJoin = ages.rightOuterJoin(address).foreach(println)

    println("********** FullOuterJoin ************")
    val fullOuterJoin = ages.fullOuterJoin(address).foreach(println)
  }
}
