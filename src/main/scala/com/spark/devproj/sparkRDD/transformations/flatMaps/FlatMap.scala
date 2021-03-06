package com.spark.devproj.sparkRDD.transformations.flatMaps

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

object FlatMap {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("Flat-Map"))

    val RD0 = sparkContext.textFile(CommonUtils.getInputFilePath("book.txt"))
    val RD1 = RD0.flatMap(rec => rec.split(" ")).map(rec=>rec.toLowerCase())
    RD1.countByValue().foreach(println)
    sparkContext.stop()
  }
}
