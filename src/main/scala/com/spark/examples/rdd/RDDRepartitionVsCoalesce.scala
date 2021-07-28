package com.spark.examples.rdd

import com.spark.devproj.config.CommonUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object RDDRepartitionVsCoalesce {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[5]")
      .appName("RDDRepartitionVsCoalesce")
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)
    val rdd = spark.sparkContext.parallelize(Range(0, 20))
    //    rdd.foreach(println)
    println("From local[5] : " + rdd.partitions.size)

    /**
     * In RDD, you can create parallelism at the time of the creation of an RDD
     * using parallelize(), textFile() and wholeTextFiles().
     */
    val rdd1 = spark.sparkContext.parallelize(Range(0, 25), 6)
    println("parallelize : " + rdd1.partitions.size)
    val rddFromFile = spark.sparkContext.textFile(CommonUtils.getInputFilePath("number.txt"), 10)
    println("TextFile : " + rddFromFile.partitions.size)

    rdd1.saveAsTextFile(CommonUtils.getOutputFilePath("tmp/rdd/partitions"))
    //Writes 6 part files, one for each partition

    val rdd2 = rdd1.repartition(4)
    println("Repartition size : " + rdd2.partitions.size)
    rdd2.saveAsTextFile(CommonUtils.getOutputFilePath("tmp/rdd/re-partition"))

    val rdd3 = rdd1.coalesce(2)
    println("Repartition size : "+rdd3.partitions.size)
    rdd3.saveAsTextFile(CommonUtils.getOutputFilePath("tmp/rdd/coalesce"))

  }
}
