package com.spark.devproj.sparkRDD.dataAnalysis.examples1

import com.spark.devproj.config.{CommonUtils, SparkConfigs}

/**
 * Created by krish on 14-04-2020.
 * Compute the total amount spent per customer in some fake e-commerce data.
 */

object PurchaseByCustomer {

  def main(args: Array[String]): Unit = {

    def parseOrderData(line: String) = {
      val fields = line.split(",")
      val customerID = fields(0)
      val itemID = fields(1)
      val invoiceAmt = fields(2).toFloat
      (customerID, itemID, invoiceAmt)
    }
    val sc = SparkConfigs.localConfig("local", "PurchaseByCustomer")
    val orderData = sc.textFile(CommonUtils.getInputFilePath("customer-orders.csv"))
    val invoiceData = orderData.map(parseOrderData)
    val purchaseData = invoiceData.map(x => (x._1.toInt, x._3))
    //    purchaseData.foreach(x=> println(x._1 + "-" + x._2))
    val custPurchaseData = purchaseData.reduceByKey((x, y) => (x + y)).sortByKey()
    //custPurchaseData.foreach(x=> println(x._1 + " " + x._2))
    /*
      1. Top Spended Customer
      2. Least Spended Customer
      3. Total Purchase Amount
     */
    val topSpentCustomer = purchaseData.reduceByKey((x, y) => (x + y)).sortBy(_._2, ascending = false).take(1)
    topSpentCustomer.foreach(x => println(s"Top Spent Customer --${x._1} and Amount =${x._2}"))
    val leastSpentCustomer = purchaseData.reduceByKey((x, y) => (x + y)).sortBy(_._2, ascending = true).take(1)
    leastSpentCustomer.foreach(x => println(s"Least Spent Customer -${x._1} and Amount =${x._2}"))
    val totalPurchaseAmt = purchaseData.values.sum()
    println(f"Total Purchases $totalPurchaseAmt%.02f")
  }
}
