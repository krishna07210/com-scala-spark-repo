package com.spark.devproj.sparkPairRDD.miscellaneous

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

object PairRddFromTupleList {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("SumOfNumbersProblem"))
    val tuple = List(("Lily", 23), ("Jack", 29), ("Mary", 29), ("James", 8))
    val pairRDD = sparkContext.parallelize(tuple)
    pairRDD.coalesce(1)
      .saveAsTextFile(CommonUtils.getOutputFilePath("pair_rdd_from_tuple_list"))
  }
}
