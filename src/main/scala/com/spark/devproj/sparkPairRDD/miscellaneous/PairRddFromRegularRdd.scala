package com.spark.devproj.sparkPairRDD.miscellaneous

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

object PairRddFromRegularRdd {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("PairRddFromRegularRdd"))
    val inputStrings = List("Lily 23", "Jack 29", "Mary 29", "James 8")
    val regularRDDs = sparkContext.parallelize(inputStrings)

    val pairRDD = regularRDDs.map(s => (s.split(" ")(0), s.split(" ")(1)))
    pairRDD.coalesce(1)
      .saveAsTextFile(CommonUtils.getOutputFilePath("out/pair_rdd_from_regular_rdd"))
  }
}
