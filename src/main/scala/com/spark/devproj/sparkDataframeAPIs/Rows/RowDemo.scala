package com.spark.devproj.sparkDataframeAPIs.Rows

import com.spark.devproj.config.SparkConfigs
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.{col, to_date}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object RowDemo extends Serializable {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(RowDemo.getClass.getName);
    val spark = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("RowDemo"))
      .enableHiveSupport()
      .getOrCreate()
    val mySchema = StructType(List(StructField("ID", StringType), StructField("EventDate", StringType)))
    val myRows = List(Row("123", "01/05/2021"), Row("234", "03/05/2021"))
    val myRDD = spark.sparkContext.parallelize(myRows, 2)
    val myDF = spark.createDataFrame(myRDD, mySchema)

    myDF.printSchema
    myDF.show()
    val newDF = toDateDF(myDF, "M/d/y", "EventDate")
    newDF.printSchema
    newDF.show()


    spark.stop()
  }

  def toDateDF(df: DataFrame, fmt: String, fld: String): DataFrame = {
    df.withColumn(fld, to_date(col(fld), fmt))
  }
}
