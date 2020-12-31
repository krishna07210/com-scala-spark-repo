package com.spark.devproj.sparkRDD.optimization

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext
import org.apache.spark.storage.StorageLevel

object PersistExample {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("PersistExample"))

    val lines = sparkContext.textFile(CommonUtils.getInputFilePath("prime_nums.text"))
    val sumOfNumbers = lines.flatMap(line => line.split("\\s+"))
      .filter(number => !number.isEmpty)
      .map(number => number.toInt)
    sumOfNumbers.persist(StorageLevel.MEMORY_ONLY)
    println("Sum :" + sumOfNumbers.reduce((x, y) => (x + y)))
    println("Multiply :" + sumOfNumbers.reduce((x, y) => (x * y)))
//    scala.io.StdIn.readLine()
    sparkContext.stop()
  }
}
