package com.spark.devproj.sparkRDD.dataAnalysis.examples1

import com.spark.devproj.config.CommonUtils
import org.apache.spark.{SparkConf, SparkContext}

object GetMaxPrice {

  def main(args: Array[String]): Unit = {

    val config = new SparkConf().setMaster("local").setAppName("GetMaxPrice")
    val sc = new SparkContext(config)
    val orderData = sc.textFile(CommonUtils.inputFile("OrderData.csv"))
    orderData.map(_.split(",")).map(line => (line(0).split("-")(0).toInt, line(1).toFloat)).reduceByKey((a, b) => Math.max(a, b)).saveAsTextFile(CommonUtils.outputFile("MaxPrice"))
  }
}
