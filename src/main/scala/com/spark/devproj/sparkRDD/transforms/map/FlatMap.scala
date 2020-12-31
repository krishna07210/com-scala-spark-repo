package com.spark.devproj.sparkRDD.transforms.map

import org.apache.spark.SparkContext
import com.spark.devproj.config.{CommonUtils, SparkConfigs}

object FlatMap {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("Flat-Map"))

    val RD0 = sparkContext.textFile(CommonUtils.inputFile("book.txt"))
    val RD1 = RD0.flatMap(rec => rec.split(" ")).map(rec=>rec.toLowerCase())
    RD1.countByValue().foreach(println)
    sparkContext.stop()
  }
}
