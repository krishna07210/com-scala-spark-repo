package com.spark.devproj.DataFrameAPIs

import com.esotericsoftware.minlog.Log
import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import com.spark.devproj.sparkDataframe.miscellaneous.HelloSparkDataframe
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DateType, IntegerType, StringType, StructField, StructType}

object SparkSchemaDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(HelloSparkDataframe.getClass.getName);
    val sparkSession = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("SparkSchemaDemo"))
      .getOrCreate()

    val flightTimeCSVDF = sparkSession.read
      .format("csv")
      .option("header", "true")
      .option("path", CommonUtils.getInputFilePath("flight-*.csv"))
      .option("inferSchema", "true") // if we won't specify default String data type is considered for all the fields.
      .load()

    flightTimeCSVDF.show(5)
    logger.info("CSV Schema :" + flightTimeCSVDF.schema.simpleString)

    val flightTimeJsonDF = sparkSession.read
      .format("json")
      //      .option("header", "true") // not applicable since json doesn't come with schema.
      .option("path", CommonUtils.getInputFilePath("flight-*.json"))
      //  .option("inferSchema","true") // for json the interschema is not applicable, because it always comes with interschema
      .load()


    flightTimeJsonDF.show(5)
    logger.info("JSON Schema :" + flightTimeJsonDF.schema.simpleString)

    val flightTimeParquetDF = sparkSession.read
      .format("parquet")
      .option("path", CommonUtils.getInputFilePath("flight-*.parquet"))
      //  .option("inferSchema","true") // parquet will always come with schema
      .load()


    flightTimeParquetDF.show(5)
    logger.info("Parquet Schema :" + flightTimeParquetDF.schema.simpleString)


    //Programmtically defining the Schema
    val flightSchemaStruct = StructType(List(
      StructField("FL_DATA", DateType),
      StructField("OP_CARRIER", StringType),
      StructField("OP_CARRIER_FL_NUM", IntegerType),
      StructField("ORIGIN", StringType),
      StructField("ORIGIN_CITY_NAME", StringType),
      StructField("DEST", StringType),
      StructField("DEST_CITY_NAME", StringType),
      StructField("CRS_DEP_TIME", IntegerType),
      StructField("DEP_TIME", IntegerType),
      StructField("WHEELS_ON", IntegerType),
      StructField("TAXI_IN", IntegerType),
      StructField("CRS_ARR_TIME", IntegerType),
      StructField("ARR_TIME", IntegerType),
      StructField("CANCELLED", IntegerType),
      StructField("DISTANCE", IntegerType)
    ))

    val flightTimeCSVDFWithSchema = sparkSession.read
      .format("csv")
      .option("header", "true")
      .option("path", CommonUtils.getInputFilePath("flight-*.csv"))
      .option("mode", "FAILFAST")
      .schema(flightSchemaStruct)
      .option("dateFormat", "M/d/y") // To Avoid java.time.format.DateTimeParseException
      .load()

    flightTimeCSVDFWithSchema.show(5)
    logger.info("CSV Programtic Schema :" + flightTimeCSVDFWithSchema.schema.simpleString)

    //Defining the Schema with DDL String
    val flightSchemaDDL = "FL_DATE DATE, OP_CARRIER STRING, OP_CARRIER_FL_NUM INT, ORIGIN STRING, " +
      "ORIGIN_CITY_NAME STRING, DEST STRING, DEST_CITY_NAME STRING, CRS_DEP_TIME INT, DEP_TIME INT, " +
      "WHEELS_ON INT, TAXI_IN INT, CRS_ARR_TIME INT, ARR_TIME INT, CANCELLED INT, DISTANCE INT"

    val flightTimeJsonDFWithSchema = sparkSession.read
      .format("json")
      .option("path", CommonUtils.getInputFilePath("flight-*.json"))
      .schema(flightSchemaDDL)
      .option("dateFormat", "M/d/y")
      .load()

    flightTimeJsonDFWithSchema.show(5)
    logger.info("JSON with DDL Schema :" + flightTimeJsonDFWithSchema.schema.simpleString)


    sparkSession.stop()

  }
}
