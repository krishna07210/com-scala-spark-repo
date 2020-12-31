package com.spark.devproj.sparkRDD.actions

import com.spark.devproj.config.SparkConfigs
import org.apache.spark.SparkContext

object ReduceExample {
  def main(args: Array[String]): Unit = {
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("ReduceExample"))
    val inputIntegers = List(1, 2, 3, 4, 5)
    val integerRdd = sparkContext.parallelize(inputIntegers)
    val product = integerRdd.reduce((x, y) => (x * y))
    println("Product is : " + product)
  }
}
