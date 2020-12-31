package com.spark.devproj.sparkRDD.actions

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.spark.SparkContext

/**
 * Create a Spark program to read the first 100 prime numbers from in/prime_nums.text,
 * print the sum of those numbers to console.
 *
 * Each row of the input file contains 10 prime numbers separated by spaces.
 */
object SumOfNumbersProblem {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("SumOfNumbersProblem"))
    val lines = sparkContext.textFile(CommonUtils.getInputFilePath("prime_nums.text"))
    val sumOfNumbers = lines.flatMap(line => line.split("\\s+"))
      .filter(number => !number.isEmpty)
      .map(number => number.toInt)
      .reduce((x, y) => x + y)
    println("Sum is :", sumOfNumbers)
  }
}
