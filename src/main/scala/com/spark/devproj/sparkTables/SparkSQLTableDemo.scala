package com.spark.devproj.sparkTables

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkSQLTableDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(SparkSQLTableDemo.getClass.getName);
    val sparkSession = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("SparkSchemaDemo"))
      .enableHiveSupport()
      .getOrCreate()

    val flightTimeParquetDF = sparkSession.read
      .format("parquet")
      .option("path", CommonUtils.getInputFilePath("flight-*.parquet"))
      .load()

    /** if we don't specify the database, the table is going to create in the default database
     * called "default"
     */
    sparkSession.sql("CREATE DATABASE IF NOT EXISTS AIRLINE_DB")
    sparkSession.catalog.setCurrentDatabase("AIRLINE_DB")
    //    flightTimeParquetDF.write
    //      .mode(SaveMode.Overwrite)
    //      .partitionBy("OP_CARRIER", "ORIGIN")
    //      .saveAsTable("flight_data_tb1")

    flightTimeParquetDF.write
      .format("csv")
      .mode(SaveMode.Overwrite)
      //      .partitionBy("OP_CARRIER", "ORIGIN")
      .bucketBy(5, "OP_CARRIER", "ORIGIN")
      .sortBy("OP_CARRIER", "ORIGIN")
      .saveAsTable("flight_data_tb1")

    sparkSession.catalog.listTables().show()

  }
}
