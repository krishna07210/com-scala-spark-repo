package com.spark.devproj.sparkDataframeAPIs.joins

import com.spark.devproj.config.SparkConfigs
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object SparkJoinDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("SparkJoinDemo"))
      .getOrCreate()

    val ordersList = List(
      ("01", "02", 350, 1),
      ("01", "04", 580, 1),
      ("01", "07", 320, 2),
      ("02", "03", 450, 1),
      ("02", "06", 220, 1),
      ("03", "01", 195, 1),
      ("04", "09", 270, 3),
      ("04", "08", 410, 2),
      ("05", "02", 350, 1)
    )
    val orderDF = spark.createDataFrame(ordersList).toDF("order_id", "prod_id", "unit_price", "qty")

    val productList = List(
      ("01", "Scroll Mouse", 250, 20),
      ("02", "Optical Mouse", 350, 20),
      ("03", "Wireless Mouse", 450, 50),
      ("04", "Wireless Keyboard", 580, 50),
      ("05", "Standard Keyboard", 360, 10),
      ("06", "16 GB Flash Storage", 240, 100),
      ("07", "32 GB Flash Storage", 320, 50),
      ("08", "64 GB Flash Storage", 430, 25)
    )
    val productDF = spark.createDataFrame(productList).toDF("prod_id", "prod_name", "list_price", "qty")

    //column ambiguous handle
    val productRenamedDF = productDF.withColumnRenamed("qty", "reorder_qty")
    val joinType ="inner"

    val joinExpr = orderDF.col("prod_id") === productDF.col("prod_id")
    //    orderDF.join(productDF, joinExpr, "inner")
    //      .select("order_id", "prod_name", "unit_price", "qty")
    //      .show()

    orderDF.join(productRenamedDF, joinExpr, joinType)
      .drop(productRenamedDF.col("prod_id"))
      .select("order_id", "prod_id", "prod_name", "unit_price", "qty")
      .show()
  }
}
