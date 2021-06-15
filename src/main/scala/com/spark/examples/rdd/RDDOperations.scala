package com.spark.examples.rdd

import com.spark.devproj.config.CommonUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object RDDOperations {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("DFReadFilesTypes")
      .master("local[3]")
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)

    val OrdersRDD = spark.sparkContext.textFile(CommonUtils.getInputFilePath("sample_Orders.txt"))
    println("No of Partitions " +OrdersRDD.getNumPartitions)
    println("Partition Size " +OrdersRDD.partitions.size)
    OrdersRDD.repartition(3)
    println("re-partition count:"+OrdersRDD.getNumPartitions)
    OrdersRDD.collect()
  }
}
