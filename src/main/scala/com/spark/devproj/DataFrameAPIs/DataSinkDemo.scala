package com.spark.devproj.DataFrameAPIs

import com.spark.devproj.config.{CommonUtils, SparkConfigs}
import com.spark.devproj.sparkDataframe.miscellaneous.HelloSparkDataframe
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}

object DataSinkDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    @transient lazy val logger: Logger = Logger.getLogger(DataSinkDemo.getClass.getName);
    val sparkSession = SparkSession.builder()
      .config(SparkConfigs.getLocalSparkConf("SparkSchemaDemo"))
      .getOrCreate()


    val flightTimeParquetDF = sparkSession.read
      .format("parquet")
      .option("path", CommonUtils.getInputFilePath("flight-*.parquet"))
      .load()

    //Number of files?
    logger.info("Num Partitions before :" + flightTimeParquetDF.rdd.getNumPartitions)
    import org.apache.spark.sql.functions.spark_partition_id
    flightTimeParquetDF.groupBy(spark_partition_id()).count().show()

    val partitionedDF = flightTimeParquetDF.repartition(5)
    logger.info("Num Partitions after :" + partitionedDF.rdd.getNumPartitions)
    partitionedDF.groupBy(spark_partition_id()).count().show()

    flightTimeParquetDF.write
      .format(source = "avro")
      .mode(SaveMode.Overwrite) //it clean the directory and creates new files.
      .option("path", CommonUtils.getOutputFilePath("dataSink/avro/"))
      .save()

    partitionedDF.write
      .format(source = "avro")
      .mode(SaveMode.Overwrite) //it clean the directory and creates new files.
      .option("path", CommonUtils.getOutputFilePath("dataSinkPartitioned/avro/"))
      .save()

    //Partition By?
    flightTimeParquetDF.write
      .format("json")
      .mode(SaveMode.Overwrite)
      .option("path", CommonUtils.getOutputFilePath("dataSinkPartitionedBy/json/"))
      .partitionBy("OP_CARRIER", "ORIGIN")
      .option("maxRecordsPerFile",10000)
      .save()

    sparkSession.stop()
  }
}
