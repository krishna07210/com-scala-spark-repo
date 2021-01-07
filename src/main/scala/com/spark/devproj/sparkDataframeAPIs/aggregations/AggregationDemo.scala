package com.spark.devproj.sparkDataframeAPIs.aggregations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import com.spark.devproj.sparkDataframeAPIs.examples.DataSinkDemo
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object AggregationDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("SparkSchemaDemo"))
      .getOrCreate()

    val invoiceDF = spark.read
      .format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(CommonUtils.getInputFilePath("invoices.csv"))

    /*
    //1. Simple Aggregations
    invoiceDF.select(
      count("*").as("Count *"),
      sum("Quantity").as("TotalQuantity"),
      avg("UnitPrice").as("AvgUnitPrice"),
      countDistinct("InvoiceNo").as("DistinctInvoiceNo")
    ).show()

    invoiceDF.selectExpr(
      "count(1) as `count 1`",
      "count(StockCode) as `count field`",
      "sum(Quantity) as TotalQuantity",
      "avg(UnitPrice) as AvgUnitPrice"
    ).show()

*/

    //2. Grouping Aggregations

    //With Spark SQL
    invoiceDF.createTempView("sales")
    val summarySQL = spark.sql(
      """
        |SELECT Country, InvoiceNo,
        |sum(Quantity) as TotalQuantity,
        | round(sum(Quantity*UnitPrice),2) as InvoiceValue
        | FROM sales
        | GROUP BY Country, InvoiceNo
        |""".stripMargin)
    summarySQL.show()

    // Grouping with DataFrame API.
    val summaryDF = invoiceDF.groupBy("Country", "InvoiceNo")
      .agg(
        sum("Quantity").as("TotalQuantity"),
        round(sum(expr("Quantity * UnitPrice")), 2).as("InvoiceValue"),
        expr("round(sum(Quantity*UnitPrice),2)").as("InvoiceValueExpr")
      )
    summaryDF.show()

    spark.stop()
  }
}
