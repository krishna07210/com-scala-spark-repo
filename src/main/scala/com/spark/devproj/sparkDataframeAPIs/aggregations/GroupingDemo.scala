package com.spark.devproj.sparkDataframeAPIs.aggregations

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import com.spark.devproj.sparkDataframeAPIs.aggregations.AggregationDemo.getClass
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{expr, _}

object GroupingDemo {
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

    val NumInvoices = countDistinct("InvoiceNo").as("NumInvoices")
    val TotalQuantity = sum("Quantity").as("TotalQuantity")
    val InvoiceValue = expr("round(sum(Quantity*UnitPrice),2)") as ("InvoiceValue")

    val exSummaryDF = invoiceDF.withColumn("InvoiceDate", to_date(col("InvoiceDate"), "dd-MM-yyyy H.mm"))
      .where("year(InvoiceDate) == 2010")
      .withColumn("WeekNumber", weekofyear(col("InvoiceDate")))
      .groupBy("Country", "WeekNumber")
      .agg(
        /*
        countDistinct("InvoiceNo").as("NumInvoices"),
        sum("Quantity").as("TotalQuantity"),
        expr("round(sum(Quantity*UnitPrice),2)") as ("InvoiceValue")
        */
        NumInvoices, TotalQuantity, InvoiceValue
      )

    exSummaryDF.coalesce(1)
      .write
      .format("parquet")
      .mode("overwrite")
      .save(CommonUtils.getOutputFilePath("dataFrameAgg/Invoices_Aggregations"))
    exSummaryDF.sort("Country", "WeekNumber")
  }
}
