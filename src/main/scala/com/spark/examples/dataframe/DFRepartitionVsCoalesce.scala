package com.spark.examples.dataframe

import com.spark.devproj.config.CommonUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}

object DFRepartitionVsCoalesce {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[5]")
      .appName("RDDRepartitionVsCoalesce")
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    val df = spark.range(0, 20)
    println(df.rdd.partitions.size)
    println(df.rdd.partitions.length)

    df.write.mode(SaveMode.Overwrite).csv(CommonUtils.getOutputFilePath("tmp/df/partitions"))

    val df2 = df.repartition(6)
    println(df2.rdd.partitions.length)
    df2.write.mode(SaveMode.Overwrite).csv(CommonUtils.getOutputFilePath("tmp/df/re-partitions"))

    val df3 = df.coalesce(2)
    println(df3.rdd.partitions.length)
    df3.write.mode(SaveMode.Overwrite).csv(CommonUtils.getOutputFilePath("tmp/df/coalesce"))

    /**
     * Calling groupBy(), union(), join() and similar functions on DataFrame results
     * in shuffling data between multiple executors and even machines and finally
     * repartitions data into 200 partitions by default. Spark default defines
     * shuffling partition to 200 using spark.sql.shuffle.partitions configuration.
     */

    val df4 = df.groupBy("id").count()
    println(df4.rdd.getNumPartitions)
    df4.write.mode(SaveMode.Overwrite).csv(CommonUtils.getOutputFilePath("tmp/df/groupBy"))

  }
}
