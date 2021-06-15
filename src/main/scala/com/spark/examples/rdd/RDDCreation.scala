package com.spark.examples.rdd

import org.apache.hadoop.hive.ql.optimizer.spark.SparkSkewJoinProcFactory.SparkSkewJoinJoinProcessor
import org.apache.spark.sql.SparkSession

object RDDCreation {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("Rdd Creation")
      .getOrCreate()
    val dataSeq = Seq(("Java", 20000), ("Python", 3000))
    val rdd = spark.sparkContext.parallelize(dataSeq)
    System.out.println("defaultParallelism " +spark.sparkContext.defaultParallelism)
    System.out.println("defaultMinPartitions " +spark.sparkContext.defaultMinPartitions)
    val rddPartitions = rdd.getNumPartitions;
    System.out.println("Rdd Partitions ::"+rddPartitions);
    scala.io.StdIn.readLine()
  }
}
