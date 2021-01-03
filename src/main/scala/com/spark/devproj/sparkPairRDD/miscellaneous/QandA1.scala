package com.spark.devproj.sparkPairRDD.miscellaneous

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/** Spark RDD partition by key in exclusive way
 * to use partitionBy() RDD must consist of tuple (pair) objects. Lets see an example below:
 * https://stackoverflow.com/questions/53383205/spark-rdd-partition-by-key-in-exclusive-way
 */

object QandA1 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkContext: SparkContext =
      new SparkContext(SparkConfigs.getLocalSparkConf("QandA1"))

    //Reading file into RDD and skip header
    val orders = sparkContext
      .textFile(CommonUtils.getInputFilePath("sample_Orders.txt"))
    orders.foreach(println)
    val newOrders = orders.filter(order => !skipHeader(order))
    //now Lets re-partition RDD into '5' partitions
    val partitionRDD = newOrders.repartition(5)
    //lets have a look how data is being distributed in these '5' partitions
    print("Partitions structure: {}".format(partitionRDD.glom().collect()))
    //here you can see that data is written into two partitions and,
    // three of them are empty and also it's not being distributed uniformly.
  }

  def skipHeader(order: String): Boolean = {
    order.split("|")(0).equals("OrderId") &&
      order.split("|")(1).equals("OrderItem") &&
      order.split("|")(2).equals("OrderDate") &&
      order.split("|")(3).equals("OrderPrice") &&
      order.split("|")(4).equals("ItemQuantity")
  }
}
